package br.com.bratatouille.management.customer.controller;

import br.com.bratatouille.management.auth.dto.RegisterCustomerRequest;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
class CustomerApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void customerCanReadAndUpdateOwnProfile() throws Exception {
        String token = mockMvc.perform(post("/api/auth/ecommerce/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterCustomerRequest(
                                "customer-profile",
                                "secret123",
                                new CustomerProfileRequest(
                                        CustomerType.PF,
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
                                )
                        ))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String customerToken = objectMapper.readTree(token).get("token").asText();

        mockMvc.perform(get("/api/customers/me")
                        .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerType").value("PF"))
                .andExpect(jsonPath("$.addresses[0].defaultAddress").value(true));

        Map<String, Object> updateRequest = Map.of(
                "customerType", "PF",
                "fullName", "Customer Name Updated",
                "email", "customer.updated@example.com",
                "phone", "11888888888",
                "addresses", java.util.List.of(Map.of(
                        "label", "Office",
                        "zipCode", "01310930",
                        "street", "Avenida Paulista",
                        "number", "1000",
                        "neighborhood", "Bela Vista",
                        "state", "SP",
                        "city", "Sao Paulo",
                        "complement", "10th floor",
                        "defaultAddress", true
                ))
        );

        mockMvc.perform(put("/api/customers/me")
                        .header("Authorization", "Bearer " + customerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Customer Name Updated"))
                .andExpect(jsonPath("$.addresses[0].label").value("Office"))
                .andExpect(jsonPath("$.addresses[0].defaultAddress").value(true));

        mockMvc.perform(get("/api/customers/me")
                        .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("customer.updated@example.com"))
                .andExpect(jsonPath("$.addresses[0].zipCode").value("01310930"));
    }

    @Test
    void customerProfileRouteRejectsAnonymousAccess() throws Exception {
        mockMvc.perform(get("/api/customers/me"))
                .andExpect(status().isUnauthorized());
    }
}
