package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
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
 * A delegate to be called by the {@link PurchasesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface PurchasesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/purchases : Create purchase
     *
     * @param purchaseCreateRequest  (required)
     * @return Purchase created (status code 200)
     * @see PurchasesApi#createPurchase
     */
    default ResponseEntity<PurchaseResponse> createPurchase(PurchaseCreateRequest purchaseCreateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"note\" : \"note\", \"paidByPartnerId\" : 6, \"totalAmount\" : 1.4658129805029452, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"purchaseDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 }, { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"supplier\" : \"supplier\", \"id\" : 0, \"items\" : [ { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 }, { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/purchases : List all purchases
     *
     * @return Purchases found (status code 200)
     * @see PurchasesApi#findAllPurchases
     */
    default ResponseEntity<List<PurchaseResponse>> findAllPurchases() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"note\" : \"note\", \"paidByPartnerId\" : 6, \"totalAmount\" : 1.4658129805029452, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"purchaseDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 }, { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"supplier\" : \"supplier\", \"id\" : 0, \"items\" : [ { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 }, { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 } ] }, { \"note\" : \"note\", \"paidByPartnerId\" : 6, \"totalAmount\" : 1.4658129805029452, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"purchaseDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 }, { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"supplier\" : \"supplier\", \"id\" : 0, \"items\" : [ { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 }, { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 } ] } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/purchases/{id} : Find purchase by id
     *
     * @param id  (required)
     * @return Purchase found (status code 200)
     * @see PurchasesApi#findPurchaseById
     */
    default ResponseEntity<PurchaseResponse> findPurchaseById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"note\" : \"note\", \"paidByPartnerId\" : 6, \"totalAmount\" : 1.4658129805029452, \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"purchaseDate\" : \"2000-01-23\", \"splits\" : [ { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 }, { \"owedAmount\" : 4.145608029883936, \"partnerName\" : \"partnerName\", \"percentage\" : 2.027123023002322, \"id\" : 9, \"partnerId\" : 3 } ], \"paidByPartnerName\" : \"paidByPartnerName\", \"supplier\" : \"supplier\", \"id\" : 0, \"items\" : [ { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 }, { \"totalValue\" : 7.061401241503109, \"itemId\" : 5, \"itemName\" : \"itemName\", \"unit\" : \"G\", \"quantity\" : 2.3021358869347655, \"id\" : 5 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
