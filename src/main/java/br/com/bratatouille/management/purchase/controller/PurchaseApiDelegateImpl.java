package br.com.bratatouille.management.purchase.controller;

import br.com.bratatouille.management.generated.api.PurchasesApiDelegate;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public class PurchaseApiDelegateImpl implements PurchasesApiDelegate {

    private final PurchaseService purchaseService;

    public PurchaseApiDelegateImpl(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> create(@RequestBody PurchaseCreateRequest request) {
        return ResponseEntity.ok(purchaseService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> findAll() {
        return ResponseEntity.ok(purchaseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.findById(id));
    }
}
