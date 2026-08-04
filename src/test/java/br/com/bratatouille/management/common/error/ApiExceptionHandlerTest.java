package br.com.bratatouille.management.common.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new ApiExceptionHandler(new ApiErrorResponseFactory()))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .setValidator(validator)
                .build();
    }

    @Test
    void validationErrorsAreReturnedInStructuredPayload() throws Exception {
        mockMvc.perform(post("/test-errors/validation")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath("$.fieldErrors[0].field").exists())
                .andExpect(jsonPath("$.message", is("Existem campos inválidos na requisição.")));
    }

    @Test
    void illegalArgumentIsMappedToBusinessRuleViolation() throws Exception {
        mockMvc.perform(get("/test-errors/illegal"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is("BUSINESS_RULE_VIOLATION")))
                .andExpect(jsonPath("$.message", is("O campo 'item' é obrigatório.")));
    }

    @Test
    void responseStatusExceptionIsPreserved() throws Exception {
        mockMvc.perform(get("/test-errors/status"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is("CONFLICT")))
                .andExpect(jsonPath("$.message", is("Já existe um usuário com esse nome.")));
    }

    @Test
    void authExceptionInvalidCredentialsIsMapped() throws Exception {
        mockMvc.perform(get("/test-errors/auth-invalid"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is("AUTH_INVALID_CREDENTIALS")))
                .andExpect(jsonPath("$.message", is("Credenciais inválidas.")));
    }

    @Test
    void authExceptionBootstrapCompletedIsMapped() throws Exception {
        mockMvc.perform(get("/test-errors/auth-bootstrap"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is("AUTH_BOOTSTRAP_ALREADY_COMPLETED")))
                .andExpect(jsonPath("$.message", is("O bootstrap do administrador já foi concluído.")));
    }

    @Test
    void authExceptionUsernameExistsIsMapped() throws Exception {
        mockMvc.perform(get("/test-errors/auth-username"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is("AUTH_USERNAME_ALREADY_EXISTS")))
                .andExpect(jsonPath("$.message", is("Já existe um usuário com esse nome.")));
    }

    @Test
    void authExceptionCustomerRoleIsMapped() throws Exception {
        mockMvc.perform(get("/test-errors/auth-customer-role"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE")))
                .andExpect(jsonPath("$.message", is("O papel de cliente deve ser criado pelo fluxo de e-commerce.")));
    }

    @RestController
    @RequestMapping("/test-errors")
    static class TestController {

        @PostMapping("/validation")
        ResponseEntity<Void> validate(@Valid @RequestBody ValidationRequest request) {
            return ResponseEntity.ok().build();
        }

        @GetMapping("/illegal")
        ResponseEntity<Void> illegal() {
            throw new IllegalArgumentException("item is required");
        }

        @GetMapping("/status")
        ResponseEntity<Void> status() {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
        }

        @GetMapping("/auth-invalid")
        ResponseEntity<Void> authInvalid() {
            throw new AuthException(HttpStatus.UNAUTHORIZED, ApiErrorCode.AUTH_INVALID_CREDENTIALS, "invalid credentials");
        }

        @GetMapping("/auth-bootstrap")
        ResponseEntity<Void> authBootstrap() {
            throw new AuthException(HttpStatus.CONFLICT, ApiErrorCode.AUTH_BOOTSTRAP_ALREADY_COMPLETED, "bootstrap already completed");
        }

        @GetMapping("/auth-username")
        ResponseEntity<Void> authUsername() {
            throw new AuthException(HttpStatus.CONFLICT, ApiErrorCode.AUTH_USERNAME_ALREADY_EXISTS, "username already exists");
        }

        @GetMapping("/auth-customer-role")
        ResponseEntity<Void> authCustomerRole() {
            throw new AuthException(HttpStatus.BAD_REQUEST, ApiErrorCode.AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE, "customer role must be created via ecommerce");
        }
    }

    record ValidationRequest(
            @NotBlank String name,
            @NotNull @Positive Integer quantity
    ) {
    }
}
