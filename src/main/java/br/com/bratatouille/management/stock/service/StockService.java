package br.com.bratatouille.management.stock.service;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.mapper.StockMapper;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.generated.model.StockMovementResponse;
import br.com.bratatouille.management.generated.model.StockResponse;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final StockMovementRepository stockMovementRepository;
    private final StockMovementService stockMovementService;
    private final ItemRepository itemRepository;

    public StockService(
            StockRepository stockRepository,
            StockMapper stockMapper,
            StockMovementRepository stockMovementRepository,
            StockMovementService stockMovementService,
            ItemRepository itemRepository
    ) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementService = stockMovementService;
        this.itemRepository = itemRepository;
    }



    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(stockMapper::toResponse)
                .toList();
    }

    public StockResponse findByItemId(Long itemId) {
        Stock stock = stockRepository.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        return stockMapper.toResponse(stock);
    }

    public List<StockMovementResponse> findMovements() {
        return stockMovementRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(stockMapper::toMovementResponse)
                .toList();
    }

    public void addFromPurchase(Item item, BigDecimal quantity) {
        Stock stock = getOrCreate(item);

        stock.add(quantity);

        stockRepository.save(stock);

        stockMovementService.registerPurchaseEntry(item, quantity);
    }

    public void removeForProduction(Item item, BigDecimal quantity) {
        Stock stock = getOrCreate(item);

        stock.remove(quantity);

        stockRepository.save(stock);

        stockMovementService.registerProductionConsumption(item, quantity);
    }

    public void addFromProduction(Item item, BigDecimal quantity) {
        Stock stock = getOrCreate(item);

        stock.add(quantity);

        stockRepository.save(stock);

        stockMovementService.registerProductionOutput(item, quantity);
    }

    public StockResponse adjustManually(Long itemId, BigDecimal newQuantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Stock stock = getOrCreate(item);

        BigDecimal previousQuantity = stock.getQuantity();
        BigDecimal difference = newQuantity.subtract(previousQuantity);

        stock.adjust(newQuantity);

        Stock saved = stockRepository.save(stock);

        stockMovementService.registerManualAdjustment(item, difference);

        return stockMapper.toResponse(saved);
    }

    private Stock getOrCreate(Item item) {
        return stockRepository.findByItemId(item.getId())
                .orElseGet(() -> {
                    Stock stock = new Stock(
                            item,
                            BigDecimal.ZERO
                    );
                    return stockRepository.save(stock);
                });
    }
}