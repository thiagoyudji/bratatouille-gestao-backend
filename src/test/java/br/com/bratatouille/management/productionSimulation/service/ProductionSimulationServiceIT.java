package br.com.bratatouille.management.productionSimulation.service;

import br.com.bratatouille.management.generated.model.ProductionSimulationItemResponse;
import br.com.bratatouille.management.generated.model.ProductionSimulationInputRequest;
import br.com.bratatouille.management.generated.model.ProductionSimulationRequest;
import br.com.bratatouille.management.generated.model.ProductionSimulationResponse;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.generated.model.RecipeCreateRequest;
import br.com.bratatouille.management.generated.model.RecipeItemRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
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
class ProductionSimulationServiceIT {

    @Autowired
    private ProductionSimulationService productionSimulationService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void simulateProductionCalculatesRequiredQuantitiesLossAndCost() {
        Partner payer = savePartner("Main Partner");
        Item flour = saveItem("Flour", ItemType.INGREDIENT);
        Item cake = saveItem("Cake", ItemType.FINISHED_PRODUCT);

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));
        Long recipeId = seedRecipe(flour, cake);

        ProductionSimulationResponse response = productionSimulationService.simulate(recipeId, new BigDecimal("3"));

        assertEquals(recipeId, response.getRecipeId());
        assertEquals("Cake recipe", response.getRecipeName());
        assertEquals(cake.getId(), response.getOutputItemId());
        assertEquals("Cake", response.getOutputItemName());
        assertEquals(new BigDecimal("3"), response.getQuantity());
        assertEquals(0, new BigDecimal("12.000000").compareTo(response.getEstimatedTotalCost()));
        assertEquals(1, response.getItems().size());

        ProductionSimulationItemResponse item = response.getItems().get(0);
        assertEquals(flour.getId(), item.getItemId());
        assertEquals("Flour", item.getItemName());
        assertEquals(0, new BigDecimal("6.000000").compareTo(item.getRequiredQuantity()));
        assertEquals(0, new BigDecimal("6.000000").compareTo(item.getUsableQuantity()));
        assertEquals(0, new BigDecimal("0.000000").compareTo(item.getLossQuantity()));
        assertEquals(0, new BigDecimal("1.0000").compareTo(item.getYieldPercentage()));
        assertEquals(0, new BigDecimal("10.000").compareTo(item.getCurrentStock()));
        assertEquals(0, new BigDecimal("2.000000").compareTo(item.getUnitCost()));
        assertEquals(0, new BigDecimal("12.000000").compareTo(item.getTotalCost()));
    }

    @Test
    void simulateProductionRejectsInactiveRecipe() {
        Partner payer = savePartner("Main Partner");
        Item flour = saveItem("Flour", ItemType.INGREDIENT);
        Item cake = saveItem("Cake", ItemType.FINISHED_PRODUCT);

        seedPurchase(payer, flour, new BigDecimal("10.000"), new BigDecimal("20.00"));
        Long recipeId = seedRecipe(flour, cake);

        recipeService.deactivate(recipeId);

        assertThrows(IllegalArgumentException.class, () -> productionSimulationService.simulate(recipeId, new BigDecimal("1")));
    }

    @Test
    void simulateFromInputsUsesManualQuantitiesAndRoundsPotsDown() {
        Partner payer = savePartner("Manual Simulation Partner");
        Item flour = saveItem("Manual Flour", ItemType.INGREDIENT);
        Item cake = saveItem("Manual Cake", ItemType.FINISHED_PRODUCT);
        seedPurchase(payer, flour, new BigDecimal("20.000"), new BigDecimal("40.00"));

        RecipeCreateRequest recipeRequest = new RecipeCreateRequest();
        recipeRequest.setName("Manual recipe");
        recipeRequest.setOutputItemId(cake.getId());
        recipeRequest.setYieldQuantity(new BigDecimal("10"));
        RecipeItemRequest recipeItem = new RecipeItemRequest();
        recipeItem.setItemId(flour.getId());
        recipeItem.setQuantity(new BigDecimal("3"));
        recipeRequest.setItems(List.of(recipeItem));
        Long recipeId = recipeService.create(recipeRequest).getId();

        ProductionSimulationInputRequest input = new ProductionSimulationInputRequest();
        input.setItemId(flour.getId());
        input.setQuantity(new BigDecimal("4"));
        ProductionSimulationRequest simulationRequest = new ProductionSimulationRequest();
        simulationRequest.setRecipeId(recipeId);
        simulationRequest.setInputs(List.of(input));

        ProductionSimulationResponse response = productionSimulationService.simulateFromInputs(simulationRequest);

        assertEquals(new BigDecimal("13"), response.getQuantity());
        assertEquals(new BigDecimal("4"), response.getItems().get(0).getUsableQuantity());
        assertEquals(0, new BigDecimal("0.000000").compareTo(response.getItems().get(0).getMissingQuantity()));
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
        request.setSupplier("Simulation supplier");
        request.setNote("simulation seed");

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
