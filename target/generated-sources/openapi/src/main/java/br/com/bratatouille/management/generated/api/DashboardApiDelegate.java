package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.DashboardOverviewResponse;
import org.springframework.format.annotation.DateTimeFormat;
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
 * A delegate to be called by the {@link DashboardApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface DashboardApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/dashboard/overview : Get operational dashboard overview
     *
     * @param startDate  (required)
     * @param endDate  (required)
     * @return Dashboard overview (status code 200)
     * @see DashboardApi#getDashboardOverview
     */
    default ResponseEntity<DashboardOverviewResponse> getDashboardOverview(LocalDate startDate,
        LocalDate endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"criticalStockAlerts\" : 7, \"marginWarning\" : \"marginWarning\", \"endDate\" : \"2000-01-23\", \"hasCostIncomplete\" : true, \"marginReliable\" : true, \"netProfit\" : 2.027123023002322, \"costIncompleteItems\" : 6, \"lowStockAlerts\" : 1, \"totalOperationalCost\" : 2.3021358869347655, \"nearZeroStockAlerts\" : 1, \"financialOperationalCost\" : 3.616076749251911, \"fixedOperationalCost\" : 7.061401241503109, \"netMarginPercentage\" : 4.145608029883936, \"openPartnerBalances\" : 1, \"totalOrders\" : 0, \"totalRevenue\" : 6.027456183070403, \"variableOperationalCost\" : 9.301444243932576, \"startDate\" : \"2000-01-23\", \"totalCost\" : 1.4658129805029452, \"grossProfit\" : 5.962133916683182, \"grossMarginPercentage\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
