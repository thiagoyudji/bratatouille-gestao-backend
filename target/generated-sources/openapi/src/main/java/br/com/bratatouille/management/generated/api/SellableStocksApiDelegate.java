package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.SellableStockResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
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
 * A delegate to be called by the {@link SellableStocksApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface SellableStocksApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/sellable-stocks : List sellable stocks
     *
     * @return Sellable stocks found (status code 200)
     * @see SellableStocksApi#findAllSellableStocks
     */
    default ResponseEntity<List<SellableStockResponse>> findAllSellableStocks() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"itemId\" : 0, \"availableQuantity\" : 6.027456183070403, \"itemName\" : \"itemName\", \"currentStockQuantity\" : 1.4658129805029452, \"infinite\" : true, \"pricePf\" : 5.962133916683182, \"pricePj\" : 5.637376656633329, \"enabled\" : true }, { \"itemId\" : 0, \"availableQuantity\" : 6.027456183070403, \"itemName\" : \"itemName\", \"currentStockQuantity\" : 1.4658129805029452, \"infinite\" : true, \"pricePf\" : 5.962133916683182, \"pricePj\" : 5.637376656633329, \"enabled\" : true } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/sellable-stocks/{itemId} : Find sellable stock by item id
     *
     * @param itemId  (required)
     * @return Sellable stock found (status code 200)
     * @see SellableStocksApi#findSellableStockByItemId
     */
    default ResponseEntity<SellableStockResponse> findSellableStockByItemId(Long itemId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 0, \"availableQuantity\" : 6.027456183070403, \"itemName\" : \"itemName\", \"currentStockQuantity\" : 1.4658129805029452, \"infinite\" : true, \"pricePf\" : 5.962133916683182, \"pricePj\" : 5.637376656633329, \"enabled\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /api/sellable-stocks/{itemId} : Create or update sellable stock
     *
     * @param itemId  (required)
     * @param sellableStockUpsertRequest  (required)
     * @return Sellable stock updated (status code 200)
     * @see SellableStocksApi#upsertSellableStock
     */
    default ResponseEntity<SellableStockResponse> upsertSellableStock(Long itemId,
        SellableStockUpsertRequest sellableStockUpsertRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"itemId\" : 0, \"availableQuantity\" : 6.027456183070403, \"itemName\" : \"itemName\", \"currentStockQuantity\" : 1.4658129805029452, \"infinite\" : true, \"pricePf\" : 5.962133916683182, \"pricePj\" : 5.637376656633329, \"enabled\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
