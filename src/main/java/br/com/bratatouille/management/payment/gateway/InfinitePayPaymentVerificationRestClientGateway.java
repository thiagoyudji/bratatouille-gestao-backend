package br.com.bratatouille.management.payment.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class InfinitePayPaymentVerificationRestClientGateway implements InfinitePayPaymentVerificationGateway {

    private final RestClient restClient;
    private final String handle;

    public InfinitePayPaymentVerificationRestClientGateway(
            RestClient.Builder restClientBuilder,
            @Value("${bratatouille.payments.infinitepay.base-url:https://api.checkout.infinitepay.io}") String baseUrl,
            @Value("${bratatouille.payments.infinitepay.handle:}") String handle
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.handle = handle;
    }

    @Override
    public InfinitePayPaymentVerification verify(String orderNsu, String transactionNsu, String invoiceSlug) {
        if (!StringUtils.hasText(handle)) {
            throw new IllegalStateException("InfinitePay handle is required");
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("handle", handle);
        payload.put("order_nsu", orderNsu);
        payload.put("transaction_nsu", transactionNsu);
        payload.put("slug", invoiceSlug);

        JsonNode response = restClient.post()
                .uri("/payment_check")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(JsonNode.class);

        if (response == null || !response.path("success").asBoolean(false)) {
            throw new IllegalArgumentException("InfinitePay payment could not be verified");
        }

        JsonNode amount = response.get("amount");
        JsonNode paidAmount = response.get("paid_amount");
        if (amount == null || !amount.canConvertToLong() || paidAmount == null || !paidAmount.canConvertToLong()) {
            throw new IllegalStateException("InfinitePay payment verification response is incomplete");
        }

        return new InfinitePayPaymentVerification(
                response.path("paid").asBoolean(false),
                amount.longValue(),
                paidAmount.longValue(),
                textOrNull(response.get("capture_method"))
        );
    }

    private String textOrNull(JsonNode node) {
        return node == null || node.isNull() || !StringUtils.hasText(node.asText()) ? null : node.asText();
    }
}
