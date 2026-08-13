package br.com.bratatouille.management.purchase.service;

import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.entity.StockMovement;
import br.com.bratatouille.management.stock.entity.StockMovementType;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;
import br.com.bratatouille.management.stock.repository.StockRepository;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PurchaseServiceIT {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @SpyBean
    private br.com.bratatouille.management.stock.service.StockService stockService;

    @Test
    void createPurchaseIncreasesStockAndRegistersMovement() {
        long initialPurchaseCount = purchaseRepository.count();
        long initialMovementCount = stockMovementRepository.count();

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

        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(payer.getId());
        request.setPurchaseDate(LocalDate.of(2026, 8, 4));
        request.setSupplier("Supplier A");
        request.setNote("Initial restock");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(flour.getId());
        purchaseItem.setQuantity(new BigDecimal("3.500"));
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(new BigDecimal("70.00"));
        request.setItems(List.of(purchaseItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(payer.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        PurchaseResponse response = purchaseService.create(request);

        assertNotNull(response.getId());
        assertEquals(LocalDate.of(2026, 8, 4), response.getPurchaseDate());
        assertEquals(new BigDecimal("70.00"), response.getTotalAmount());
        assertEquals(1, response.getItems().size());
        assertEquals(1, response.getSplits().size());

        Stock stock = stockRepository.findByItemId(flour.getId()).orElseThrow();
        assertEquals(new BigDecimal("3.500"), stock.getQuantity());

        List<StockMovement> movements = stockMovementRepository.findAll().stream()
                .filter(movement -> response.getId().equals(movement.getSourceId()))
                .toList();
        assertEquals(1, movements.size());
        StockMovement movement = movements.get(0);
        assertEquals(flour.getId(), movement.getItem().getId());
        assertEquals(new BigDecimal("3.500"), movement.getQuantity());
        assertEquals(StockMovementType.PURCHASE_ENTRY, movement.getType());
        assertEquals(response.getId(), movement.getSourceId());

        assertEquals(initialPurchaseCount + 1, purchaseRepository.count());
        assertEquals(initialMovementCount + 1, stockMovementRepository.count());
        assertTrue(purchaseRepository.findById(response.getId()).isPresent());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void createPurchaseRollsBackWhenStockUpdateFailsOnSecondItem() {
        long initialPurchaseCount = purchaseRepository.count();
        long initialMovementCount = stockMovementRepository.count();

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

        Item sugar = itemRepository.save(new Item(
                "Sugar",
                ItemType.INGREDIENT,
                UnitType.G,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        doAnswer(invocation -> {
            Item item = invocation.getArgument(0);
            if (flour.getId().equals(item.getId())) {
                return invocation.callRealMethod();
            }

            throw new IllegalStateException("boom");
        }).when(stockService).addFromPurchase(any(), any(), any());

        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(payer.getId());
        request.setPurchaseDate(LocalDate.of(2026, 8, 4));
        request.setSupplier("Supplier A");

        PurchaseItemRequest firstItem = new PurchaseItemRequest();
        firstItem.setItemId(flour.getId());
        firstItem.setQuantity(new BigDecimal("3.500"));
        firstItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        firstItem.setTotalValue(new BigDecimal("70.00"));

        PurchaseItemRequest secondItem = new PurchaseItemRequest();
        secondItem.setItemId(sugar.getId());
        secondItem.setQuantity(new BigDecimal("1.000"));
        secondItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        secondItem.setTotalValue(new BigDecimal("20.00"));

        request.setItems(List.of(firstItem, secondItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(payer.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> purchaseService.create(request));

        assertEquals(initialPurchaseCount, purchaseRepository.count());
        assertEquals(initialMovementCount, stockMovementRepository.count());
        assertTrue(stockRepository.findByItemId(flour.getId()).isEmpty());
        assertTrue(stockRepository.findByItemId(sugar.getId()).isEmpty());
    }
}
