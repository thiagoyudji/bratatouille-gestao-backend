package br.com.bratatouille.management.generated.api;

import org.springframework.format.annotation.DateTimeFormat;
import br.com.bratatouille.management.generated.model.FinancialClosingResponse;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import java.time.LocalDate;
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
 * A delegate to be called by the {@link FinancialClosingApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface FinancialClosingApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/financial-closings
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Closed (status code 200)
     * @see FinancialClosingApi#closeFinancialPeriod
     */
    default ResponseEntity<FinancialClosingResponse> closeFinancialPeriod(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/financial-closings/{id}
     *
     * @param id  (required)
     * @return Closed summary (status code 200)
     * @see FinancialClosingApi#getFinancialClosing
     */
    default ResponseEntity<FinancialSummaryResponse> getFinancialClosing(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"totalSpent\" : 1.4658129805029452, \"pixSettlement\" : [ { \"fromPartnerId\" : 0, \"amount\" : 1.4658129805029452, \"fromPartnerName\" : \"fromPartnerName\", \"toPartnerId\" : 6, \"toPartnerName\" : \"toPartnerName\" }, { \"fromPartnerId\" : 0, \"amount\" : 1.4658129805029452, \"fromPartnerName\" : \"fromPartnerName\", \"toPartnerId\" : 6, \"toPartnerName\" : \"toPartnerName\" } ], \"endDate\" : \"2000-01-23\", \"totalPurchases\" : 0.8008281904610115, \"partners\" : [ { \"balance\" : 7.061401241503109, \"partnerName\" : \"partnerName\", \"totalPaid\" : 5.637376656633329, \"partnerId\" : 5, \"totalOwed\" : 2.3021358869347655 }, { \"balance\" : 7.061401241503109, \"partnerName\" : \"partnerName\", \"totalPaid\" : 5.637376656633329, \"partnerId\" : 5, \"totalOwed\" : 2.3021358869347655 } ], \"totalOperationalCosts\" : 6.027456183070403, \"startDate\" : \"2000-01-23\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
