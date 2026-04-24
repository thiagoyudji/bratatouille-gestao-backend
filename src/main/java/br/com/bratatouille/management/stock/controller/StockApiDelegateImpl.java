package br.com.bratatouille.management.stock.controller;

import br.com.bratatouille.management.generated.api.StocksApiDelegate;
import br.com.bratatouille.management.generated.model.AdjustStockRequest;
import br.com.bratatouille.management.generated.model.StockMovementResponse;
import br.com.bratatouille.management.generated.model.StockResponse;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockApiDelegateImpl implements StocksApiDelegate {

    private final StockService stockService;

    public StockApiDelegateImpl(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public ResponseEntity<List<StockResponse>> findAllStocks() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @Override
    public ResponseEntity<StockResponse> findStockByItemId(Long itemId) {
        return ResponseEntity.ok(stockService.findByItemId(itemId));
    }

    @Override
    public ResponseEntity<List<StockMovementResponse>> findStockMovements() {
        return ResponseEntity.ok(stockService.findMovements());
    }

    @Override
    public ResponseEntity<StockResponse> adjustStockManually(Long itemId, AdjustStockRequest request) {
        return ResponseEntity.ok(stockService.adjustManually(itemId, request.getQuantity()));
    }
}