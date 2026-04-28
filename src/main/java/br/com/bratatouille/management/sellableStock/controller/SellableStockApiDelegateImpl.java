package br.com.bratatouille.management.sellableStock.controller;

import br.com.bratatouille.management.generated.api.SellableStocksApiDelegate;
import br.com.bratatouille.management.generated.model.SellableStockResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellableStockApiDelegateImpl implements SellableStocksApiDelegate {

    private final SellableStockService sellableStockService;

    public SellableStockApiDelegateImpl(SellableStockService sellableStockService) {
        this.sellableStockService = sellableStockService;
    }

    @Override
    public ResponseEntity<List<SellableStockResponse>> findAllSellableStocks() {
        return ResponseEntity.ok(sellableStockService.findAll());
    }

    @Override
    public ResponseEntity<SellableStockResponse> findSellableStockByItemId(Long itemId) {
        return ResponseEntity.ok(sellableStockService.findByItemId(itemId));
    }

    @Override
    public ResponseEntity<SellableStockResponse> upsertSellableStock(Long itemId, SellableStockUpsertRequest request) {
        return ResponseEntity.ok(sellableStockService.upsert(itemId, request));
    }
}