package br.com.bratatouille.management.generated.api;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import br.com.bratatouille.management.generated.model.SalesProductPerformanceResponse;
import br.com.bratatouille.management.generated.model.SalesSummaryResponse;
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
 * A delegate to be called by the {@link SalesReportsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface SalesReportsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/sales-reports/products : Get sales product performance by period
     * Groups product performance using the actual sold price stored on each order item, so PF and PJ prices remain consistent in reporting.
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Sales product performance (status code 200)
     * @see SalesReportsApi#getSalesProductPerformance
     */
    default ResponseEntity<List<SalesProductPerformanceResponse>> getSalesProductPerformance(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 10, \"itemName\" : \"Pizza Margherita\", \"soldQuantity\" : 2, \"totalAmount\" : 43.4, \"totalCost\" : 0, \"grossProfit\" : 43.4, \"grossMarginPercentage\" : 100 }, { \"itemId\" : 10, \"itemName\" : \"Pizza Margherita\", \"soldQuantity\" : 2, \"totalAmount\" : 43.4, \"totalCost\" : 0, \"grossProfit\" : 43.4, \"grossMarginPercentage\" : 100 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/sales-reports/summary : Get sales summary by period
     * Aggregates the real sold amount, cost and gross profit for the period. PF, PJ and guest sales are already reflected in the stored order totals.
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Sales summary (status code 200)
     * @see SalesReportsApi#getSalesSummary
     */
    default ResponseEntity<SalesSummaryResponse> getSalesSummary(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"startDate\" : \"2026-08-01\", \"endDate\" : \"2026-08-31\", \"totalOrders\" : 2, \"totalAmount\" : 43.4, \"totalCost\" : 0, \"grossProfit\" : 43.4, \"averageTicket\" : 21.7, \"grossMarginPercentage\" : 100, \"costIncompleteItems\" : 2, \"hasCostIncomplete\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
