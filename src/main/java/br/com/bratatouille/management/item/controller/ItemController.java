package br.com.bratatouille.management.item.controller;

import br.com.bratatouille.management.item.dto.CreateItemRequest;
import br.com.bratatouille.management.item.dto.ItemResponse;
import br.com.bratatouille.management.item.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody CreateItemRequest request) {
        return ResponseEntity.ok(itemService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }
}