package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.CreateItemRequest;
import br.com.bratatouille.management.generated.model.ItemResponse;
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
 * A delegate to be called by the {@link ItemsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface ItemsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/items : Create item
     *
     * @param createItemRequest  (required)
     * @return Item created (status code 200)
     * @see ItemsApi#createItem
     */
    default ResponseEntity<ItemResponse> createItem(CreateItemRequest createItemRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"baseUnit\" : \"G\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lowStockThreshold\" : 6.027456183070403, \"pricePf\" : 12.9, \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"pricePj\" : 15.9, \"type\" : \"INGREDIENT\", \"criticalStockThreshold\" : 1.4658129805029452, \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/items : List all items
     *
     * @return Items found (status code 200)
     * @see ItemsApi#findAllItems
     */
    default ResponseEntity<List<ItemResponse>> findAllItems() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"baseUnit\" : \"G\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lowStockThreshold\" : 6.027456183070403, \"pricePf\" : 12.9, \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"pricePj\" : 15.9, \"type\" : \"INGREDIENT\", \"criticalStockThreshold\" : 1.4658129805029452, \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"baseUnit\" : \"G\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lowStockThreshold\" : 6.027456183070403, \"pricePf\" : 12.9, \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"pricePj\" : 15.9, \"type\" : \"INGREDIENT\", \"criticalStockThreshold\" : 1.4658129805029452, \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/items/{id} : Find item by id
     *
     * @param id  (required)
     * @return Item found (status code 200)
     * @see ItemsApi#findItemById
     */
    default ResponseEntity<ItemResponse> findItemById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"baseUnit\" : \"G\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lowStockThreshold\" : 6.027456183070403, \"pricePf\" : 12.9, \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"pricePj\" : 15.9, \"type\" : \"INGREDIENT\", \"criticalStockThreshold\" : 1.4658129805029452, \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
