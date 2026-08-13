package br.com.bratatouille.management.production.service;

import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.lot.repository.LotRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.production.entity.Production;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import br.com.bratatouille.management.recipe.domain.ItemQuantityData;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import br.com.bratatouille.management.stock.entity.Stock;
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
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductionServiceIT {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private LotRepository lotRepository;

    @SpyBean
    private StockService stockService;

    @Test
    void createProductionConsumesInputsCreatesOutputAndLot() {
        long initialProductionCount = productionRepository.count();
        long initialLotCount = lotRepository.count();

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

        Item cake = itemRepository.save(new Item(
                "Cake",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));

        Recipe recipe = recipeRepository.save(Recipe.create(
                "Cake recipe",
                cake,
                List.of(new ItemQuantityData(flour, new BigDecimal("2.000"), BigDecimal.ONE))
        ));

        ProductionCreateRequest request = new ProductionCreateRequest();
        request.setRecipeId(recipe.getId());
        request.setProducedQuantity(new BigDecimal("2.000"));

        LocalDate expectedProductionDate = LocalDate.now();

        ProductionResponse response = productionService.create(request);

        assertNotNull(response.getId());
        assertEquals(recipe.getId(), response.getRecipeId());
        assertEquals(cake.getId(), response.getOutputItemId());
        assertEquals(expectedProductionDate, response.getProductionDate());
        assertEquals(new BigDecimal("2.000"), response.getProducedQuantity());
        assertTrue(response.getTotalCost().compareTo(new BigDecimal("8.00")) == 0);
        assertTrue(response.getUnitCost().compareTo(new BigDecimal("4.000000")) == 0);
        assertNotNull(response.getLotId());
        assertEquals(expectedProductionDate.plusMonths(6), response.getLotExpirationDate());
        assertEquals(1, response.getItems().size());

        assertTrue(stockRepository.findByItemId(flour.getId()).orElseThrow().getQuantity().compareTo(new BigDecimal("6.000")) == 0);
        assertEquals(new BigDecimal("2.000"), stockRepository.findByItemId(cake.getId()).orElseThrow().getQuantity());
        assertEquals(initialProductionCount + 1, productionRepository.count());
        assertEquals(initialLotCount + 1, lotRepository.count());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void createProductionRollsBackWhenOutputCreationFailsAfterFirstConsumption() {
        long initialProductionCount = productionRepository.count();
        long initialLotCount = lotRepository.count();

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

        Item cake = itemRepository.save(new Item(
                "Cake",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));
        seedPurchase(payer, sugar, new BigDecimal("10.000"), new BigDecimal("30.00"));

        Recipe recipe = recipeRepository.save(Recipe.create(
                "Cake recipe",
                cake,
                List.of(
                        new ItemQuantityData(flour, new BigDecimal("2.000"), BigDecimal.ONE),
                        new ItemQuantityData(sugar, new BigDecimal("1.000"), BigDecimal.ONE)
                )
        ));

        AtomicInteger invocationCount = new AtomicInteger();
        doAnswer(invocation -> {
            int callNumber = invocationCount.incrementAndGet();

            if (callNumber == 1) {
                return invocation.callRealMethod();
            }

            throw new IllegalStateException("boom");
        }).when(stockService).removeForProduction(any(), any(), any());

        ProductionCreateRequest request = new ProductionCreateRequest();
        request.setRecipeId(recipe.getId());
        request.setProducedQuantity(new BigDecimal("2.000"));

        assertThrows(IllegalStateException.class, () -> productionService.create(request));

        assertEquals(new BigDecimal("10.000"), stockRepository.findByItemId(flour.getId()).orElseThrow().getQuantity());
        assertEquals(new BigDecimal("10.000"), stockRepository.findByItemId(sugar.getId()).orElseThrow().getQuantity());
        assertTrue(stockRepository.findByItemId(cake.getId()).isEmpty());
        assertEquals(initialProductionCount, productionRepository.count());
        assertEquals(initialLotCount, lotRepository.count());
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
}
