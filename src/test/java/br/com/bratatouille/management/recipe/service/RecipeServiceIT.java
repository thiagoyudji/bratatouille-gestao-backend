package br.com.bratatouille.management.recipe.service;

import br.com.bratatouille.management.generated.model.RecipeCreateRequest;
import br.com.bratatouille.management.generated.model.RecipeItemRequest;
import br.com.bratatouille.management.generated.model.RecipeResponse;
import br.com.bratatouille.management.generated.model.RecipeUpdateRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RecipeServiceIT {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void createUpdateAndToggleRecipeLifecycle() {
        long initialCount = recipeRepository.count();

        Item flour = saveItem("Flour", ItemType.INGREDIENT);
        Item sugar = saveItem("Sugar", ItemType.INGREDIENT);
        Item cake = saveItem("Cake", ItemType.FINISHED_PRODUCT);

        RecipeCreateRequest createRequest = new RecipeCreateRequest();
        createRequest.setName("Cake base");
        createRequest.setOutputItemId(cake.getId());
        createRequest.setItems(java.util.List.of(recipeItem(flour.getId(), new BigDecimal("2.000"))));

        RecipeResponse created = recipeService.create(createRequest);

        assertNotNull(created.getId());
        assertEquals("Cake base", created.getName());
        assertEquals(cake.getId(), created.getOutputItemId());
        assertEquals("Cake", created.getOutputItemName());
        assertTrue(created.getActive());
        assertEquals(1, created.getItems().size());
        assertEquals(flour.getId(), created.getItems().get(0).getItemId());
        assertEquals(new BigDecimal("2.000"), created.getItems().get(0).getQuantity());

        RecipeUpdateRequest updateRequest = new RecipeUpdateRequest();
        updateRequest.setName("Cake base v2");
        updateRequest.setOutputItemId(cake.getId());
        updateRequest.setItems(java.util.List.of(recipeItem(sugar.getId(), new BigDecimal("1.250"))));

        RecipeResponse updated = recipeService.update(created.getId(), updateRequest);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Cake base v2", updated.getName());
        assertEquals(cake.getId(), updated.getOutputItemId());
        assertEquals(1, updated.getItems().size());
        assertEquals(sugar.getId(), updated.getItems().get(0).getItemId());
        assertEquals(new BigDecimal("1.250"), updated.getItems().get(0).getQuantity());

        RecipeResponse deactivated = recipeService.deactivate(created.getId());
        assertFalse(deactivated.getActive());

        RecipeResponse activated = recipeService.activate(created.getId());
        assertTrue(activated.getActive());

        RecipeResponse found = recipeService.findById(created.getId());
        assertEquals("Cake base v2", found.getName());
        assertTrue(found.getActive());
        assertEquals(initialCount + 1, recipeRepository.count());
    }

    @Test
    void createRejectsNonFinishedOutputItem() {
        long initialCount = recipeRepository.count();

        Item flour = saveItem("Flour", ItemType.INGREDIENT);
        Item sugar = saveItem("Sugar", ItemType.INGREDIENT);

        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("Invalid recipe");
        request.setOutputItemId(flour.getId());
        request.setItems(java.util.List.of(recipeItem(sugar.getId(), new BigDecimal("1.000"))));

        assertThrows(IllegalArgumentException.class, () -> recipeService.create(request));
        assertEquals(initialCount, recipeRepository.count());
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

    private RecipeItemRequest recipeItem(Long itemId, BigDecimal quantity) {
        RecipeItemRequest request = new RecipeItemRequest();
        request.setItemId(itemId);
        request.setQuantity(quantity);
        return request;
    }
}
