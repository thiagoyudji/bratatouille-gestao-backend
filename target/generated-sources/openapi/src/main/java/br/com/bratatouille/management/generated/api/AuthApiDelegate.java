package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.AuthResponse;
import br.com.bratatouille.management.generated.model.BootstrapAdminRequest;
import br.com.bratatouille.management.generated.model.CreateDashboardUserRequest;
import br.com.bratatouille.management.generated.model.LoginRequest;
import br.com.bratatouille.management.generated.model.RegisterCustomerRequest;
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
 * A delegate to be called by the {@link AuthApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public interface AuthApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/auth/bootstrap/admin : Bootstrap the first admin user
     *
     * @param bootstrapAdminRequest  (required)
     * @return Admin user created (status code 200)
     * @see AuthApi#bootstrapAdmin
     */
    default ResponseEntity<AuthResponse> bootstrapAdmin(BootstrapAdminRequest bootstrapAdminRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"role\" : \"ADMIN\", \"tokenType\" : \"Bearer\", \"expiresAt\" : \"2000-01-23T04:56:07.000+00:00\", \"token\" : \"token\", \"username\" : \"username\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/auth/dashboard/users : Create a dashboard user or PJ customer account
     *
     * @param createDashboardUserRequest  (required)
     * @return Dashboard user created (status code 200)
     * @see AuthApi#createDashboardUser
     */
    default ResponseEntity<AuthResponse> createDashboardUser(CreateDashboardUserRequest createDashboardUserRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"role\" : \"ADMIN\", \"tokenType\" : \"Bearer\", \"expiresAt\" : \"2000-01-23T04:56:07.000+00:00\", \"token\" : \"token\", \"username\" : \"username\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/auth/dashboard/login : Login for internal dashboard users
     *
     * @param loginRequest  (required)
     * @return Token issued (status code 200)
     * @see AuthApi#loginDashboard
     */
    default ResponseEntity<AuthResponse> loginDashboard(LoginRequest loginRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"role\" : \"ADMIN\", \"tokenType\" : \"Bearer\", \"expiresAt\" : \"2000-01-23T04:56:07.000+00:00\", \"token\" : \"token\", \"username\" : \"username\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/auth/ecommerce/login : Login for ecommerce customers and guests
     *
     * @param loginRequest  (required)
     * @return Token issued (status code 200)
     * @see AuthApi#loginEcommerce
     */
    default ResponseEntity<AuthResponse> loginEcommerce(LoginRequest loginRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"role\" : \"ADMIN\", \"tokenType\" : \"Bearer\", \"expiresAt\" : \"2000-01-23T04:56:07.000+00:00\", \"token\" : \"token\", \"username\" : \"username\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/auth/ecommerce/register : Register a new PF ecommerce customer
     *
     * @param registerCustomerRequest  (required)
     * @return Customer registered (status code 200)
     * @see AuthApi#registerCustomer
     */
    default ResponseEntity<AuthResponse> registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"role\" : \"ADMIN\", \"tokenType\" : \"Bearer\", \"expiresAt\" : \"2000-01-23T04:56:07.000+00:00\", \"token\" : \"token\", \"username\" : \"username\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
