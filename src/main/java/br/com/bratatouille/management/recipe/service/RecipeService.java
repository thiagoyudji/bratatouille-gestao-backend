package br.com.bratatouille.management.recipe.service;

import br.com.bratatouille.management.generated.model.RecipeCreateRequest;
import br.com.bratatouille.management.generated.model.RecipeItemRequest;
import br.com.bratatouille.management.generated.model.RecipeResponse;
import br.com.bratatouille.management.generated.model.RecipeUpdateRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.recipe.domain.ItemQuantityData;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.mapper.RecipeMapper;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ItemRepository itemRepository;
    private final RecipeMapper recipeMapper;

    public RecipeService(
            RecipeRepository recipeRepository,
            ItemRepository itemRepository,
            RecipeMapper recipeMapper
    ) {
        this.recipeRepository = recipeRepository;
        this.itemRepository = itemRepository;
        this.recipeMapper = recipeMapper;
    }

    @Transactional
    public RecipeResponse create(RecipeCreateRequest request) {
        Item outputItem = findItem(request.getOutputItemId());

        validateOutputItem(outputItem);

        List<ItemQuantityData> itemsData = buildItemsData(request.getItems());

        Recipe recipe = Recipe.create(
                request.getName(),
                outputItem,
                itemsData
        );

        Recipe saved = recipeRepository.save(recipe);

        return recipeMapper.toResponse(saved);
    }

    public List<RecipeResponse> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toResponse)
                .toList();
    }

    public RecipeResponse findById(Long id) {
        Recipe recipe = findRecipe(id);

        return recipeMapper.toResponse(recipe);
    }

    @Transactional
    public RecipeResponse update(Long id, RecipeUpdateRequest request) {
        Recipe recipe = findRecipe(id);

        Item outputItem = findItem(request.getOutputItemId());

        validateOutputItem(outputItem);

        List<ItemQuantityData> itemsData = buildItemsData(request.getItems());

        recipe.update(
                request.getName(),
                outputItem,
                itemsData
        );

        return recipeMapper.toResponse(recipe);
    }

    @Transactional
    public RecipeResponse activate(Long id) {
        Recipe recipe = findRecipe(id);

        recipe.activate();

        return recipeMapper.toResponse(recipe);
    }

    @Transactional
    public RecipeResponse deactivate(Long id) {
        Recipe recipe = findRecipe(id);

        recipe.deactivate();

        return recipeMapper.toResponse(recipe);
    }

    private Recipe findRecipe(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    private Item findItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    private List<ItemQuantityData> buildItemsData(List<RecipeItemRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("recipe must have at least one item");
        }

        return items.stream()
                .map(itemRequest -> {
                    Item item = findItem(itemRequest.getItemId());

                    return new ItemQuantityData(
                            item,
                            itemRequest.getQuantity(),
                            itemRequest.getYieldPercentage()
                    );
                })
                .toList();
    }

    private void validateOutputItem(Item outputItem) {
        if (outputItem.getType() != ItemType.FINISHED_PRODUCT) {
            throw new IllegalArgumentException("output item must be a finished product");
        }
    }
}