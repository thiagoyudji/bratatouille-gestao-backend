package br.com.bratatouille.management.productionSimulation.controller;

import br.com.bratatouille.management.generated.api.ProductionSimulationApiDelegate;
import br.com.bratatouille.management.generated.model.ProductionSimulationResponse;
import br.com.bratatouille.management.productionSimulation.service.ProductionSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductionSimulationApiDelegateImpl implements ProductionSimulationApiDelegate {

    private final ProductionSimulationService productionSimulationService;

    public ProductionSimulationApiDelegateImpl(ProductionSimulationService productionSimulationService) {
        this.productionSimulationService = productionSimulationService;
    }

    @Override
    public ResponseEntity<ProductionSimulationResponse> simulateProduction(Long recipeId, BigDecimal quantity) {
        return ResponseEntity.ok(productionSimulationService.simulate(recipeId, quantity));
    }
}