package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.AdjustStockRequest;
import br.com.bratatouille.management.generated.model.StockAlertResponse;
import br.com.bratatouille.management.generated.model.StockMovementResponse;
import br.com.bratatouille.management.generated.model.StockResponse;
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
 * A delegate to be called by the {@link StocksApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface StocksApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/stocks/{itemId}/adjust
     *
     * @param itemId  (required)
     * @param adjustStockRequest  (required)
     * @return Stock adjusted (status code 200)
     * @see StocksApi#adjustStockManually
     */
    default ResponseEntity<StockResponse> adjustStockManually(Long itemId,
        AdjustStockRequest adjustStockRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 6, \"baseUnit\" : \"G\", \"itemName\" : \"itemName\", \"itemType\" : \"INGREDIENT\", \"quantity\" : 1.4658129805029452, \"pricePf\" : 5.962133916683182, \"id\" : 0, \"pricePj\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/stocks : List all stocks
     *
     * @return Stocks found (status code 200)
     * @see StocksApi#findAllStocks
     */
    default ResponseEntity<List<StockResponse>> findAllStocks() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 6, \"baseUnit\" : \"G\", \"itemName\" : \"itemName\", \"itemType\" : \"INGREDIENT\", \"quantity\" : 1.4658129805029452, \"pricePf\" : 5.962133916683182, \"id\" : 0, \"pricePj\" : 5.637376656633329 }, { \"itemId\" : 6, \"baseUnit\" : \"G\", \"itemName\" : \"itemName\", \"itemType\" : \"INGREDIENT\", \"quantity\" : 1.4658129805029452, \"pricePf\" : 5.962133916683182, \"id\" : 0, \"pricePj\" : 5.637376656633329 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/stocks/alerts : List stock alerts
     *
     * @return Stock alerts found (status code 200)
     * @see StocksApi#findStockAlerts
     */
    default ResponseEntity<List<StockAlertResponse>> findStockAlerts() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 0, \"baseUnit\" : \"G\", \"itemName\" : \"itemName\", \"itemType\" : \"INGREDIENT\", \"lowStockThreshold\" : 1.4658129805029452, \"currentQuantity\" : 6.027456183070403, \"criticalStockThreshold\" : 5.962133916683182, \"status\" : \"LOW\" }, { \"itemId\" : 0, \"baseUnit\" : \"G\", \"itemName\" : \"itemName\", \"itemType\" : \"INGREDIENT\", \"lowStockThreshold\" : 1.4658129805029452, \"currentQuantity\" : 6.027456183070403, \"criticalStockThreshold\" : 5.962133916683182, \"status\" : \"LOW\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/stocks/{itemId}
     *
     * @param itemId  (required)
     * @return Stock found (status code 200)
     * @see StocksApi#findStockByItemId
     */
    default ResponseEntity<StockResponse> findStockByItemId(Long itemId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 6, \"baseUnit\" : \"G\", \"itemName\" : \"itemName\", \"itemType\" : \"INGREDIENT\", \"quantity\" : 1.4658129805029452, \"pricePf\" : 5.962133916683182, \"id\" : 0, \"pricePj\" : 5.637376656633329 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/stocks/movements
     *
     * @return Stock movements found (status code 200)
     * @see StocksApi#findStockMovements
     */
    default ResponseEntity<List<StockMovementResponse>> findStockMovements() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"sourceId\" : 6, \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 5.962133916683182, \"id\" : 0, \"type\" : \"PURCHASE_ENTRY\" }, { \"sourceId\" : 6, \"itemId\" : 1, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"itemName\" : \"itemName\", \"quantity\" : 5.962133916683182, \"id\" : 0, \"type\" : \"PURCHASE_ENTRY\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
