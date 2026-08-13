package br.com.bratatouille.management.payment.service;

import br.com.bratatouille.management.generated.model.InfinitePayCheckoutResponse;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.customer.service.CustomerProfileService;
import br.com.bratatouille.management.payment.gateway.InfinitePayCheckoutGateway;
import br.com.bratatouille.management.sales.service.SalesOrderService;
import org.springframework.stereotype.Service;

@Service
public class InfinitePayCheckoutService {

    private static final String PROVIDER = "INFINITEPAY";

    private final SalesOrderService salesOrderService;
    private final InfinitePayCheckoutGateway infinitePayCheckoutGateway;
    private final CustomerProfileService customerProfileService;

    public InfinitePayCheckoutService(
            SalesOrderService salesOrderService,
            InfinitePayCheckoutGateway infinitePayCheckoutGateway,
            CustomerProfileService customerProfileService
    ) {
        this.salesOrderService = salesOrderService;
        this.infinitePayCheckoutGateway = infinitePayCheckoutGateway;
        this.customerProfileService = customerProfileService;
    }

    public InfinitePayCheckoutResponse create(SalesOrderCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }

        applyAuthoritativeCustomer(request);

        var salesOrderResponse = salesOrderService.create(request);
        var checkoutResult = infinitePayCheckoutGateway.createCheckout(salesOrderResponse);

        salesOrderService.updateCheckoutMetadata(
                salesOrderResponse.getId(),
                checkoutResult.checkoutUrl(),
                checkoutResult.invoiceSlug()
        );

        InfinitePayCheckoutResponse response = new InfinitePayCheckoutResponse();
        response.setProvider(PROVIDER);
        response.setOrderId(salesOrderResponse.getId());
        response.setCheckoutUrl(checkoutResult.checkoutUrl());
        response.setInvoiceSlug(checkoutResult.invoiceSlug());
        response.setPaymentStatus(
                br.com.bratatouille.management.generated.model.InfinitePayCheckoutResponse.PaymentStatusEnum.valueOf(
                        salesOrderResponse.getPaymentStatus().name()
                )
        );

        return response;
    }

    private void applyAuthoritativeCustomer(SalesOrderCreateRequest request) {
        var authenticatedProfile = customerProfileService.findAuthenticatedProfile();

        if (authenticatedProfile.isEmpty()) {
            request.setCustomerType(SalesOrderCustomerType.GUEST);
            return;
        }

        var profile = authenticatedProfile.get();
        request.setCustomerType(SalesOrderCustomerType.valueOf(profile.customerType().name()));
        request.setCustomerName(profile.fullName());
        request.setCustomerEmail(profile.email());
        request.setCustomerPhone(profile.phone());
    }
}
