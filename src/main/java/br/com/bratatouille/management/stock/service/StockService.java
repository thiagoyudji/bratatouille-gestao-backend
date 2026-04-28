package br.com.bratatouille.management.stock.service;

import br.com.bratatouille.management.generated.model.StockAlertResponse;
import br.com.bratatouille.management.generated.model.StockMovementResponse;
import br.com.bratatouille.management.generated.model.StockResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.stock.domain.StockAlertData;
import br.com.bratatouille.management.stock.domain.StockAlertStatus;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.mapper.StockMapper;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public void addFromPurchase(Item item, BigDecimal quantity, Long purchaseId) {
        Stock stock = getOrCreateWithLock(item);

        stock.add(quantity);

        stockRepository.save(stock);

        stockMovementService.registerPurchaseEntry(item, quantity, purchaseId);
    }

    @Transactional
    public void removeForProduction(Item item, BigDecimal quantity, Long productionId) {
        Stock stock = getExistingWithLock(item);

        stock.remove(quantity);

        stockRepository.save(stock);

        stockMovementService.registerProductionConsumption(item, quantity, productionId);
    }

    @Transactional
    public void addFromProduction(Item item, BigDecimal quantity, Long productionId) {
        Stock stock = getOrCreateWithLock(item);

        stock.add(quantity);

        stockRepository.save(stock);

        stockMovementService.registerProductionOutput(item, quantity, productionId);
    }

    @Transactional
    public void removeForSale(Item item, BigDecimal quantity, Long salesOrderId) {
        Stock stock = getExistingWithLock(item);

        stock.remove(quantity);

        stockRepository.save(stock);

        stockMovementService.registerSaleOutput(item, quantity, salesOrderId);
    }

    @Transactional
    public void removeForOperationalLoss(Item item, BigDecimal quantity, Long operationalLossId) {
        Stock stock = getExistingWithLock(item);

        stock.remove(quantity);

        stockRepository.save(stock);

        stockMovementService.registerOperationalLoss(item, quantity, operationalLossId);
    }

    @Transactional
    public StockResponse adjustManually(Long itemId, BigDecimal newQuantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Stock stock = getOrCreateWithLock(item);

        BigDecimal previousQuantity = stock.getQuantity();
        BigDecimal difference = newQuantity.subtract(previousQuantity);

        stock.adjust(newQuantity);

        Stock saved = stockRepository.save(stock);

        if (difference.compareTo(BigDecimal.ZERO) != 0) {
            stockMovementService.registerManualAdjustment(item, difference);
        }

        return stockMapper.toResponse(saved);
    }

    public List<StockAlertResponse> findAlerts() {
        return stockRepository.findAll()
                .stream()
                .filter(stock -> stock.getItem().isActive())
                .map(this::buildAlert)
                .filter(alert -> alert != null)
                .map(stockMapper::toAlertResponse)
                .toList();
    }

    private Stock getExistingWithLock(Item item) {
        return stockRepository.findByItemIdForUpdate(item.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Stock not found for item: " + item.getName()
                ));
    }

    private Stock getOrCreateWithLock(Item item) {
        return stockRepository.findByItemIdForUpdate(item.getId())
                .orElseGet(() -> {
                    Stock stock = new Stock(item, BigDecimal.ZERO);
                    return stockRepository.save(stock);
                });
    }

    private StockAlertData buildAlert(Stock stock) {
        BigDecimal quantity = stock.getQuantity();
        BigDecimal lowThreshold = stock.getItem().getLowStockThreshold();
        BigDecimal criticalThreshold = stock.getItem().getCriticalStockThreshold();

        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            return new StockAlertData(
                    stock.getItem(),
                    quantity,
                    lowThreshold,
                    criticalThreshold,
                    StockAlertStatus.NEAR_ZERO
            );
        }

        if (criticalThreshold != null && quantity.compareTo(criticalThreshold) <= 0) {
            return new StockAlertData(
                    stock.getItem(),
                    quantity,
                    lowThreshold,
                    criticalThreshold,
                    StockAlertStatus.CRITICAL
            );
        }

        if (lowThreshold != null && quantity.compareTo(lowThreshold) <= 0) {
            return new StockAlertData(
                    stock.getItem(),
                    quantity,
                    lowThreshold,
                    criticalThreshold,
                    StockAlertStatus.LOW
            );
        }

        return null;
    }
}