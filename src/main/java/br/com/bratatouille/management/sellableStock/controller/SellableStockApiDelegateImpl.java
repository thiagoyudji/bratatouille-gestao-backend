package br.com.bratatouille.management.sellableStock.controller;

import br.com.bratatouille.management.generated.api.SellableStocksApiDelegate;
import br.com.bratatouille.management.generated.model.SellableStockAdminResponse;
import br.com.bratatouille.management.generated.model.SellableStockCatalogResponse;
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
    public ResponseEntity<List<SellableStockCatalogResponse>> findAllSellableStocks() {
        return ResponseEntity.ok(sellableStockService.findAllCatalog());
    }

    @Override
    public ResponseEntity<SellableStockCatalogResponse> findSellableStockByItemId(Long itemId) {
        return ResponseEntity.ok(sellableStockService.findCatalogByItemId(itemId));
    }

    @Override
    public ResponseEntity<List<SellableStockAdminResponse>> findAllSellableStockAdmin() {
        return ResponseEntity.ok(sellableStockService.findAllAdmin());
    }

    @Override
    public ResponseEntity<SellableStockAdminResponse> findSellableStockAdminByItemId(Long itemId) {
        return ResponseEntity.ok(sellableStockService.findAdminByItemId(itemId));
    }

    @Override
    public ResponseEntity<SellableStockAdminResponse> upsertSellableStock(Long itemId, SellableStockUpsertRequest request) {
        return ResponseEntity.ok(sellableStockService.upsert(itemId, request));
    }
}
