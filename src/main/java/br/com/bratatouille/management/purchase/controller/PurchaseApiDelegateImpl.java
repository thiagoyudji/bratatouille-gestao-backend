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

    @Override
    public ResponseEntity<PurchaseResponse> createPurchase(PurchaseCreateRequest request) {
        return ResponseEntity.ok(purchaseService.create(request));
    }

    @Override
    public ResponseEntity<List<PurchaseResponse>> findAllPurchases() {
        return ResponseEntity.ok(purchaseService.findAll());
    }

    @Override
    public ResponseEntity<PurchaseResponse> findPurchaseById(Long id) {
        return ResponseEntity.ok(purchaseService.findById(id));
    }
}
