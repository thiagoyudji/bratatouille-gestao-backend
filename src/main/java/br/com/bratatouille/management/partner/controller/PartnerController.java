package br.com.bratatouille.management.partner.controller;

import br.com.bratatouille.management.partner.dto.CreatePartnerRequest;
import br.com.bratatouille.management.partner.dto.PartnerResponse;
import br.com.bratatouille.management.partner.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
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