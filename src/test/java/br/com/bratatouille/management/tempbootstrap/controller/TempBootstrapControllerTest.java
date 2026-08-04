package br.com.bratatouille.management.tempbootstrap.controller;

import br.com.bratatouille.management.auth.dto.AuthResponse;
import br.com.bratatouille.management.auth.dto.BootstrapAdminRequest;
import br.com.bratatouille.management.auth.dto.CreateDashboardUserRequest;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TempBootstrapControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private AuthService authService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new TempBootstrapController(authService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void bootstrapAdminIsExposedWithoutAuth() throws Exception {
        when(authService.bootstrapAdmin(any(BootstrapAdminRequest.class))).thenReturn(sampleResponse("admin", UserRole.ADMIN));

        mockMvc.perform(post("/api/_temp/bootstrap/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BootstrapAdminRequest("admin", "secret123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void createDashboardUserIsExposedWithoutAuth() throws Exception {
        when(authService.createDashboardUser(any(CreateDashboardUserRequest.class))).thenReturn(sampleResponse("employee", UserRole.EMPLOYEE));

        mockMvc.perform(post("/api/_temp/bootstrap/dashboard/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateDashboardUserRequest("employee", "secret123", UserRole.EMPLOYEE))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("employee"))
                .andExpect(jsonPath("$.role").value("EMPLOYEE"));
    }

    private AuthResponse sampleResponse(String username, UserRole role) {
        return new AuthResponse("jwt-token", "Bearer", username, role, Instant.parse("2026-08-04T12:00:00Z"));
    }
}
