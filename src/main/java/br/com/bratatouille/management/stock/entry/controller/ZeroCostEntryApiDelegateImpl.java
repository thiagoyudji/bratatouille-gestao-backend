package br.com.bratatouille.management.stock.entry.controller;

import br.com.bratatouille.management.generated.api.ZeroCostEntriesApiDelegate;
import br.com.bratatouille.management.generated.model.ZeroCostEntryCreateRequest;
import br.com.bratatouille.management.generated.model.ZeroCostEntryResponse;
import br.com.bratatouille.management.stock.entry.service.ZeroCostEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZeroCostEntryApiDelegateImpl implements ZeroCostEntriesApiDelegate {

    private final ZeroCostEntryService zeroCostEntryService;

    public ZeroCostEntryApiDelegateImpl(ZeroCostEntryService zeroCostEntryService) {
        this.zeroCostEntryService = zeroCostEntryService;
    }

    @Override
    public ResponseEntity<ZeroCostEntryResponse> createZeroCostEntry(ZeroCostEntryCreateRequest request) {
        return ResponseEntity.ok(zeroCostEntryService.create(request));
    }

    @Override
    public ResponseEntity<List<ZeroCostEntryResponse>> findAllZeroCostEntries() {
        return ResponseEntity.ok(zeroCostEntryService.findAll());
    }

    @Override
    public ResponseEntity<ZeroCostEntryResponse> findZeroCostEntryById(Long id) {
        return ResponseEntity.ok(zeroCostEntryService.findById(id));
    }
}