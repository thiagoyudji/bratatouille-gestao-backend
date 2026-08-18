package br.com.bratatouille.management.auth.security;

import br.com.bratatouille.management.auth.dto.BootstrapAdminRequest;
import br.com.bratatouille.management.auth.dto.CreateDashboardUserRequest;
import br.com.bratatouille.management.auth.dto.RegisterCustomerRequest;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.customer.dto.CustomerAddressResponse;
import br.com.bratatouille.management.customer.dto.CustomerAddressRequest;
import br.com.bratatouille.management.customer.dto.CustomerProfileRequest;
import br.com.bratatouille.management.customer.entity.CustomerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class AuthSecurityIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void anonymousRequestIsRejectedFromProtectedInternalEndpoint() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void anonymousRequestCanAccessPublicSellableStockCatalog() throws Exception {
        mockMvc.perform(get("/api/sellable-stocks"))
                .andExpect(status().isOk());
    }

    @Test
    void anonymousRequestCannotAccessAdministrativeSellableStockCatalog() throws Exception {
        mockMvc.perform(get("/api/admin/sellable-stocks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void bootstrapAdminProducesTokenThatCanAccessInternalEndpoints() throws Exception {
        String token = mockMvc.perform(post("/api/auth/bootstrap/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BootstrapAdminRequest("admin", "secret123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String extractedToken = objectMapper.readTree(token).get("token").asText();

        mockMvc.perform(get("/api/items")
                        .header("Authorization", "Bearer " + extractedToken))
                .andExpect(status().isOk());
    }

    @Test
    void customerTokenCanAccessEcommerceEndpointsButNotInternalAdminEndpoints() throws Exception {
        String responseBody = mockMvc.perform(post("/api/auth/ecommerce/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterCustomerRequest("customer", "secret123", sampleProfile(CustomerType.PF)))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String customerToken = objectMapper.readTree(responseBody).get("token").asText();

        mockMvc.perform(get("/api/items")
                        .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/sellable-stocks")
                        .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/customers/me")
                        .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerType").value("PF"));

        mockMvc.perform(post("/api/auth/dashboard/users")
                        .header("Authorization", "Bearer " + customerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateDashboardUserRequest("employee", "secret123", UserRole.EMPLOYEE, null))))
                .andExpect(status().isForbidden());
    }

    @Test
    void malformedTokenIsRejectedOnProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/api/items")
                        .header("Authorization", "Bearer not-a-valid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void dashboardUserBootstrapRequiresAdminToken() throws Exception {
        mockMvc.perform(post("/api/_temp/bootstrap/dashboard/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateDashboardUserRequest("employee", "secret123", UserRole.EMPLOYEE, null))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminTokenCanCreateBusinessCustomerFromBootstrapEndpoint() throws Exception {
        String token = mockMvc.perform(post("/api/auth/bootstrap/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BootstrapAdminRequest("admin2", "secret123"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String adminToken = objectMapper.readTree(token).get("token").asText();

        mockMvc.perform(post("/api/_temp/bootstrap/dashboard/users")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateDashboardUserRequest("business", "secret123", UserRole.CUSTOMER, sampleProfile(CustomerType.PJ)))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role", notNullValue()))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    void adminTokenCanCreateDashboardUserThroughPublicContractRoute() throws Exception {
        String token = mockMvc.perform(post("/api/auth/bootstrap/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BootstrapAdminRequest("admin3", "secret123"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String adminToken = objectMapper.readTree(token).get("token").asText();

        mockMvc.perform(post("/api/auth/dashboard/users")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateDashboardUserRequest("employee2", "secret123", UserRole.EMPLOYEE, null))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    private CustomerProfileRequest sampleProfile(CustomerType customerType) {
        return new CustomerProfileRequest(
                customerType,
                "Customer Name",
                "customer@example.com",
                "11999999999",
                java.util.List.of(new CustomerAddressRequest(
                        "home",
                        "01001000",
                        "Street",
                        "123",
                        "Center",
                        "SP",
                        "Sao Paulo",
                        null,
                        true
                ))
        );
    }
}
