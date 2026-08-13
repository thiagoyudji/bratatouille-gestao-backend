package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.CashFlowSummaryResponse;
import org.springframework.format.annotation.DateTimeFormat;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import java.time.LocalDate;
import br.com.bratatouille.management.generated.model.PartnerBalanceResponse;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
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
 * A delegate to be called by the {@link FinancialApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface FinancialApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/financial/balances : Get partner balances
     *
     * @return Partner balances found (status code 200)
     * @see FinancialApi#getBalances
     */
    default ResponseEntity<List<PartnerBalanceResponse>> getBalances() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"balance\" : 6.027456183070403, \"partnerName\" : \"partnerName\", \"partnerId\" : 0 }, { \"balance\" : 6.027456183070403, \"partnerName\" : \"partnerName\", \"partnerId\" : 0 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/financial/balances/period : Get partner balances by period
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Partner balances by period found (status code 200)
     * @see FinancialApi#getBalancesByPeriod
     */
    default ResponseEntity<List<PartnerBalanceResponse>> getBalancesByPeriod(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"balance\" : 6.027456183070403, \"partnerName\" : \"partnerName\", \"partnerId\" : 0 }, { \"balance\" : 6.027456183070403, \"partnerName\" : \"partnerName\", \"partnerId\" : 0 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/financial/cash-flow/period : Get cash flow by period
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Cash flow by period found (status code 200)
     * @see FinancialApi#getCashFlowByPeriod
     */
    default ResponseEntity<CashFlowSummaryResponse> getCashFlowByPeriod(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"entries\" : [ { \"date\" : \"2000-01-23\", \"sourceId\" : 5, \"amount\" : 7.061401241503109, \"partnerName\" : \"partnerName\", \"description\" : \"description\", \"balanceAfter\" : 9.301444243932576, \"partnerId\" : 2, \"type\" : \"PURCHASE\", \"direction\" : \"OUT\" }, { \"date\" : \"2000-01-23\", \"sourceId\" : 5, \"amount\" : 7.061401241503109, \"partnerName\" : \"partnerName\", \"description\" : \"description\", \"balanceAfter\" : 9.301444243932576, \"partnerId\" : 2, \"type\" : \"PURCHASE\", \"direction\" : \"OUT\" } ], \"totalIn\" : 6.027456183070403, \"endDate\" : \"2000-01-23\", \"closingBalance\" : 5.962133916683182, \"openingBalance\" : 0.8008281904610115, \"totalOut\" : 1.4658129805029452, \"startDate\" : \"2000-01-23\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/financial/summary/period : Get financial summary by period
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Financial summary by period found (status code 200)
     * @see FinancialApi#getFinancialSummaryByPeriod
     */
    default ResponseEntity<FinancialSummaryResponse> getFinancialSummaryByPeriod(LocalDate startDate,
        LocalDate endDate) {
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

    /**
     * GET /api/financial/pix-settlement : Get Pix settlement suggestion
     *
     * @return Pix settlement found (status code 200)
     * @see FinancialApi#getPixSettlement
     */
    default ResponseEntity<List<PixSettlementResponse>> getPixSettlement() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"fromPartnerId\" : 0, \"amount\" : 1.4658129805029452, \"fromPartnerName\" : \"fromPartnerName\", \"toPartnerId\" : 6, \"toPartnerName\" : \"toPartnerName\" }, { \"fromPartnerId\" : 0, \"amount\" : 1.4658129805029452, \"fromPartnerName\" : \"fromPartnerName\", \"toPartnerId\" : 6, \"toPartnerName\" : \"toPartnerName\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/financial/pix-settlement/period : Get Pix settlement suggestion by period
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Pix settlement by period found (status code 200)
     * @see FinancialApi#getPixSettlementByPeriod
     */
    default ResponseEntity<List<PixSettlementResponse>> getPixSettlementByPeriod(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"fromPartnerId\" : 0, \"amount\" : 1.4658129805029452, \"fromPartnerName\" : \"fromPartnerName\", \"toPartnerId\" : 6, \"toPartnerName\" : \"toPartnerName\" }, { \"fromPartnerId\" : 0, \"amount\" : 1.4658129805029452, \"fromPartnerName\" : \"fromPartnerName\", \"toPartnerId\" : 6, \"toPartnerName\" : \"toPartnerName\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
