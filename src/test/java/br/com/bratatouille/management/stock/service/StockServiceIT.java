package br.com.bratatouille.management.stock.service;

import br.com.bratatouille.management.generated.model.StockAlertResponse;
import br.com.bratatouille.management.generated.model.StockResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.stock.entity.StockMovement;
import br.com.bratatouille.management.stock.entity.StockMovementType;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StockServiceIT {

    @Autowired
    private StockService stockService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Test
    void findAlertsClassifiesLowCriticalAndNearZeroStocks() {
        Item lowItem = saveItem("Low Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));
        Item criticalItem = saveItem("Critical Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));
        Item nearZeroItem = saveItem("Near Zero Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));
        Item inactiveItem = saveInactiveItem("Inactive Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));

        stockService.adjustManually(lowItem.getId(), new BigDecimal("4.000"));
        stockService.adjustManually(criticalItem.getId(), new BigDecimal("2.000"));
        stockService.adjustManually(nearZeroItem.getId(), new BigDecimal("0.000"));
        stockService.adjustManually(inactiveItem.getId(), new BigDecimal("0.000"));

        Map<Long, StockAlertResponse> alerts = stockService.findAlerts().stream()
                .filter(alert -> alert.getItemId().equals(lowItem.getId())
                        || alert.getItemId().equals(criticalItem.getId())
                        || alert.getItemId().equals(nearZeroItem.getId())
                        || alert.getItemId().equals(inactiveItem.getId()))
                .collect(Collectors.toMap(StockAlertResponse::getItemId, alert -> alert));

        assertEquals(3, alerts.size());
        assertEquals(StockAlertResponse.StatusEnum.LOW, alerts.get(lowItem.getId()).getStatus());
        assertEquals(StockAlertResponse.StatusEnum.CRITICAL, alerts.get(criticalItem.getId()).getStatus());
        assertEquals(StockAlertResponse.StatusEnum.NEAR_ZERO, alerts.get(nearZeroItem.getId()).getStatus());
        assertFalse(alerts.containsKey(inactiveItem.getId()));

        assertEquals(0, new BigDecimal("4.000").compareTo(stockRepository.findByItemId(lowItem.getId()).orElseThrow().getQuantity()));
    }

    @Test
    void adjustManuallyRejectsNegativeQuantity() {
        Item item = saveItem("Reject Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));

        assertThrows(IllegalArgumentException.class, () -> stockService.adjustManually(item.getId(), new BigDecimal("-1.000")));
    }

    @Test
    void adjustManuallyPersistsMovementAndCanBeListed() {
        Item item = saveItem("Adjust Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));

        StockResponse response = stockService.adjustManually(item.getId(), new BigDecimal("6.500"));

        assertEquals(0, new BigDecimal("6.500").compareTo(stockRepository.findByItemId(item.getId()).orElseThrow().getQuantity()));
        assertEquals(new BigDecimal("18.50"), response.getPricePf());
        assertEquals(new BigDecimal("24.90"), response.getPricePj());

        StockMovement movement = stockMovementRepository.findAll().stream()
                .filter(value -> value.getItem().getId().equals(item.getId()))
                .findFirst()
                .orElseThrow();

        assertEquals(StockMovementType.MANUAL_ADJUSTMENT, movement.getType());
        assertEquals(0, new BigDecimal("6.500").compareTo(movement.getQuantity()));
        assertEquals(null, movement.getSourceId());

        assertEquals(1, stockService.findMovements().stream()
                .filter(movementResponse -> movementResponse.getItemId().equals(item.getId()))
                .count());
    }

    @Test
    void findMovementsReturnsAllRegisteredMovementTypesForCriticalStockFlows() {
        Item item = saveItem("Movement Item", ItemType.INGREDIENT, new BigDecimal("5.000"), new BigDecimal("2.000"));

        stockService.addFromPurchase(item, new BigDecimal("5.000"), 101L);
        stockService.adjustManually(item.getId(), new BigDecimal("3.000"));
        stockService.addZeroCostEntry(item, new BigDecimal("2.000"), 202L);
        stockService.removeForSale(item, new BigDecimal("1.000"), 303L);

        assertEquals(0, new BigDecimal("4.000").compareTo(stockRepository.findByItemId(item.getId()).orElseThrow().getQuantity()));

        Map<StockMovementType, StockMovement> movementsByType = stockMovementRepository.findAll().stream()
                .filter(movement -> movement.getItem().getId().equals(item.getId()))
                .collect(Collectors.toMap(StockMovement::getType, movement -> movement));

        assertEquals(4, movementsByType.size());
        assertEquals(new BigDecimal("5.000"), movementsByType.get(StockMovementType.PURCHASE_ENTRY).getQuantity());
        assertEquals(new BigDecimal("-2.000"), movementsByType.get(StockMovementType.MANUAL_ADJUSTMENT).getQuantity());
        assertEquals(new BigDecimal("2.000"), movementsByType.get(StockMovementType.ZERO_COST_ENTRY).getQuantity());
        assertEquals(new BigDecimal("-1.000"), movementsByType.get(StockMovementType.SALE_OUTPUT).getQuantity());
        assertEquals(101L, movementsByType.get(StockMovementType.PURCHASE_ENTRY).getSourceId());
        assertEquals(null, movementsByType.get(StockMovementType.MANUAL_ADJUSTMENT).getSourceId());
        assertEquals(202L, movementsByType.get(StockMovementType.ZERO_COST_ENTRY).getSourceId());
        assertEquals(303L, movementsByType.get(StockMovementType.SALE_OUTPUT).getSourceId());

        assertEquals(4, stockService.findMovements().stream()
                .filter(movementResponse -> movementResponse.getItemId().equals(item.getId()))
                .count());
    }

    private Item saveItem(String name, ItemType type, BigDecimal lowThreshold, BigDecimal criticalThreshold) {
        return itemRepository.save(new Item(
                name,
                type,
                UnitType.G,
                lowThreshold,
                criticalThreshold,
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));
    }

    private Item saveInactiveItem(String name, ItemType type, BigDecimal lowThreshold, BigDecimal criticalThreshold) {
        Item item = new Item(
                name,
                type,
                UnitType.G,
                lowThreshold,
                criticalThreshold,
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        );

        Item saved = itemRepository.save(item);
        saved.update(saved.getName(), saved.getType(), saved.getLowStockThreshold(), saved.getCriticalStockThreshold(), false);
        return saved;
    }
}
