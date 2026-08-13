package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.CustomerProfileRequest;
import br.com.bratatouille.management.generated.model.CustomerProfileResponse;
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
 * A delegate to be called by the {@link CustomersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface CustomersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/customers/me : Get the authenticated customer profile
     *
     * @return Customer profile found (status code 200)
     * @see CustomersApi#getMyCustomerProfile
     */
    default ResponseEntity<CustomerProfileResponse> getMyCustomerProfile() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"customerType\" : \"PF\", \"addresses\" : [ { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true }, { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true } ], \"phone\" : \"phone\", \"fullName\" : \"fullName\", \"email\" : \"email\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /api/customers/me : Update the authenticated customer profile
     *
     * @param customerProfileRequest  (required)
     * @return Customer profile updated (status code 200)
     * @see CustomersApi#updateMyCustomerProfile
     */
    default ResponseEntity<CustomerProfileResponse> updateMyCustomerProfile(CustomerProfileRequest customerProfileRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"customerType\" : \"PF\", \"addresses\" : [ { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true }, { \"zipCode\" : \"zipCode\", \"number\" : \"number\", \"city\" : \"city\", \"street\" : \"street\", \"label\" : \"label\", \"neighborhood\" : \"neighborhood\", \"state\" : \"state\", \"complement\" : \"complement\", \"defaultAddress\" : true } ], \"phone\" : \"phone\", \"fullName\" : \"fullName\", \"email\" : \"email\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
