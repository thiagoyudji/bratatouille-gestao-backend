package br.com.bratatouille.management.sales.controller;

import br.com.bratatouille.management.generated.api.SalesOrdersApiDelegate;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.sales.service.SalesOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesOrderApiDelegateImpl implements SalesOrdersApiDelegate {

    private final SalesOrderService salesOrderService;

    public SalesOrderApiDelegateImpl(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @Override
    public ResponseEntity<SalesOrderResponse> createSalesOrder(SalesOrderCreateRequest request) {
        return ResponseEntity.ok(salesOrderService.create(request));
    }

    @Override
    public ResponseEntity<List<SalesOrderResponse>> findAllSalesOrders() {
        return ResponseEntity.ok(salesOrderService.findAll());
    }

    @Override
    public ResponseEntity<SalesOrderResponse> findSalesOrderById(Long id) {
        return ResponseEntity.ok(salesOrderService.findById(id));
    }
}