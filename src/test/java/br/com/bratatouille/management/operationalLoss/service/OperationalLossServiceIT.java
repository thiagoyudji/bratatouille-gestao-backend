package br.com.bratatouille.management.operationalLoss.service;

import br.com.bratatouille.management.generated.model.OperationalLossCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalLossResponse;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.operationalLoss.entity.OperationalLoss;
import br.com.bratatouille.management.operationalLoss.repository.OperationalLossRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.production.entity.Production;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.production.service.ProductionService;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import br.com.bratatouille.management.recipe.domain.ItemQuantityData;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import br.com.bratatouille.management.sellableStock.repository.SellableStockRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import br.com.bratatouille.management.stock.repository.StockRepository;
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
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OperationalLossServiceIT {

    @Autowired
    private OperationalLossService operationalLossService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private OperationalLossRepository operationalLossRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private SellableStockRepository sellableStockRepository;

    @SpyBean
    private SellableStockService sellableStockService;

    @Test
    void createOperationalLossDecreasesStockAndRegistersLoss() {
        long initialLossCount = operationalLossRepository.count();

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

        OperationalLossCreateRequest request = new OperationalLossCreateRequest();
        request.setLossDate(LocalDate.of(2026, 8, 4));
        request.setItemId(flour.getId());
        request.setQuantity(new BigDecimal("2.000"));
        request.setReason(OperationalLossCreateRequest.ReasonEnum.DAMAGE);
        request.setNote("Dropped during handling");

        OperationalLossResponse response = operationalLossService.create(request);

        assertNotNull(response.getId());
        assertEquals(flour.getId(), response.getItemId());
        assertEquals(LocalDate.of(2026, 8, 4), response.getLossDate());
        assertEquals(new BigDecimal("2.000"), response.getQuantity());
        assertTrue(response.getUnitCost().compareTo(new BigDecimal("2.000000")) == 0);
        assertTrue(response.getTotalCost().compareTo(new BigDecimal("4.000000")) == 0);

        assertTrue(stockRepository.findByItemId(flour.getId()).orElseThrow().getQuantity().compareTo(new BigDecimal("8.000")) == 0);
        assertEquals(initialLossCount + 1, operationalLossRepository.count());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void createOperationalLossRollsBackWhenSellableStockUpdateFails() {
        long initialLossCount = operationalLossRepository.count();

        Partner payer = partnerRepository.save(new Partner(
                "Main Partner",
                true,
                new BigDecimal("50.00"),
                LocalDateTime.of(2026, 8, 1, 10, 0),
                Set.of(PartnerRole.ADMIN)
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
        seedProduction(cake, flour);

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setAvailableQuantity(new BigDecimal("5.000"));
        sellableStockRequest.setInfinite(false);
        sellableStockRequest.setEnabled(true);
        sellableStockService.upsert(cake.getId(), sellableStockRequest);

        doAnswer(invocation -> {
            invocation.callRealMethod();
            throw new IllegalStateException("boom");
        }).when(sellableStockService).decreaseAfterLossIfConfigured(any(), any());

        OperationalLossCreateRequest request = new OperationalLossCreateRequest();
        request.setLossDate(LocalDate.of(2026, 8, 4));
        request.setItemId(cake.getId());
        request.setQuantity(new BigDecimal("1.000"));
        request.setReason(OperationalLossCreateRequest.ReasonEnum.OPERATIONAL);
        request.setNote("Damaged on shelf");

        assertThrows(IllegalStateException.class, () -> operationalLossService.create(request));

        assertEquals(initialLossCount, operationalLossRepository.count());
        assertTrue(stockRepository.findByItemId(cake.getId()).orElseThrow().getQuantity().compareTo(new BigDecimal("2.000")) == 0);
        assertTrue(sellableStockRepository.findByItemId(cake.getId()).orElseThrow().getAvailableQuantity().compareTo(new BigDecimal("5.000")) == 0);
        assertTrue(stockRepository.findByItemId(flour.getId()).orElseThrow().getQuantity().compareTo(new BigDecimal("6.000")) == 0);
        assertEquals(1, productionRepository.count());
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

    private void seedProduction(Item outputItem, Item inputItem) {
        Recipe recipe = recipeRepository.save(Recipe.create(
                "Cake recipe",
                outputItem,
                List.of(new ItemQuantityData(inputItem, new BigDecimal("2.000"), BigDecimal.ONE))
        ));

        br.com.bratatouille.management.generated.model.ProductionCreateRequest request = new br.com.bratatouille.management.generated.model.ProductionCreateRequest();
        request.setRecipeId(recipe.getId());
        request.setProducedQuantity(new BigDecimal("2.000"));

        productionService.create(request);
    }
}
