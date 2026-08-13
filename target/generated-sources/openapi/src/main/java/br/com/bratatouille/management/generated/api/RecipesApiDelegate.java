package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.RecipeCreateRequest;
import br.com.bratatouille.management.generated.model.RecipeResponse;
import br.com.bratatouille.management.generated.model.RecipeUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link RecipesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface RecipesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PATCH /api/recipes/{id}/activate : Activate recipe
     *
     * @param id  (required)
     * @return Recipe activated (status code 200)
     * @see RecipesApi#activateRecipe
     */
    default ResponseEntity<RecipeResponse> activateRecipe(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/recipes : Create recipe
     *
     * @param recipeCreateRequest  (required)
     * @return Recipe created (status code 200)
     * @see RecipesApi#createRecipe
     */
    default ResponseEntity<RecipeResponse> createRecipe(RecipeCreateRequest recipeCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PATCH /api/recipes/{id}/deactivate : Deactivate recipe
     *
     * @param id  (required)
     * @return Recipe deactivated (status code 200)
     * @see RecipesApi#deactivateRecipe
     */
    default ResponseEntity<RecipeResponse> deactivateRecipe(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/recipes : List all recipes
     *
     * @return Recipes found (status code 200)
     * @see RecipesApi#findAllRecipes
     */
    default ResponseEntity<List<RecipeResponse>> findAllRecipes() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/recipes/{id} : Find recipe by id
     *
     * @param id  (required)
     * @return Recipe found (status code 200)
     * @see RecipesApi#findRecipeById
     */
    default ResponseEntity<RecipeResponse> findRecipeById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /api/recipes/{id} : Update recipe
     *
     * @param id  (required)
     * @param recipeUpdateRequest  (required)
     * @return Recipe updated (status code 200)
     * @see RecipesApi#updateRecipe
     */
    default ResponseEntity<RecipeResponse> updateRecipe(Long id,
        RecipeUpdateRequest recipeUpdateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 }, { \"itemId\" : 1, \"itemName\" : \"itemName\", \"yieldPercentage\" : 5.637376656633329, \"unit\" : \"unit\", \"quantity\" : 5.962133916683182 } ], \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
