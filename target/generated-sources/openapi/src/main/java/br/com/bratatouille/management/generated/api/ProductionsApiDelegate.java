package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.ProductionResponse;
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
 * A delegate to be called by the {@link ProductionsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface ProductionsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/productions : Create production
     *
     * @param productionCreateRequest  (required)
     * @return Production created (status code 200)
     * @see ProductionsApi#createProduction
     */
    default ResponseEntity<ProductionResponse> createProduction(ProductionCreateRequest productionCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"lotId\" : 7, \"outputItemId\" : 1, \"outputItemName\" : \"outputItemName\", \"recipeId\" : 6, \"recipeName\" : \"recipeName\", \"lotExpirationDate\" : \"2000-01-23\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"productionDate\" : \"2000-01-23\", \"producedQuantity\" : 5.962133916683182, \"unitCost\" : 2.3021358869347655, \"id\" : 0, \"items\" : [ { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 }, { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 } ], \"totalCost\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/productions : List all productions
     *
     * @return Productions found (status code 200)
     * @see ProductionsApi#findAllProductions
     */
    default ResponseEntity<List<ProductionResponse>> findAllProductions() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"lotId\" : 7, \"outputItemId\" : 1, \"outputItemName\" : \"outputItemName\", \"recipeId\" : 6, \"recipeName\" : \"recipeName\", \"lotExpirationDate\" : \"2000-01-23\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"productionDate\" : \"2000-01-23\", \"producedQuantity\" : 5.962133916683182, \"unitCost\" : 2.3021358869347655, \"id\" : 0, \"items\" : [ { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 }, { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 } ], \"totalCost\" : 5.637376656633329 }, { \"lotId\" : 7, \"outputItemId\" : 1, \"outputItemName\" : \"outputItemName\", \"recipeId\" : 6, \"recipeName\" : \"recipeName\", \"lotExpirationDate\" : \"2000-01-23\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"productionDate\" : \"2000-01-23\", \"producedQuantity\" : 5.962133916683182, \"unitCost\" : 2.3021358869347655, \"id\" : 0, \"items\" : [ { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 }, { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 } ], \"totalCost\" : 5.637376656633329 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/productions/{id} : Find production by id
     *
     * @param id  (required)
     * @return Production found (status code 200)
     * @see ProductionsApi#findProductionById
     */
    default ResponseEntity<ProductionResponse> findProductionById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"lotId\" : 7, \"outputItemId\" : 1, \"outputItemName\" : \"outputItemName\", \"recipeId\" : 6, \"recipeName\" : \"recipeName\", \"lotExpirationDate\" : \"2000-01-23\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"productionDate\" : \"2000-01-23\", \"producedQuantity\" : 5.962133916683182, \"unitCost\" : 2.3021358869347655, \"id\" : 0, \"items\" : [ { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 }, { \"usableQuantity\" : 2.027123023002322, \"itemId\" : 9, \"itemName\" : \"itemName\", \"yieldPercentage\" : 7.386281948385884, \"lossQuantity\" : 4.145608029883936, \"unitCost\" : 1.2315135367772556, \"totalCost\" : 1.0246457001441578, \"consumedQuantity\" : 3.616076749251911 } ], \"totalCost\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
