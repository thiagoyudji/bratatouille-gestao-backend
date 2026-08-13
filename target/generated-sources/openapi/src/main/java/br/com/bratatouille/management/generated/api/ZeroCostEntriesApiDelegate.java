package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.ZeroCostEntryCreateRequest;
import br.com.bratatouille.management.generated.model.ZeroCostEntryResponse;
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
 * A delegate to be called by the {@link ZeroCostEntriesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface ZeroCostEntriesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/zero-cost-entries : Create zero cost entry
     *
     * @param zeroCostEntryCreateRequest  (required)
     * @return Zero cost entry created (status code 200)
     * @see ZeroCostEntriesApi#createZeroCostEntry
     */
    default ResponseEntity<ZeroCostEntryResponse> createZeroCostEntry(ZeroCostEntryCreateRequest zeroCostEntryCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 6, \"reason\" : \"DONATION\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/zero-cost-entries : List zero cost entries
     *
     * @return Zero cost entries found (status code 200)
     * @see ZeroCostEntriesApi#findAllZeroCostEntries
     */
    default ResponseEntity<List<ZeroCostEntryResponse>> findAllZeroCostEntries() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 6, \"reason\" : \"DONATION\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"id\" : 0 }, { \"itemId\" : 6, \"reason\" : \"DONATION\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"id\" : 0 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/zero-cost-entries/{id} : Find zero cost entry by id
     *
     * @param id  (required)
     * @return Zero cost entry found (status code 200)
     * @see ZeroCostEntriesApi#findZeroCostEntryById
     */
    default ResponseEntity<ZeroCostEntryResponse> findZeroCostEntryById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 6, \"reason\" : \"DONATION\", \"note\" : \"note\", \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 1.4658129805029452, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
