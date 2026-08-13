package br.com.bratatouille.management.stock.entry.service;

import br.com.bratatouille.management.cost.service.CostService;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.generated.model.ZeroCostEntryCreateRequest;
import br.com.bratatouille.management.generated.model.ZeroCostEntryResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.entity.StockMovement;
import br.com.bratatouille.management.stock.entity.StockMovementType;
import br.com.bratatouille.management.stock.entry.entity.ZeroCostEntry;
import br.com.bratatouille.management.stock.entry.repository.ZeroCostEntryRepository;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;
import br.com.bratatouille.management.stock.repository.StockRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ZeroCostEntryServiceIT {

    @Autowired
    private ZeroCostEntryService zeroCostEntryService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private CostService costService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private ZeroCostEntryRepository zeroCostEntryRepository;

    @SpyBean
    private StockService stockService;

    @Test
    void createZeroCostEntryAddsStockRegistersMovementAndImpactsCost() {
        Partner payer = partnerRepository.save(new Partner(
                "Main Partner",
                true,
                new BigDecimal("50.00"),
                LocalDateTime.of(2026, 8, 1, 10, 0),
                Set.of(PartnerRole.ADMIN)
        ));

        Item flour = itemRepository.save(new Item(
                "Flour",
                ItemType.INGREDIENT,
                UnitType.G,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));

        long initialEntryCount = countZeroCostEntriesForItem(flour.getId());
        long initialMovementCount = countZeroCostMovementsForItem(flour.getId());

        assertTrue(costService.findUnitCostOrZero(flour).compareTo(new BigDecimal("2.000000")) == 0);
        assertTrue(costService.hasZeroCostImpact(flour) == false);

        ZeroCostEntryCreateRequest request = new ZeroCostEntryCreateRequest();
        request.setItemId(flour.getId());
        request.setQuantity(new BigDecimal("4.000"));
        request.setReason(ZeroCostEntryCreateRequest.ReasonEnum.INITIAL_STOCK);
        request.setNote("seed adjustment");

        ZeroCostEntryResponse response = zeroCostEntryService.create(request);

        assertNotNull(response.getId());
        assertEquals(flour.getId(), response.getItemId());
        assertEquals(new BigDecimal("4.000"), response.getQuantity());
        assertEquals(ZeroCostEntryResponse.ReasonEnum.INITIAL_STOCK, response.getReason());

        Stock stock = stockRepository.findByItemId(flour.getId()).orElseThrow();
        assertTrue(stock.getQuantity().compareTo(new BigDecimal("14.000")) == 0);

        List<StockMovement> zeroCostMovements = stockMovementRepository.findAll().stream()
                .filter(movement -> movement.getType() == StockMovementType.ZERO_COST_ENTRY)
                .filter(movement -> movement.getItem().getId().equals(flour.getId()))
                .toList();

        assertEquals(1, zeroCostMovements.size());
        assertTrue(costService.hasZeroCostImpact(flour));
        assertTrue(costService.findUnitCostOrZero(flour).compareTo(new BigDecimal("1.428571")) == 0);
        assertEquals(initialEntryCount + 1, zeroCostEntryRepository.count());
        assertEquals(initialMovementCount + 1, countZeroCostMovementsForItem(flour.getId()));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void createZeroCostEntryRollsBackWhenStockUpdateFails() {
        Partner payer = partnerRepository.save(new Partner(
                "Main Partner",
                true,
                new BigDecimal("50.00"),
                LocalDateTime.of(2026, 8, 1, 10, 0),
                Set.of(PartnerRole.ADMIN)
        ));

        Item flour = itemRepository.save(new Item(
                "Flour",
                ItemType.INGREDIENT,
                UnitType.G,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));

        long initialEntryCount = countZeroCostEntriesForItem(flour.getId());
        long initialMovementCount = countZeroCostMovementsForItem(flour.getId());

        doThrow(new IllegalStateException("boom"))
                .when(stockService)
                .addZeroCostEntry(any(), any(), any());

        ZeroCostEntryCreateRequest request = new ZeroCostEntryCreateRequest();
        request.setItemId(flour.getId());
        request.setQuantity(new BigDecimal("4.000"));
        request.setReason(ZeroCostEntryCreateRequest.ReasonEnum.MANUAL_ADJUSTMENT);
        request.setNote("should rollback");

        assertThrows(IllegalStateException.class, () -> zeroCostEntryService.create(request));

        assertEquals(initialEntryCount, countZeroCostEntriesForItem(flour.getId()));
        assertEquals(initialMovementCount, countZeroCostMovementsForItem(flour.getId()));
        assertTrue(stockRepository.findByItemId(flour.getId()).orElseThrow().getQuantity().compareTo(new BigDecimal("10.000")) == 0);
        assertTrue(costService.hasZeroCostImpact(flour) == false);
        assertTrue(costService.findUnitCostOrZero(flour).compareTo(new BigDecimal("2.000000")) == 0);
    }

    private void seedPurchase(Partner payer, Item item, BigDecimal quantity, BigDecimal totalValue) {
        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(payer.getId());
        request.setPurchaseDate(LocalDate.of(2026, 8, 4));
        request.setSupplier("Seed supplier");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(item.getId());
        purchaseItem.setQuantity(quantity);
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(totalValue);
        request.setItems(List.of(purchaseItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(payer.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        purchaseService.create(request);
    }

    private long countZeroCostEntriesForItem(Long itemId) {
        return zeroCostEntryRepository.findAll().stream()
                .filter(entry -> entry.getItem().getId().equals(itemId))
                .count();
    }

    private long countZeroCostMovementsForItem(Long itemId) {
        return stockMovementRepository.findAll().stream()
                .filter(movement -> movement.getItem().getId().equals(itemId))
                .filter(movement -> movement.getType() == StockMovementType.ZERO_COST_ENTRY)
                .count();
    }
}
