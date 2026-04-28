package br.com.bratatouille.management.sales.mapper;

import br.com.bratatouille.management.generated.model.SalesOrderItemResponse;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.entity.SalesOrderItem;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class SalesOrderMapper {

    public SalesOrderResponse toResponse(SalesOrder salesOrder) {
        SalesOrderResponse response = new SalesOrderResponse();

        response.setId(salesOrder.getId());
        response.setSaleDate(salesOrder.getSaleDate());
        response.setCustomerName(salesOrder.getCustomerName());
        response.setNote(salesOrder.getNote());
        response.setTotalAmount(salesOrder.getTotalAmount());
        response.setTotalCost(salesOrder.getTotalCost());
        response.setGrossProfit(salesOrder.getGrossProfit());
        response.setCreatedAt(salesOrder.getCreatedAt().atOffset(ZoneOffset.UTC));

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
        response.setTotalPrice(item.getTotalPrice());
        response.setUnitCost(item.getUnitCost());
        response.setTotalCost(item.getTotalCost());
        response.setGrossProfit(item.getGrossProfit());

        return response;
    }
}