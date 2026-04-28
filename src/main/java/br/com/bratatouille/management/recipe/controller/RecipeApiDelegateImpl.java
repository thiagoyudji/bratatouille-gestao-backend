package br.com.bratatouille.management.recipe.controller;

import br.com.bratatouille.management.generated.api.RecipesApiDelegate;
import br.com.bratatouille.management.generated.model.RecipeCreateRequest;
import br.com.bratatouille.management.generated.model.RecipeResponse;
import br.com.bratatouille.management.generated.model.RecipeUpdateRequest;
import br.com.bratatouille.management.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeApiDelegateImpl implements RecipesApiDelegate {

    private final RecipeService recipeService;

    public RecipeApiDelegateImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public ResponseEntity<RecipeResponse> createRecipe(RecipeCreateRequest request) {
        return ResponseEntity.ok(recipeService.create(request));
    }

    @Override
    public ResponseEntity<List<RecipeResponse>> findAllRecipes() {
        return ResponseEntity.ok(recipeService.findAll());
    }

    @Override
    public ResponseEntity<RecipeResponse> findRecipeById(Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @Override
    public ResponseEntity<RecipeResponse> updateRecipe(Long id, RecipeUpdateRequest request) {
        return ResponseEntity.ok(recipeService.update(id, request));
    }

    @Override
    public ResponseEntity<RecipeResponse> activateRecipe(Long id) {
        return ResponseEntity.ok(recipeService.activate(id));
    }

    @Override
    public ResponseEntity<RecipeResponse> deactivateRecipe(Long id) {
        return ResponseEntity.ok(recipeService.deactivate(id));
    }
}