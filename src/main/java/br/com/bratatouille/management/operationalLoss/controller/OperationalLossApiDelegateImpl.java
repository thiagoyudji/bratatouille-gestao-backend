package br.com.bratatouille.management.operationalLoss.controller;

import br.com.bratatouille.management.generated.api.OperationalLossesApiDelegate;
import br.com.bratatouille.management.generated.model.OperationalLossCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalLossResponse;
import br.com.bratatouille.management.operationalLoss.service.OperationalLossService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationalLossApiDelegateImpl implements OperationalLossesApiDelegate {

    private final OperationalLossService operationalLossService;

    public OperationalLossApiDelegateImpl(OperationalLossService operationalLossService) {
        this.operationalLossService = operationalLossService;
    }

    @Override
    public ResponseEntity<OperationalLossResponse> createOperationalLoss(OperationalLossCreateRequest request) {
        return ResponseEntity.ok(operationalLossService.create(request));
    }

    @Override
    public ResponseEntity<List<OperationalLossResponse>> findAllOperationalLosses() {
        return ResponseEntity.ok(operationalLossService.findAll());
    }

    @Override
    public ResponseEntity<OperationalLossResponse> findOperationalLossById(Long id) {
        return ResponseEntity.ok(operationalLossService.findById(id));
    }
}