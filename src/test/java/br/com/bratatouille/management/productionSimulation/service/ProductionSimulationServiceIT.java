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
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
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

    @Autowired
    private StockRepository stockRepository;

    @Test
    void unknownYieldOnlyEstimatesPackagingWhenFinishedUnitsAreSupplied() {
        Item ingredient = itemRepository.save(new Item("Unknown ingredient", ItemType.INGREDIENT, UnitType.G, null, null));
        Item packaging = itemRepository.save(new Item("Unknown jar", ItemType.PACKAGING, UnitType.UN, null, null));
        Item output = itemRepository.save(new Item("Unknown output", ItemType.FINISHED_PRODUCT, UnitType.UN, null, null));
        stockRepository.save(new Stock(packaging, new BigDecimal("2")));
        RecipeCreateRequest recipe = new RecipeCreateRequest();
        recipe.setName("Unknown packaging recipe");
        recipe.setOutputItemId(output.getId());
        RecipeItemRequest ing = new RecipeItemRequest();
        ing.setItemId(ingredient.getId());
        ing.setQuantity(new BigDecimal("10"));
        RecipeItemRequest pack = new RecipeItemRequest();
        pack.setItemId(packaging.getId());
        pack.setQuantity(BigDecimal.ONE);
        recipe.setItems(List.of(ing, pack));
        Long recipeId = recipeService.create(recipe).getId();

        ProductionSimulationInputRequest input = new ProductionSimulationInputRequest();
        input.setItemId(ingredient.getId());
        input.setQuantity(new BigDecimal("20"));
        ProductionSimulationRequest request = new ProductionSimulationRequest();
        request.setRecipeId(recipeId);
        request.setInputs(List.of(input));

        ProductionSimulationResponse unknown = productionSimulationService.simulateFromInputs(request);
        org.junit.jupiter.api.Assertions.assertNull(unknown.getQuantity());
        assertEquals(1, unknown.getItems().size());

        request.setPlannedFinishedQuantity(new BigDecimal("5"));
        ProductionSimulationResponse known = productionSimulationService.simulateFromInputs(request);
        assertEquals(0, known.getQuantity().compareTo(new BigDecimal("5")));
        ProductionSimulationItemResponse packageLine = known.getItems().stream()
                .filter(line -> line.getItemType() == ProductionSimulationItemResponse.ItemTypeEnum.PACKAGING)
                .findFirst().orElseThrow();
        assertEquals(0, packageLine.getRequiredQuantity().compareTo(new BigDecimal("5")));
        assertEquals(0, packageLine.getCurrentStock().compareTo(new BigDecimal("2")));
        assertEquals(0, packageLine.getMissingQuantity().compareTo(new BigDecimal("3")));
    }

    @Test
    void targetPlanningUsesRealStockWithoutMovingIt() {
        Item ingredient = itemRepository.save(new Item("Target ingredient", ItemType.INGREDIENT, UnitType.G, null, null));
        Item packaging = itemRepository.save(new Item("Target packaging", ItemType.PACKAGING, UnitType.UN, null, null));
        Item product = itemRepository.save(new Item("Target product", ItemType.FINISHED_PRODUCT, UnitType.UN, null, null));
        stockRepository.save(new Stock(ingredient, new BigDecimal("15")));
        stockRepository.save(new Stock(packaging, new BigDecimal("3")));
        RecipeCreateRequest recipe = new RecipeCreateRequest();
        recipe.setName("Target recipe");
        recipe.setOutputItemId(product.getId());
        recipe.setYieldQuantity(new BigDecimal("2"));
        RecipeItemRequest ingredientLine = new RecipeItemRequest();
        ingredientLine.setItemId(ingredient.getId());
        ingredientLine.setQuantity(new BigDecimal("10"));
        RecipeItemRequest packagingLine = new RecipeItemRequest();
        packagingLine.setItemId(packaging.getId());
        packagingLine.setQuantity(BigDecimal.ONE);
        recipe.setItems(List.of(ingredientLine, packagingLine));
        Long recipeId = recipeService.create(recipe).getId();

        ProductionSimulationRequest request = new ProductionSimulationRequest();
        request.setRecipeId(recipeId);
        request.setPlannedFinishedQuantity(new BigDecimal("4"));
        ProductionSimulationResponse result = productionSimulationService.simulateFromInputs(request);

        assertEquals(0, result.getQuantity().compareTo(new BigDecimal("4")));
        assertEquals(2, result.getItems().size());
        ProductionSimulationItemResponse raw = result.getItems().stream()
                .filter(item -> ingredient.getId().equals(item.getItemId())).findFirst().orElseThrow();
        ProductionSimulationItemResponse jar = result.getItems().stream()
                .filter(item -> packaging.getId().equals(item.getItemId())).findFirst().orElseThrow();
        assertEquals(0, raw.getRequiredQuantity().compareTo(new BigDecimal("20")));
        assertEquals(0, raw.getMissingQuantity().compareTo(new BigDecimal("5")));
        assertEquals(0, jar.getRequiredQuantity().compareTo(new BigDecimal("4")));
        assertEquals(0, jar.getMissingQuantity().compareTo(BigDecimal.ONE));
        assertEquals(0, stockRepository.findByItemId(ingredient.getId()).orElseThrow().getQuantity().compareTo(new BigDecimal("15")));
    }

    @Test
    void targetPlanningWithUnknownYieldShowsOnlyPackagingAndLimitation() {
        Item ingredient = itemRepository.save(new Item("Unknown target ingredient", ItemType.INGREDIENT, UnitType.G, null, null));
        Item packaging = itemRepository.save(new Item("Unknown target packaging", ItemType.PACKAGING, UnitType.UN, null, null));
        Item output = itemRepository.save(new Item("Unknown target output", ItemType.FINISHED_PRODUCT, UnitType.UN, null, null));
        RecipeCreateRequest recipe = new RecipeCreateRequest();
        recipe.setName("Unknown target recipe");
        recipe.setOutputItemId(output.getId());
        RecipeItemRequest raw = new RecipeItemRequest();
        raw.setItemId(ingredient.getId());
        raw.setQuantity(new BigDecimal("100"));
        RecipeItemRequest jar = new RecipeItemRequest();
        jar.setItemId(packaging.getId());
        jar.setQuantity(BigDecimal.ONE);
        recipe.setItems(List.of(raw, jar));
        Long id = recipeService.create(recipe).getId();

        ProductionSimulationRequest request = new ProductionSimulationRequest();
        request.setRecipeId(id);
        request.setPlannedFinishedQuantity(new BigDecimal("6"));
        ProductionSimulationResponse result = productionSimulationService.simulateFromInputs(request);
        assertEquals(1, result.getItems().size());
        assertEquals(packaging.getId(), result.getItems().get(0).getItemId());
        assertNotNull(result.getPlanningNote());
        request.setPlannedFinishedQuantity(new BigDecimal("1.5"));
        assertThrows(IllegalArgumentException.class, () -> productionSimulationService.simulateFromInputs(request));
    }

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


    @Test
    void simulationWithoutKnownYieldDoesNotInventFinishedUnits() {
        Item flour = saveItem("Unknown yield flour", ItemType.INGREDIENT);
        Item cake = saveItem("Unknown yield cake", ItemType.FINISHED_PRODUCT);
        RecipeCreateRequest recipeRequest = new RecipeCreateRequest();
        recipeRequest.setName("Unknown yield");
        recipeRequest.setOutputItemId(cake.getId());
        RecipeItemRequest ingredient = new RecipeItemRequest();
        ingredient.setItemId(flour.getId());
        ingredient.setQuantity(new BigDecimal("2"));
        recipeRequest.setItems(List.of(ingredient));
        Long recipeId = recipeService.create(recipeRequest).getId();

        ProductionSimulationInputRequest input = new ProductionSimulationInputRequest();
        input.setItemId(flour.getId());
        input.setQuantity(new BigDecimal("4"));
        ProductionSimulationRequest request = new ProductionSimulationRequest();
        request.setRecipeId(recipeId);
        request.setInputs(List.of(input));

        ProductionSimulationResponse response = productionSimulationService.simulateFromInputs(request);
        org.junit.jupiter.api.Assertions.assertNull(response.getQuantity());
        assertEquals(0, new BigDecimal("4").compareTo(response.getItems().get(0).getRequiredQuantity()));
        assertThrows(IllegalArgumentException.class,
                () -> productionSimulationService.simulate(recipeId, BigDecimal.ONE));
    }

    @Test
    void outputBasedSimulationScalesUsingReferenceYield() {
        Item flour = saveItem("Scaled yield flour", ItemType.INGREDIENT);
        Item cake = saveItem("Scaled yield cake", ItemType.FINISHED_PRODUCT);
        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("Scaled yield");
        request.setOutputItemId(cake.getId());
        request.setYieldQuantity(new BigDecimal("5"));
        RecipeItemRequest ingredient = new RecipeItemRequest();
        ingredient.setItemId(flour.getId());
        ingredient.setQuantity(new BigDecimal("10"));
        request.setItems(List.of(ingredient));
        Long recipeId = recipeService.create(request).getId();

        ProductionSimulationResponse result = productionSimulationService.simulate(recipeId, new BigDecimal("2"));
        assertEquals(0, new BigDecimal("4").compareTo(result.getItems().get(0).getRequiredQuantity()));
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
        request.setPayerType(PurchaseCreateRequest.PayerTypeEnum.BRATATOUILLE);
        request.setPurchaseDate(LocalDate.of(2026, 8, 4));
        request.setSupplier("Simulation supplier");
        request.setNote("simulation seed");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(item.getId());
        purchaseItem.setQuantity(quantity);
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(totalValue);
        request.setItems(List.of(purchaseItem));

        purchaseService.create(request);
    }

    private Long seedRecipe(Item flour, Item cake) {
        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("Cake recipe");
        request.setOutputItemId(cake.getId());
        request.setYieldQuantity(BigDecimal.ONE);

        RecipeItemRequest item = new RecipeItemRequest();
        item.setItemId(flour.getId());
        item.setQuantity(new BigDecimal("2.000"));

        request.setItems(List.of(item));

        return recipeService.create(request).getId();
    }
}
