package br.com.bratatouille.management.payment.gateway;

public interface InfinitePayPaymentVerificationGateway {

    InfinitePayPaymentVerification verify(String orderNsu, String transactionNsu, String invoiceSlug);

    record InfinitePayPaymentVerification(
            boolean paid,
            long amount,
            long paidAmount,
            String captureMethod
    ) {
    }
}
