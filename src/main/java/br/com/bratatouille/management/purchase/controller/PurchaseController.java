package br.com.bratatouille.management.purchase.controller;

import br.com.bratatouille.management.purchase.dto.request.PurchaseCreateRequest;
import br.com.bratatouille.management.purchase.dto.response.PurchaseResponse;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
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