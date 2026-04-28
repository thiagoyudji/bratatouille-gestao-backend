package br.com.bratatouille.management.recipe.mapper;

import br.com.bratatouille.management.generated.model.RecipeItemResponse;
import br.com.bratatouille.management.generated.model.RecipeResponse;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.entity.RecipeItem;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class RecipeMapper {

    public RecipeResponse toResponse(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();

        response.setId(recipe.getId());
        response.setName(recipe.getName());
        response.setOutputItemId(recipe.getOutputItem().getId());
        response.setOutputItemName(recipe.getOutputItem().getName());
        response.setActive(recipe.getActive());

        response.setItems(
                recipe.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );

        response.setCreatedAt(recipe.getCreatedAt().atOffset(ZoneOffset.UTC));
        response.setUpdatedAt(recipe.getUpdatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }

    private RecipeItemResponse toItemResponse(RecipeItem recipeItem) {
        RecipeItemResponse response = new RecipeItemResponse();

        response.setItemId(recipeItem.getItem().getId());
        response.setItemName(recipeItem.getItem().getName());
        response.setQuantity(recipeItem.getQuantity());
        response.setYieldPercentage(recipeItem.getYieldPercentage());
        response.setUnit(recipeItem.getItem().getBaseUnit().name());

        return response;
    }
}