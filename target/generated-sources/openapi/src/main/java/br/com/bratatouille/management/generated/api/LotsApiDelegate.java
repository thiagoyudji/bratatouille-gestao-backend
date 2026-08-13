package br.com.bratatouille.management.generated.api;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import br.com.bratatouille.management.generated.model.LotResponse;
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
 * A delegate to be called by the {@link LotsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface LotsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/lots : List all lots
     *
     * @return Lots found (status code 200)
     * @see LotsApi#findAllLots
     */
    default ResponseEntity<List<LotResponse>> findAllLots() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" }, { \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/lots/expiring : Find lots expiring between dates
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Expiring lots found (status code 200)
     * @see LotsApi#findExpiringLots
     */
    default ResponseEntity<List<LotResponse>> findExpiringLots(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" }, { \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/lots/{id} : Find lot by id
     *
     * @param id  (required)
     * @return Lot found (status code 200)
     * @see LotsApi#findLotById
     */
    default ResponseEntity<LotResponse> findLotById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/lots/production/{productionId} : Find lot by production id
     *
     * @param productionId  (required)
     * @return Lot found (status code 200)
     * @see LotsApi#findLotByProductionId
     */
    default ResponseEntity<LotResponse> findLotByProductionId(Long productionId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/lots/item/{itemId} : Find lots by item id
     *
     * @param itemId  (required)
     * @return Lots found (status code 200)
     * @see LotsApi#findLotsByItemId
     */
    default ResponseEntity<List<LotResponse>> findLotsByItemId(Long itemId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" }, { \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"productionDate\" : \"2000-01-23\", \"quantity\" : 5.962133916683182, \"productionId\" : 6, \"id\" : 0, \"expirationDate\" : \"2000-01-23\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
