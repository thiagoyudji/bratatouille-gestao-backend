package br.com.bratatouille.management.item.controller;

import br.com.bratatouille.management.generated.api.ItemsApiDelegate;
import br.com.bratatouille.management.generated.model.CreateItemRequest;
import br.com.bratatouille.management.generated.model.ItemResponse;
import br.com.bratatouille.management.generated.model.UpdateItemRequest;
import br.com.bratatouille.management.item.mapper.ItemMapper;
import br.com.bratatouille.management.item.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public class ItemApiDelegateImpl implements ItemsApiDelegate {

    private final ItemService itemService;

    public ItemApiDelegateImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ResponseEntity<ItemResponse> createItem(CreateItemRequest request) {
        return ResponseEntity.ok(itemService.create(request));
    }

    @Override
    public ResponseEntity<List<ItemResponse>> findAllItems() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @Override
    public ResponseEntity<ItemResponse> findItemById(Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @Override
    public ResponseEntity<ItemResponse> updateItem(Long id, UpdateItemRequest request) {
        return ResponseEntity.ok(itemService.update(id, request));
    }

    @Override
    public ResponseEntity<ItemResponse> deactivateItem(Long id) {
        return ResponseEntity.ok(itemService.deactivate(id));
    }
}
