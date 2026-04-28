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

    public void registerPurchaseEntry(Item item, BigDecimal quantity, Long purchaseId) {
        registerMovement(item, quantity, StockMovementType.PURCHASE_ENTRY, purchaseId);
    }

    public void registerProductionConsumption(Item item, BigDecimal quantity, Long productionId) {
        registerMovement(item, quantity.negate(), StockMovementType.PRODUCTION_CONSUMPTION, productionId);
    }

    public void registerProductionOutput(Item item, BigDecimal quantity, Long productionId) {
        registerMovement(item, quantity, StockMovementType.PRODUCTION_OUTPUT, productionId);
    }

    public void registerManualAdjustment(Item item, BigDecimal difference) {
        registerMovement(item, difference, StockMovementType.MANUAL_ADJUSTMENT, null);
    }

    public void registerSaleOutput(Item item, BigDecimal quantity, Long salesOrderId) {
        registerMovement(item, quantity.negate(), StockMovementType.SALE_OUTPUT, salesOrderId);
    }

    public void registerOperationalLoss(Item item, BigDecimal quantity, Long operationalLossId) {
        registerMovement(item, quantity.negate(), StockMovementType.LOSS_OUTPUT, operationalLossId);
    }

    private void registerMovement(Item item, BigDecimal quantity, StockMovementType type, Long sourceId) {
        StockMovement movement = new StockMovement(item, quantity, type, sourceId);
        stockMovementRepository.save(movement);
    }
}