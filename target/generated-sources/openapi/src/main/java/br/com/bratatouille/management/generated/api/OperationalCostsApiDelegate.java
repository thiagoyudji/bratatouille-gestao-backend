package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostResponse;
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
 * A delegate to be called by the {@link OperationalCostsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface OperationalCostsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/operational-costs : Create operational cost
     *
     * @param operationalCostCreateRequest  (required)
     * @return Operational cost created (status code 200)
     * @see OperationalCostsApi#createOperationalCost
     */
    default ResponseEntity<OperationalCostResponse> createOperationalCost(OperationalCostCreateRequest operationalCostCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"paidByPartnerId\" : 6, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"amount\" : 1.4658129805029452, \"costDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 }, { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"description\" : \"description\", \"id\" : 0, \"category\" : \"FIXED\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/operational-costs : List operational costs
     *
     * @return Operational costs found (status code 200)
     * @see OperationalCostsApi#findAllOperationalCosts
     */
    default ResponseEntity<List<OperationalCostResponse>> findAllOperationalCosts() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"paidByPartnerId\" : 6, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"amount\" : 1.4658129805029452, \"costDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 }, { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"description\" : \"description\", \"id\" : 0, \"category\" : \"FIXED\" }, { \"paidByPartnerId\" : 6, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"amount\" : 1.4658129805029452, \"costDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 }, { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"description\" : \"description\", \"id\" : 0, \"category\" : \"FIXED\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/operational-costs/{id} : Find operational cost by id
     *
     * @param id  (required)
     * @return Operational cost found (status code 200)
     * @see OperationalCostsApi#findOperationalCostById
     */
    default ResponseEntity<OperationalCostResponse> findOperationalCostById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"paidByPartnerId\" : 6, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"amount\" : 1.4658129805029452, \"costDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 }, { \"owedAmount\" : 2.3021358869347655, \"partnerName\" : \"partnerName\", \"id\" : 5, \"partnerId\" : 5 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"description\" : \"description\", \"id\" : 0, \"category\" : \"FIXED\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
