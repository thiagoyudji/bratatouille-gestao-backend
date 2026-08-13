package br.com.bratatouille.management.lot.service;

import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.generated.model.RecipeCreateRequest;
import br.com.bratatouille.management.generated.model.RecipeItemRequest;
import br.com.bratatouille.management.generated.model.LotResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.production.service.ProductionService;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import br.com.bratatouille.management.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LotServiceIT {

    @Autowired
    private LotService lotService;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findLotByProductionAndExpirationRangeUsesProducedBatch() {
        Partner payer = savePartner("Main Partner");
        Item flour = saveItem("Flour", ItemType.INGREDIENT);
        Item cake = saveItem("Cake", ItemType.FINISHED_PRODUCT);

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));
        Long recipeId = seedRecipe(flour, cake);

        ProductionCreateRequest productionRequest = new ProductionCreateRequest();
        productionRequest.setRecipeId(recipeId);
        productionRequest.setProducedQuantity(new BigDecimal("2.000"));

        var production = productionService.create(productionRequest);

        assertNotNull(production.getLotId());
        assertEquals(cake.getId(), production.getOutputItemId());

        LotResponse byProduction = lotService.findByProductionId(production.getId());
        assertEquals(production.getLotId(), byProduction.getId());
        assertEquals(production.getId(), byProduction.getProductionId());
        assertEquals(cake.getId(), byProduction.getItemId());
        assertEquals(0, new BigDecimal("2.000").compareTo(byProduction.getQuantity()));
        assertEquals(byProduction.getProductionDate().plusMonths(6), byProduction.getExpirationDate());

        LotResponse byId = lotService.findById(byProduction.getId());
        assertEquals(byProduction.getExpirationDate(), byId.getExpirationDate());

        List<LotResponse> byItem = lotService.findByItemId(cake.getId());
        assertEquals(1, byItem.size());
        assertEquals(byProduction.getId(), byItem.get(0).getId());

        List<LotResponse> expiring = lotService.findExpiringBetween(
                byProduction.getExpirationDate().minusDays(1),
                byProduction.getExpirationDate().plusDays(1)
        );
        assertEquals(1, expiring.size());
        assertEquals(byProduction.getId(), expiring.get(0).getId());
    }

    @Test
    void findExpiringBetweenRejectsInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> lotService.findExpiringBetween(LocalDate.now().plusDays(1), LocalDate.now()));
    }

    private Partner savePartner(String name) {
        return partnerRepository.save(new Partner(
                name,
                true,
                new BigDecimal("0.00"),
                LocalDateTime.of(2026, 8, 4, 10, 0),
                Set.of(PartnerRole.ADMIN)
        ));
    }

    private Item saveItem(String name, ItemType type) {
        return itemRepository.save(new Item(
                name,
                type,
                UnitType.G,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));
    }

    private void seedPurchase(Partner paidBy, Item item, BigDecimal quantity, BigDecimal totalValue) {
        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(paidBy.getId());
        request.setPurchaseDate(LocalDate.of(2026, 8, 4));
        request.setSupplier("Lot supplier");
        request.setNote("lot seed");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(item.getId());
        purchaseItem.setQuantity(quantity);
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(totalValue);
        request.setItems(List.of(purchaseItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(paidBy.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        purchaseService.create(request);
    }

    private Long seedRecipe(Item flour, Item cake) {
        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("Cake recipe");
        request.setOutputItemId(cake.getId());

        RecipeItemRequest item = new RecipeItemRequest();
        item.setItemId(flour.getId());
        item.setQuantity(new BigDecimal("2.000"));
        request.setItems(List.of(item));

        return recipeService.create(request).getId();
    }
}
