package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.OperationalLossCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalLossResponse;
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
 * A delegate to be called by the {@link OperationalLossesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface OperationalLossesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/operational-losses : Create operational loss
     *
     * @param operationalLossCreateRequest  (required)
     * @return Operational loss created (status code 200)
     * @see OperationalLossesApi#createOperationalLoss
     */
    default ResponseEntity<OperationalLossResponse> createOperationalLoss(OperationalLossCreateRequest operationalLossCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 6, \"reason\" : \"reason\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"lossDate\" : \"2000-01-23\", \"unitCost\" : 5.962133916683182, \"id\" : 0, \"totalCost\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/operational-losses : List operational losses
     *
     * @return Operational losses found (status code 200)
     * @see OperationalLossesApi#findAllOperationalLosses
     */
    default ResponseEntity<List<OperationalLossResponse>> findAllOperationalLosses() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 6, \"reason\" : \"reason\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"lossDate\" : \"2000-01-23\", \"unitCost\" : 5.962133916683182, \"id\" : 0, \"totalCost\" : 5.637376656633329 }, { \"itemId\" : 6, \"reason\" : \"reason\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"lossDate\" : \"2000-01-23\", \"unitCost\" : 5.962133916683182, \"id\" : 0, \"totalCost\" : 5.637376656633329 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/operational-losses/{id} : Find operational loss by id
     *
     * @param id  (required)
     * @return Operational loss found (status code 200)
     * @see OperationalLossesApi#findOperationalLossById
     */
    default ResponseEntity<OperationalLossResponse> findOperationalLossById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 6, \"reason\" : \"reason\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"lossDate\" : \"2000-01-23\", \"unitCost\" : 5.962133916683182, \"id\" : 0, \"totalCost\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
