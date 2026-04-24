package br.com.bratatouille.management.partner.controller;

import br.com.bratatouille.management.generated.api.PartnersApiDelegate;
import br.com.bratatouille.management.generated.model.CreatePartnerRequest;
import br.com.bratatouille.management.generated.model.PartnerResponse;
import br.com.bratatouille.management.partner.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public class PartnerApiDelegateImpl implements PartnersApiDelegate {

    private final PartnerService partnerService;

    public PartnerApiDelegateImpl(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<PartnerResponse> create(@RequestBody CreatePartnerRequest request) {
        return ResponseEntity.ok(partnerService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PartnerResponse>> findAll() {
        return ResponseEntity.ok(partnerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.findById(id));
    }
}