package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.CreatePartnerRequest;
import br.com.bratatouille.management.generated.model.PartnerResponse;
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
 * A delegate to be called by the {@link PartnersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface PartnersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/partners : Create partner
     *
     * @param createPartnerRequest  (required)
     * @return Partner created (status code 200)
     * @see PartnersApi#createPartner
     */
    default ResponseEntity<PartnerResponse> createPartner(CreatePartnerRequest createPartnerRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"roles\" : [ \"ADMIN\", \"ADMIN\" ], \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"defaultSplitPercentage\" : 6.027456183070403 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/partners : List all partners
     *
     * @return Partners found (status code 200)
     * @see PartnersApi#findAllPartners
     */
    default ResponseEntity<List<PartnerResponse>> findAllPartners() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"roles\" : [ \"ADMIN\", \"ADMIN\" ], \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"defaultSplitPercentage\" : 6.027456183070403 }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"roles\" : [ \"ADMIN\", \"ADMIN\" ], \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"defaultSplitPercentage\" : 6.027456183070403 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/partners/{id} : Find partner by id
     *
     * @param id  (required)
     * @return Partner found (status code 200)
     * @see PartnersApi#findPartnerById
     */
    default ResponseEntity<PartnerResponse> findPartnerById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"roles\" : [ \"ADMIN\", \"ADMIN\" ], \"name\" : \"name\", \"active\" : true, \"id\" : 0, \"defaultSplitPercentage\" : 6.027456183070403 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
