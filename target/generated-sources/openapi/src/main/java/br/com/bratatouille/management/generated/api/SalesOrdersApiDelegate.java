package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
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
 * A delegate to be called by the {@link SalesOrdersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface SalesOrdersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/sales-orders : Create sales order
     *
     * @param salesOrderCreateRequest  (required)
     * @return Sales order created (status code 200)
     * @see SalesOrdersApi#createSalesOrder
     */
    default ResponseEntity<SalesOrderResponse> createSalesOrder(SalesOrderCreateRequest salesOrderCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"note\" : \"note\", \"paymentReceiptUrl\" : \"paymentReceiptUrl\", \"paymentCheckoutUrl\" : \"paymentCheckoutUrl\", \"paymentProviderTransactionId\" : \"paymentProviderTransactionId\", \"saleDate\" : \"2000-01-23\", \"paymentProviderStatus\" : \"paymentProviderStatus\", \"customerName\" : \"customerName\", \"paymentInvoiceSlug\" : \"paymentInvoiceSlug\", \"totalAmount\" : 6.027456183070403, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"customerType\" : \"GUEST\", \"customerPhone\" : \"customerPhone\", \"paymentProvider\" : \"paymentProvider\", \"deliveryAddress\" : { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true }, \"customerEmail\" : \"customerEmail\", \"paidAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"items\" : [ { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 }, { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 } ], \"paymentStatus\" : \"PENDING\", \"totalCost\" : 1.4658129805029452, \"grossProfit\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/sales-orders : List sales orders
     *
     * @return Sales orders found (status code 200)
     * @see SalesOrdersApi#findAllSalesOrders
     */
    default ResponseEntity<List<SalesOrderResponse>> findAllSalesOrders() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"note\" : \"note\", \"paymentReceiptUrl\" : \"paymentReceiptUrl\", \"paymentCheckoutUrl\" : \"paymentCheckoutUrl\", \"paymentProviderTransactionId\" : \"paymentProviderTransactionId\", \"saleDate\" : \"2000-01-23\", \"paymentProviderStatus\" : \"paymentProviderStatus\", \"customerName\" : \"customerName\", \"paymentInvoiceSlug\" : \"paymentInvoiceSlug\", \"totalAmount\" : 6.027456183070403, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"customerType\" : \"GUEST\", \"customerPhone\" : \"customerPhone\", \"paymentProvider\" : \"paymentProvider\", \"deliveryAddress\" : { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true }, \"customerEmail\" : \"customerEmail\", \"paidAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"items\" : [ { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 }, { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 } ], \"paymentStatus\" : \"PENDING\", \"totalCost\" : 1.4658129805029452, \"grossProfit\" : 5.962133916683182 }, { \"note\" : \"note\", \"paymentReceiptUrl\" : \"paymentReceiptUrl\", \"paymentCheckoutUrl\" : \"paymentCheckoutUrl\", \"paymentProviderTransactionId\" : \"paymentProviderTransactionId\", \"saleDate\" : \"2000-01-23\", \"paymentProviderStatus\" : \"paymentProviderStatus\", \"customerName\" : \"customerName\", \"paymentInvoiceSlug\" : \"paymentInvoiceSlug\", \"totalAmount\" : 6.027456183070403, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"customerType\" : \"GUEST\", \"customerPhone\" : \"customerPhone\", \"paymentProvider\" : \"paymentProvider\", \"deliveryAddress\" : { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true }, \"customerEmail\" : \"customerEmail\", \"paidAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"items\" : [ { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 }, { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 } ], \"paymentStatus\" : \"PENDING\", \"totalCost\" : 1.4658129805029452, \"grossProfit\" : 5.962133916683182 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/sales-orders/{id} : Find sales order by id
     *
     * @param id  (required)
     * @return Sales order found (status code 200)
     * @see SalesOrdersApi#findSalesOrderById
     */
    default ResponseEntity<SalesOrderResponse> findSalesOrderById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"note\" : \"note\", \"paymentReceiptUrl\" : \"paymentReceiptUrl\", \"paymentCheckoutUrl\" : \"paymentCheckoutUrl\", \"paymentProviderTransactionId\" : \"paymentProviderTransactionId\", \"saleDate\" : \"2000-01-23\", \"paymentProviderStatus\" : \"paymentProviderStatus\", \"customerName\" : \"customerName\", \"paymentInvoiceSlug\" : \"paymentInvoiceSlug\", \"totalAmount\" : 6.027456183070403, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"customerType\" : \"GUEST\", \"customerPhone\" : \"customerPhone\", \"paymentProvider\" : \"paymentProvider\", \"deliveryAddress\" : { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true }, \"customerEmail\" : \"customerEmail\", \"paidAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"items\" : [ { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 }, { \"unitPrice\" : 9.301444243932576, \"unitPricePj\" : 15.9, \"itemId\" : 2, \"itemName\" : \"itemName\", \"quantity\" : 7.061401241503109, \"unitPricePf\" : 12.9, \"totalPrice\" : 3.616076749251911, \"unitCost\" : 2.027123023002322, \"costIncomplete\" : true, \"id\" : 5, \"totalCost\" : 4.145608029883936, \"grossProfit\" : 7.386281948385884 } ], \"paymentStatus\" : \"PENDING\", \"totalCost\" : 1.4658129805029452, \"grossProfit\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
