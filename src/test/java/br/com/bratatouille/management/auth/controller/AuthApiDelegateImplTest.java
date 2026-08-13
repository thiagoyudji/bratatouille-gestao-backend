package br.com.bratatouille.management.auth.controller;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.auth.security.JwtService;
import br.com.bratatouille.management.auth.service.AuthService;
import br.com.bratatouille.management.customer.mapper.CustomerContractMapper;
import br.com.bratatouille.management.customer.repository.CustomerProfileRepository;
import br.com.bratatouille.management.support.builder.AuthUserBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiDelegateImplTest {

    private AuthUserRepository authUserRepository;
    private CustomerProfileRepository customerProfileRepository;
    private CustomerContractMapper customerContractMapper;
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        authUserRepository = mock(AuthUserRepository.class);
        customerProfileRepository = mock(CustomerProfileRepository.class);
        customerContractMapper = new CustomerContractMapper();
        passwordEncoder = mock(org.springframework.security.crypto.password.PasswordEncoder.class);
        jwtService = mock(JwtService.class);

        AuthService authService = new AuthService(authUserRepository, customerProfileRepository, passwordEncoder, jwtService);
        AuthApiDelegateImpl delegate = new AuthApiDelegateImpl(authService, customerContractMapper);

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        mockMvc = MockMvcBuilders.standaloneSetup(new br.com.bratatouille.management.generated.api.AuthApiController(delegate))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void loginDashboardReturnsTokenThroughGeneratedEndpoint() throws Exception {
        AuthUser user = new AuthUserBuilder()
                .withUsername("admin")
                .withPasswordHash("hashed-secret")
                .withRole(UserRole.ADMIN)
                .build();

        when(authUserRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret123", "hashed-secret")).thenReturn(true);
        when(jwtService.generateToken(any(AuthUser.class))).thenReturn("jwt-token");
        when(jwtService.extractExpiresAt("jwt-token")).thenReturn(Instant.parse("2026-08-04T12:00:00Z"));

        mockMvc.perform(post("/api/auth/dashboard/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"secret123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.expiresAt", notNullValue()));
    }
}
