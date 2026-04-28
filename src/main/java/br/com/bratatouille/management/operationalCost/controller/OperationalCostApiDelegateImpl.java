package br.com.bratatouille.management.operationalCost.controller;

import br.com.bratatouille.management.generated.api.OperationalCostsApiDelegate;
import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostResponse;
import br.com.bratatouille.management.operationalCost.service.OperationalCostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationalCostApiDelegateImpl implements OperationalCostsApiDelegate {

    private final OperationalCostService operationalCostService;

    public OperationalCostApiDelegateImpl(OperationalCostService operationalCostService) {
        this.operationalCostService = operationalCostService;
    }

    @Override
    public ResponseEntity<OperationalCostResponse> createOperationalCost(OperationalCostCreateRequest operationalCostCreateRequest) {
        return ResponseEntity.ok(operationalCostService.create(operationalCostCreateRequest));
    }

    @Override
    public ResponseEntity<List<OperationalCostResponse>> findAllOperationalCosts() {
        return ResponseEntity.ok(operationalCostService.findAll());
    }

    @Override
    public ResponseEntity<OperationalCostResponse> findOperationalCostById(Long id) {
        return ResponseEntity.ok(operationalCostService.findById(id));
    }
}