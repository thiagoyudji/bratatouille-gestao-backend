package br.com.bratatouille.management.sales.mapper;

import br.com.bratatouille.management.generated.model.SalesOrderItemResponse;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.common.mapper.ApiResponseMapperSupport;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.entity.SalesOrderItem;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderMapper {

    public SalesOrderResponse toResponse(SalesOrder salesOrder) {
        SalesOrderResponse response = new SalesOrderResponse();

        response.setId(salesOrder.getId());
        response.setSaleDate(salesOrder.getSaleDate());
        response.setCustomerType(br.com.bratatouille.management.generated.model.SalesOrderCustomerType.valueOf(salesOrder.getCustomerType().name()));
        response.setCustomerName(salesOrder.getCustomerName());
        response.setCustomerEmail(salesOrder.getCustomerEmail());
        response.setCustomerPhone(salesOrder.getCustomerPhone());
        response.setDeliveryAddress(toDeliveryAddress(salesOrder));
        response.setNote(salesOrder.getNote());
        response.setPaymentStatus(SalesOrderResponse.PaymentStatusEnum.valueOf(salesOrder.getPaymentStatus().name()));
        response.setPaymentProvider(salesOrder.getPaymentProvider());
        response.setPaymentProviderTransactionId(salesOrder.getPaymentProviderTransactionId());
        response.setPaymentProviderStatus(salesOrder.getPaymentProviderStatus());
        response.setPaymentReceiptUrl(salesOrder.getPaymentReceiptUrl());
        response.setPaymentCheckoutUrl(salesOrder.getPaymentCheckoutUrl());
        response.setPaymentInvoiceSlug(salesOrder.getPaymentInvoiceSlug());
        response.setPaidAt(ApiResponseMapperSupport.toUtc(salesOrder.getPaidAt()));
        response.setTotalAmount(salesOrder.getTotalAmount());
        response.setTotalCost(salesOrder.getTotalCost());
        response.setGrossProfit(salesOrder.getGrossProfit());
        response.setCreatedAt(ApiResponseMapperSupport.toUtc(salesOrder.getCreatedAt()));

        response.setItems(
                salesOrder.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );

        return response;
    }

    private SalesOrderItemResponse toItemResponse(SalesOrderItem item) {
        SalesOrderItemResponse response = new SalesOrderItemResponse();

        response.setId(item.getId());
        response.setItemId(item.getItem().getId());
        response.setItemName(item.getItem().getName());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setUnitPricePf(item.getUnitPricePf());
        response.setUnitPricePj(item.getUnitPricePj());
        response.setCostIncomplete(item.getCostIncomplete());
        response.setTotalPrice(item.getTotalPrice());
        response.setUnitCost(item.getUnitCost());
        response.setTotalCost(item.getTotalCost());
        response.setGrossProfit(item.getGrossProfit());

        return response;
    }

    private br.com.bratatouille.management.generated.model.SalesOrderCustomerAddress toDeliveryAddress(SalesOrder salesOrder) {
        if (salesOrder.getDeliveryZipCode() == null
                && salesOrder.getDeliveryStreet() == null
                && salesOrder.getDeliveryNumber() == null
                && salesOrder.getDeliveryNeighborhood() == null
                && salesOrder.getDeliveryState() == null
                && salesOrder.getDeliveryCity() == null
                && salesOrder.getDeliveryComplement() == null) {
            return null;
        }

        return new br.com.bratatouille.management.generated.model.SalesOrderCustomerAddress()
                .label(null)
                .zipCode(salesOrder.getDeliveryZipCode())
                .street(salesOrder.getDeliveryStreet())
                .number(salesOrder.getDeliveryNumber())
                .neighborhood(salesOrder.getDeliveryNeighborhood())
                .state(salesOrder.getDeliveryState())
                .city(salesOrder.getDeliveryCity())
                .complement(salesOrder.getDeliveryComplement())
                .defaultAddress(true);
    }
}
