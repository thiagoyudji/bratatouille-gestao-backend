package br.com.bratatouille.management.stock.service;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.stock.entity.StockMovement;
import br.com.bratatouille.management.stock.entity.StockMovementType;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    public void registerPurchaseEntry(Item item, BigDecimal quantity) {
        registerMovement(item, quantity, StockMovementType.PURCHASE_ENTRY);
    }

    public void registerProductionConsumption(Item item, BigDecimal quantity) {
        registerMovement(item, quantity.negate(), StockMovementType.PRODUCTION_CONSUMPTION);
    }

    public void registerProductionOutput(Item item, BigDecimal quantity) {
        registerMovement(item, quantity, StockMovementType.PRODUCTION_OUTPUT);
    }

    public void registerManualAdjustment(Item item, BigDecimal difference) {
        registerMovement(item, difference, StockMovementType.MANUAL_ADJUSTMENT);
    }

    private void registerMovement(Item item, BigDecimal quantity, StockMovementType type) {
        StockMovement movement = new StockMovement(item, quantity, type);
        stockMovementRepository.save(movement);
    }

    public void registerSaleOutput(Item item, BigDecimal quantity) {
        registerMovement(item, quantity.negate(), StockMovementType.SALE_OUTPUT);
    }

    public void registerOperationalLoss(Item item, BigDecimal quantity) {
        registerMovement(item, quantity.negate(), StockMovementType.LOSS_OUTPUT);
    }
}