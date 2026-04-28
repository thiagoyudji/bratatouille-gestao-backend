package br.com.bratatouille.management.lot.controller;

import br.com.bratatouille.management.generated.api.LotsApiDelegate;
import br.com.bratatouille.management.generated.model.LotResponse;
import br.com.bratatouille.management.lot.service.LotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LotApiDelegateImpl implements LotsApiDelegate {

    private final LotService lotService;

    public LotApiDelegateImpl(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public ResponseEntity<List<LotResponse>> findAllLots() {
        return ResponseEntity.ok(lotService.findAll());
    }

    @Override
    public ResponseEntity<LotResponse> findLotById(Long id) {
        return ResponseEntity.ok(lotService.findById(id));
    }

    @Override
    public ResponseEntity<LotResponse> findLotByProductionId(Long productionId) {
        return ResponseEntity.ok(lotService.findByProductionId(productionId));
    }

    @Override
    public ResponseEntity<List<LotResponse>> findLotsByItemId(Long itemId) {
        return ResponseEntity.ok(lotService.findByItemId(itemId));
    }

    @Override
    public ResponseEntity<List<LotResponse>> findExpiringLots(LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok(lotService.findExpiringBetween(startDate, endDate));
    }
}