package br.com.bratatouille.management.payment.controller;

import br.com.bratatouille.management.generated.api.PaymentsApiDelegate;
import br.com.bratatouille.management.generated.model.InfinitePayCheckoutResponse;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.InfinitePayWebhookResponse;
import br.com.bratatouille.management.payment.service.InfinitePayCheckoutService;
import br.com.bratatouille.management.payment.service.InfinitePayWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.NoSuchElementException;

@Component
public class PaymentsApiDelegateImpl implements PaymentsApiDelegate {

    private final InfinitePayCheckoutService infinitePayCheckoutService;
    private final InfinitePayWebhookService infinitePayWebhookService;

    public PaymentsApiDelegateImpl(
            InfinitePayCheckoutService infinitePayCheckoutService,
            InfinitePayWebhookService infinitePayWebhookService
    ) {
        this.infinitePayCheckoutService = infinitePayCheckoutService;
        this.infinitePayWebhookService = infinitePayWebhookService;
    }

    @Override
    public ResponseEntity<InfinitePayCheckoutResponse> createInfinitePayCheckout(SalesOrderCreateRequest request) {
        return ResponseEntity.ok(infinitePayCheckoutService.create(request));
    }

    @Override
    public ResponseEntity<InfinitePayWebhookResponse> handleInfinitePayWebhook(InfinitePayWebhookRequest request) {
        try {
            infinitePayWebhookService.handle(request);
            return ResponseEntity.ok(webhookResponse(true, null));
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException | RestClientException exception) {
            return ResponseEntity.badRequest().body(webhookResponse(false, exception.getMessage()));
        }
    }

    private InfinitePayWebhookResponse webhookResponse(boolean success, String message) {
        InfinitePayWebhookResponse response = new InfinitePayWebhookResponse();
        response.setSuccess(success);
        response.setMessage(message);
        return response;
    }
}
