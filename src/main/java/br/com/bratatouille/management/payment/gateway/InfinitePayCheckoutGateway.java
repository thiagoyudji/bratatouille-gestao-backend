package br.com.bratatouille.management.payment.gateway;

import br.com.bratatouille.management.generated.model.SalesOrderResponse;

public interface InfinitePayCheckoutGateway {

    InfinitePayCheckoutResult createCheckout(SalesOrderResponse salesOrderResponse);

    record InfinitePayCheckoutResult(String checkoutUrl, String invoiceSlug) {
    }
}
