package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.InfinitePayCheckoutResponse;
import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.InfinitePayWebhookResponse;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link PaymentsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface PaymentsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/payments/infinitepay/checkouts : Create an InfinitePay checkout for a pending sales order
     * Creates the sales order, reserves stock and generates the InfinitePay checkout URL. Anonymous requests are treated as guest with PF price; authenticated customers have identity and PF/PJ type resolved by the backend.
     *
     * @param salesOrderCreateRequest  (required)
     * @return Checkout created (status code 200)
     * @see PaymentsApi#createInfinitePayCheckout
     */
    default ResponseEntity<InfinitePayCheckoutResponse> createInfinitePayCheckout(SalesOrderCreateRequest salesOrderCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"provider\" : \"INFINITEPAY\", \"orderId\" : 123, \"checkoutUrl\" : \"https://buy.infinitepay.io/checkout/abc123\", \"invoiceSlug\" : \"abc123\", \"paymentStatus\" : \"PENDING\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/payments/webhooks/infinitepay : Handle InfinitePay payment webhook
     * Receives an InfinitePay approved-payment notification, confirms it through payment_check and reconciles the internal sales order.
     *
     * @param infinitePayWebhookRequest  (required)
     * @return Webhook processed (status code 200)
     *         or Webhook rejected; InfinitePay may retry the notification (status code 400)
     * @see PaymentsApi#handleInfinitePayWebhook
     */
    default ResponseEntity<InfinitePayWebhookResponse> handleInfinitePayWebhook(InfinitePayWebhookRequest infinitePayWebhookRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"success\" : true, \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"success\" : true, \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
