package br.com.bratatouille.management.cost.service;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.purchase.repository.PurchaseItemRepository;
import br.com.bratatouille.management.stock.entry.repository.ZeroCostEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CostService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductionRepository productionRepository;
    private final ZeroCostEntryRepository zeroCostEntryRepository;

    public CostService(
            PurchaseItemRepository purchaseItemRepository,
            ProductionRepository productionRepository,
            ZeroCostEntryRepository zeroCostEntryRepository
    ) {
        this.purchaseItemRepository = purchaseItemRepository;
        this.productionRepository = productionRepository;
        this.zeroCostEntryRepository = zeroCostEntryRepository;
    }

    public BigDecimal findUnitCost(Item item) {
        if (item.getType() == ItemType.FINISHED_PRODUCT) {
            return findFinishedProductUnitCost(item);
        }

        return findPurchasedItemUnitCost(item);
    }

    private BigDecimal findPurchasedItemUnitCost(Item item) {
        BigDecimal purchaseTotalValue = purchaseItemRepository.sumTotalValueByItemId(item.getId());
        BigDecimal purchaseQuantity = purchaseItemRepository.sumQuantityByItemId(item.getId());
        BigDecimal zeroCostQuantity = zeroCostEntryRepository.sumQuantityByItemId(item.getId());

        BigDecimal totalQuantity = purchaseQuantity.add(zeroCostQuantity);

        if (totalQuantity.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return purchaseTotalValue.divide(totalQuantity, 6, RoundingMode.HALF_UP);
    }

    private BigDecimal findFinishedProductUnitCost(Item item) {
        BigDecimal productionTotalCost = productionRepository.sumTotalCostByOutputItemId(item.getId());
        BigDecimal productionQuantity = productionRepository.sumProducedQuantityByOutputItemId(item.getId());
        BigDecimal zeroCostQuantity = zeroCostEntryRepository.sumQuantityByItemId(item.getId());

        BigDecimal totalQuantity = productionQuantity.add(zeroCostQuantity);

        if (totalQuantity.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return productionTotalCost.divide(totalQuantity, 6, RoundingMode.HALF_UP);
    }

    public BigDecimal findRequiredUnitCost(Item item) {
        BigDecimal unitCost = findUnitCost(item);

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cost history not found for item: " + item.getName());
        }

        return unitCost;
    }

    public BigDecimal findUnitCostOrZero(Item item) {
        BigDecimal unitCost = findUnitCost(item);

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return unitCost;
    }

    public boolean isCostIncomplete(BigDecimal unitCost) {
        return unitCost == null || unitCost.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean hasZeroCostImpact(Item item) {
        return zeroCostEntryRepository.sumQuantityByItemId(item.getId()).compareTo(BigDecimal.ZERO) > 0;
    }
}