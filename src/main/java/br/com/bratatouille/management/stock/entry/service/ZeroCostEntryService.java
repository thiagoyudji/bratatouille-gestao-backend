package br.com.bratatouille.management.stock.entry.service;

import br.com.bratatouille.management.generated.model.ZeroCostEntryCreateRequest;
import br.com.bratatouille.management.generated.model.ZeroCostEntryResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.stock.entry.entity.ZeroCostEntry;
import br.com.bratatouille.management.stock.entry.entity.ZeroCostEntryReason;
import br.com.bratatouille.management.stock.entry.mapper.ZeroCostEntryMapper;
import br.com.bratatouille.management.stock.entry.repository.ZeroCostEntryRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ZeroCostEntryService {

    private final ZeroCostEntryRepository repository;
    private final ItemRepository itemRepository;
    private final StockService stockService;
    private final ZeroCostEntryMapper mapper;

    public ZeroCostEntryService(
            ZeroCostEntryRepository repository,
            ItemRepository itemRepository,
            StockService stockService,
            ZeroCostEntryMapper mapper
    ) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.stockService = stockService;
        this.mapper = mapper;
    }

    @Transactional
    public ZeroCostEntryResponse create(ZeroCostEntryCreateRequest request) {
        validate(request);

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        ZeroCostEntry entry = ZeroCostEntry.create(
                item,
                request.getQuantity(),
                ZeroCostEntryReason.valueOf(request.getReason().name()),
                request.getNote()
        );

        ZeroCostEntry saved = repository.save(entry);

        stockService.addZeroCostEntry(
                item,
                request.getQuantity(),
                saved.getId()
        );

        return mapper.toResponse(saved);
    }

    public List<ZeroCostEntryResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ZeroCostEntryResponse findById(Long id) {
        ZeroCostEntry entry = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zero cost entry not found"));

        return mapper.toResponse(entry);
    }

    private void validate(ZeroCostEntryCreateRequest request) {
        if (request.getItemId() == null) {
            throw new IllegalArgumentException("itemId is required");
        }

        if (request.getQuantity() == null || request.getQuantity().signum() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (request.getReason() == null) {
            throw new IllegalArgumentException("reason is required");
        }
    }
}