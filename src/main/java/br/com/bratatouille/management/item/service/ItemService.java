package br.com.bratatouille.management.item.service;

import br.com.bratatouille.management.generated.model.CreateItemRequest;
import br.com.bratatouille.management.generated.model.ItemResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.mapper.ItemMapper;
import br.com.bratatouille.management.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository,
                       ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemResponse create(CreateItemRequest request) {
        if (request.getPricePf() == null || request.getPricePj() == null) {
            throw new IllegalArgumentException("item prices are required");
        }

        Item item = new Item(
                request.getName(),
                ItemType.valueOf(request.getType().name()),
                UnitType.valueOf(request.getBaseUnit().name()),
                request.getLowStockThreshold(),
                request.getCriticalStockThreshold(),
                request.getPricePf(),
                request.getPricePj()
        );

        Item saved = itemRepository.save(item);

        return itemMapper.toResponse(saved);
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll()
            .stream()
            .map(itemMapper::toResponse)
            .toList();
    }

    public ItemResponse findById(Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Item not found"));

        return itemMapper.toResponse(item);
    }

}
