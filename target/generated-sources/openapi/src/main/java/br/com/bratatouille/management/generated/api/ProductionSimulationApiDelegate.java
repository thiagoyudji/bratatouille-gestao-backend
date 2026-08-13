package br.com.bratatouille.management.generated.api;

import java.math.BigDecimal;
import br.com.bratatouille.management.generated.model.ProductionSimulationResponse;
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
 * A delegate to be called by the {@link ProductionSimulationApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface ProductionSimulationApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/production-simulation : Simulate production
     *
     * @param recipeId  (required)
     * @param quantity  (required)
     * @return Simulation result (status code 200)
     * @see ProductionSimulationApi#simulateProduction
     */
    default ResponseEntity<ProductionSimulationResponse> simulateProduction(Long recipeId,
        BigDecimal quantity) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"recipeName\" : \"recipeName\", \"quantity\" : 1.4658129805029452, \"estimatedTotalCost\" : 5.962133916683182, \"outputItemId\" : 6, \"outputItemName\" : \"outputItemName\", \"items\" : [ { \"usableQuantity\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"yieldPercentage\" : 3.616076749251911, \"requiredQuantity\" : 2.3021358869347655, \"missingQuantity\" : 4.145608029883936, \"lossQuantity\" : 9.301444243932576, \"unitCost\" : 7.386281948385884, \"currentStock\" : 2.027123023002322, \"totalCost\" : 1.2315135367772556 }, { \"usableQuantity\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"yieldPercentage\" : 3.616076749251911, \"requiredQuantity\" : 2.3021358869347655, \"missingQuantity\" : 4.145608029883936, \"lossQuantity\" : 9.301444243932576, \"unitCost\" : 7.386281948385884, \"currentStock\" : 2.027123023002322, \"totalCost\" : 1.2315135367772556 } ], \"recipeId\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
