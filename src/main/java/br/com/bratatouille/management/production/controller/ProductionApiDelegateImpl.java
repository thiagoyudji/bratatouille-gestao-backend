package br.com.bratatouille.management.production.controller;

import br.com.bratatouille.management.generated.api.ProductionsApiDelegate;
import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.production.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductionApiDelegateImpl implements ProductionsApiDelegate {

    private final ProductionService productionService;

    public ProductionApiDelegateImpl(ProductionService productionService) {
        this.productionService = productionService;
    }

    @Override
    public ResponseEntity<ProductionResponse> createProduction(ProductionCreateRequest request) {
        return ResponseEntity.ok(productionService.create(request));
    }

    @Override
    public ResponseEntity<List<ProductionResponse>> findAllProductions() {
        return ResponseEntity.ok(productionService.findAll());
    }

    @Override
    public ResponseEntity<ProductionResponse> findProductionById(Long id) {
        return ResponseEntity.ok(productionService.findById(id));
    }
}