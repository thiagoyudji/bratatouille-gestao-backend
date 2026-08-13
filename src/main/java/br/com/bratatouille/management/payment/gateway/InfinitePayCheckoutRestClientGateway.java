package br.com.bratatouille.management.payment.gateway;

import br.com.bratatouille.management.generated.model.SalesOrderCustomerAddress;
import br.com.bratatouille.management.generated.model.SalesOrderItemResponse;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class InfinitePayCheckoutRestClientGateway implements InfinitePayCheckoutGateway {

    private final RestClient restClient;
    private final String handle;
    private final String webhookUrl;
    private final String redirectUrl;

    public InfinitePayCheckoutRestClientGateway(
            RestClient.Builder restClientBuilder,
            @Value("${bratatouille.payments.infinitepay.base-url:https://api.checkout.infinitepay.io}") String baseUrl,
            @Value("${bratatouille.payments.infinitepay.handle:}") String handle,
            @Value("${bratatouille.payments.infinitepay.webhook-url:}") String webhookUrl,
            @Value("${bratatouille.payments.infinitepay.redirect-url:}") String redirectUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.handle = handle;
        this.webhookUrl = webhookUrl;
        this.redirectUrl = redirectUrl;
    }

    @Override
    public InfinitePayCheckoutResult createCheckout(SalesOrderResponse salesOrderResponse) {
        if (!StringUtils.hasText(handle)) {
            throw new IllegalStateException("InfinitePay handle is required");
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("handle", handle);
        payload.put("order_nsu", String.valueOf(salesOrderResponse.getId()));
        payload.put("items", salesOrderResponse.getItems().stream().map(this::toItemPayload).toList());

        if (StringUtils.hasText(webhookUrl)) {
            payload.put("webhook_url", webhookUrl);
        }

        if (StringUtils.hasText(redirectUrl)) {
            payload.put("redirect_url", redirectUrl);
        }

        if (StringUtils.hasText(salesOrderResponse.getCustomerName())
                || StringUtils.hasText(salesOrderResponse.getCustomerEmail())
                || StringUtils.hasText(salesOrderResponse.getCustomerPhone())) {
            payload.put("customer", toCustomerPayload(salesOrderResponse));
        }

        if (salesOrderResponse.getDeliveryAddress() != null) {
            payload.put("address", toAddressPayload(salesOrderResponse.getDeliveryAddress()));
        }

        JsonNode response = restClient.post()
                .uri("/links")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(JsonNode.class);

        if (response == null) {
            throw new IllegalStateException("InfinitePay checkout response is empty");
        }

        String checkoutUrl = extractString(response, "checkout_url", "url", "link", "payment_url");
        if (!StringUtils.hasText(checkoutUrl)) {
            throw new IllegalStateException("InfinitePay checkout url not found in response");
        }

        return new InfinitePayCheckoutResult(checkoutUrl, extractString(response, "invoice_slug", "slug"));
    }

    private Map<String, Object> toItemPayload(SalesOrderItemResponse itemResponse) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("description", itemResponse.getItemName());
        item.put("quantity", itemResponse.getQuantity());
        item.put("price", moneyToCents(itemResponse.getUnitPrice()));
        return item;
    }

    private Map<String, Object> toCustomerPayload(SalesOrderResponse salesOrderResponse) {
        Map<String, Object> customer = new LinkedHashMap<>();
        customer.put("name", salesOrderResponse.getCustomerName());
        customer.put("email", salesOrderResponse.getCustomerEmail());
        customer.put("phone", salesOrderResponse.getCustomerPhone());
        return customer;
    }

    private Map<String, Object> toAddressPayload(SalesOrderCustomerAddress address) {
        Map<String, Object> addressPayload = new LinkedHashMap<>();
        addressPayload.put("zipCode", address.getZipCode());
        addressPayload.put("street", address.getStreet());
        addressPayload.put("number", address.getNumber());
        addressPayload.put("neighborhood", address.getNeighborhood());
        addressPayload.put("state", address.getState());
        addressPayload.put("city", address.getCity());
        addressPayload.put("complement", address.getComplement());
        return addressPayload;
    }

    private long moneyToCents(BigDecimal value) {
        return value.movePointRight(2).setScale(0, java.math.RoundingMode.HALF_UP).longValueExact();
    }

    private String extractString(JsonNode response, String... keys) {
        for (String key : keys) {
            JsonNode node = response.get(key);
            if (node != null && !node.isNull() && StringUtils.hasText(node.asText())) {
                return node.asText();
            }
        }

        JsonNode data = response.get("data");
        if (data != null && data.isObject()) {
            for (String key : keys) {
                JsonNode node = data.get(key);
                if (node != null && !node.isNull() && StringUtils.hasText(node.asText())) {
                    return node.asText();
                }
            }
        }

        return null;
    }
}
