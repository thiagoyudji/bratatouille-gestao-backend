package br.com.bratatouille.management.item.service;

import br.com.bratatouille.management.item.dto.CreateItemRequest;
import br.com.bratatouille.management.item.dto.ItemResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponse create(CreateItemRequest request) {
        Item item = new Item(
                request.name(),
                request.type(),
                request.baseUnit()
        );

        Item saved = itemRepository.save(item);

        return ItemResponse.from(saved);
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public ItemResponse findById(Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        return toResponse(item);
    }

    private ItemResponse toResponse(Item item) {
        return new ItemResponse(
            item.getId(),
            item.getName(),
            item.getType(),
            item.getBaseUnit(),
            item.isActive(),
            item.getCreatedAt(),
            item.getUpdatedAt()
        );
    }
}