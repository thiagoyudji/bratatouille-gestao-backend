package br.com.bratatouille.management.stock.service;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMovementService stockMovementService;

    public StockService(
            StockRepository stockRepository,
            StockMovementService stockMovementService
    ) {
        this.stockRepository = stockRepository;
        this.stockMovementService = stockMovementService;
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

    public void adjustManually(Item item, BigDecimal newQuantity) {
        Stock stock = getOrCreate(item);

        BigDecimal previousQuantity = stock.getQuantity();
        BigDecimal difference = newQuantity.subtract(previousQuantity);

        stock.adjust(newQuantity);

        stockRepository.save(stock);

        stockMovementService.registerManualAdjustment(item, difference);
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