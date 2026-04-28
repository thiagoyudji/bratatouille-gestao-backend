package br.com.bratatouille.management.sellableStock.service;

import br.com.bratatouille.management.generated.model.SellableStockResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import br.com.bratatouille.management.sellableStock.mapper.SellableStockMapper;
import br.com.bratatouille.management.sellableStock.repository.SellableStockRepository;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SellableStockService {

    private final SellableStockRepository sellableStockRepository;
    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;
    private final SellableStockMapper sellableStockMapper;

    public SellableStockService(
            SellableStockRepository sellableStockRepository,
            ItemRepository itemRepository,
            StockRepository stockRepository,
            SellableStockMapper sellableStockMapper
    ) {
        this.sellableStockRepository = sellableStockRepository;
        this.itemRepository = itemRepository;
        this.stockRepository = stockRepository;
        this.sellableStockMapper = sellableStockMapper;
    }

    public List<SellableStockResponse> findAll() {
        return sellableStockRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public SellableStockResponse findByItemId(Long itemId) {
        SellableStock sellableStock = sellableStockRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Sellable stock not found"));

        return toResponse(sellableStock);
    }

    @Transactional
    public SellableStockResponse upsert(Long itemId, SellableStockUpsertRequest request) {
        validateRequest(request);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        validateFinishedProduct(item);

        Stock stock = stockRepository.findByItemId(itemId).orElse(null);

        validateAvailableQuantity(request, stock);

        SellableStock sellableStock = sellableStockRepository.findByItemId(itemId)
                .orElseGet(() -> SellableStock.create(
                        item,
                        request.getAvailableQuantity(),
                        request.getInfinite(),
                        request.getEnabled()
                ));

        sellableStock.update(
                request.getAvailableQuantity(),
                request.getInfinite(),
                request.getEnabled()
        );

        SellableStock saved = sellableStockRepository.save(sellableStock);

        return sellableStockMapper.toResponse(saved, stock);
    }

    private SellableStockResponse toResponse(SellableStock sellableStock) {
        Stock stock = stockRepository.findByItemId(sellableStock.getItem().getId()).orElse(null);

        return sellableStockMapper.toResponse(sellableStock, stock);
    }

    private void validateRequest(SellableStockUpsertRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        if (request.getInfinite() == null) {
            throw new IllegalArgumentException("infinite is required");
        }

        if (request.getEnabled() == null) {
            throw new IllegalArgumentException("enabled is required");
        }

        if (!request.getInfinite()
                && (request.getAvailableQuantity() == null || request.getAvailableQuantity().compareTo(BigDecimal.ZERO) < 0)) {
            throw new IllegalArgumentException("availableQuantity cannot be negative");
        }
    }

    private void validateFinishedProduct(Item item) {
        if (item.getType() != ItemType.FINISHED_PRODUCT) {
            throw new IllegalArgumentException("Only finished products can be sellable");
        }
    }

    private void validateAvailableQuantity(SellableStockUpsertRequest request, Stock stock) {
        if (Boolean.TRUE.equals(request.getInfinite())) {
            return;
        }

        BigDecimal currentStock = stock == null ? BigDecimal.ZERO : stock.getQuantity();

        if (request.getAvailableQuantity().compareTo(currentStock) > 0) {
            throw new IllegalArgumentException("availableQuantity cannot be greater than current stock");
        }
    }

    @Transactional
    public void decreaseAfterSale(Item item, BigDecimal quantity) {
        SellableStock sellableStock = sellableStockRepository.findByItemIdForUpdate(item.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sellable stock not configured for item: " + item.getName()
                ));

        if (!Boolean.TRUE.equals(sellableStock.getEnabled())) {
            throw new IllegalArgumentException("Sellable stock is disabled for item: " + item.getName());
        }

        sellableStock.decrease(quantity);

        sellableStockRepository.save(sellableStock);
    }

    @Transactional
    public void decreaseAfterLossIfConfigured(Item item, BigDecimal quantity) {
        sellableStockRepository.findByItemIdForUpdate(item.getId())
                .ifPresent(sellableStock -> {
                    if (!Boolean.TRUE.equals(sellableStock.getEnabled())) {
                        return;
                    }

                    sellableStock.decrease(quantity);

                    sellableStockRepository.save(sellableStock);
                });
    }
}