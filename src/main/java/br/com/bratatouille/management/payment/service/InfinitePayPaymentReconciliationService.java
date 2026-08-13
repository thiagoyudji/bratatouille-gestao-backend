package br.com.bratatouille.management.payment.service;

import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway.InfinitePayPaymentVerification;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.entity.SalesPaymentStatus;
import br.com.bratatouille.management.sales.mapper.SalesOrderMapper;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.NoSuchElementException;

@Service
public class InfinitePayPaymentReconciliationService {

    private static final String PROVIDER_NAME = "INFINITEPAY";
    private static final String PROVIDER_STATUS = "PAID";

    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderMapper salesOrderMapper;

    public InfinitePayPaymentReconciliationService(
            SalesOrderRepository salesOrderRepository,
            SalesOrderMapper salesOrderMapper
    ) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderMapper = salesOrderMapper;
    }

    @Transactional
    public SalesOrderResponse reconcile(
            Long orderId,
            InfinitePayWebhookRequest request,
            InfinitePayPaymentVerification verification
    ) {
        SalesOrder salesOrder = salesOrderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new NoSuchElementException("Sales order not found"));

        validateOrder(salesOrder, request, verification);

        if (salesOrder.isPaymentFinalized()) {
            if (salesOrder.getPaymentStatus() == SalesPaymentStatus.APPROVED
                    && PROVIDER_NAME.equals(salesOrder.getPaymentProvider())
                    && request.getTransactionNsu().equals(salesOrder.getPaymentProviderTransactionId())) {
                return salesOrderMapper.toResponse(salesOrder);
            }
            throw new IllegalStateException("Sales order payment is already finalized");
        }

        salesOrderRepository.findByPaymentProviderTransactionId(request.getTransactionNsu())
                .filter(existing -> !existing.getId().equals(salesOrder.getId()))
                .ifPresent(existing -> {
                    throw new IllegalStateException("InfinitePay transaction is already linked to another sales order");
                });

        salesOrder.updatePayment(
                SalesPaymentStatus.APPROVED,
                PROVIDER_NAME,
                request.getTransactionNsu(),
                PROVIDER_STATUS,
                request.getReceiptUrl(),
                salesOrder.getPaymentCheckoutUrl(),
                request.getInvoiceSlug(),
                LocalDateTime.now(ZoneOffset.UTC)
        );

        try {
            return salesOrderMapper.toResponse(salesOrderRepository.saveAndFlush(salesOrder));
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException("InfinitePay transaction is already linked to another sales order", exception);
        }
    }

    private void validateOrder(
            SalesOrder salesOrder,
            InfinitePayWebhookRequest request,
            InfinitePayPaymentVerification verification
    ) {
        long expectedAmount = salesOrder.getTotalAmount()
                .movePointRight(2)
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();

        if (verification.amount() != expectedAmount) {
            throw new IllegalArgumentException("InfinitePay payment amount does not match sales order total");
        }

        if (request.getAmount() != verification.amount()
                || request.getPaidAmount() != verification.paidAmount()) {
            throw new IllegalArgumentException("InfinitePay webhook amount does not match payment verification");
        }

        if (salesOrder.getPaymentInvoiceSlug() != null
                && !salesOrder.getPaymentInvoiceSlug().equals(request.getInvoiceSlug())) {
            throw new IllegalArgumentException("InfinitePay invoice does not match sales order checkout");
        }
    }
}
