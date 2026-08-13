package br.com.bratatouille.management.payment.service;

import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

@Service
public class InfinitePayWebhookService {

    private final InfinitePayPaymentVerificationGateway paymentVerificationGateway;
    private final InfinitePayPaymentReconciliationService paymentReconciliationService;

    public InfinitePayWebhookService(
            InfinitePayPaymentVerificationGateway paymentVerificationGateway,
            InfinitePayPaymentReconciliationService paymentReconciliationService
    ) {
        this.paymentVerificationGateway = paymentVerificationGateway;
        this.paymentReconciliationService = paymentReconciliationService;
    }

    public SalesOrderResponse handle(InfinitePayWebhookRequest request) {
        validate(request);

        Long orderId;
        try {
            orderId = Long.valueOf(request.getOrderNsu());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("orderNsu must identify a valid sales order", exception);
        }

        var verification = paymentVerificationGateway.verify(
                request.getOrderNsu(),
                request.getTransactionNsu(),
                request.getInvoiceSlug()
        );

        if (!verification.paid()) {
            throw new IllegalArgumentException("InfinitePay payment is not confirmed");
        }

        return paymentReconciliationService.reconcile(orderId, request, verification);
    }

    private void validate(InfinitePayWebhookRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (!StringUtils.hasText(request.getOrderNsu())) {
            throw new IllegalArgumentException("orderNsu is required");
        }
        if (!StringUtils.hasText(request.getTransactionNsu())) {
            throw new IllegalArgumentException("transactionNsu is required");
        }
        if (!StringUtils.hasText(request.getInvoiceSlug())) {
            throw new IllegalArgumentException("invoiceSlug is required");
        }
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (request.getPaidAmount() == null || request.getPaidAmount() <= 0) {
            throw new IllegalArgumentException("paidAmount must be greater than zero");
        }
    }
}
