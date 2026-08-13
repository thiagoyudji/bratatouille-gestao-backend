package br.com.bratatouille.management.payment.service;

import br.com.bratatouille.management.generated.model.InfinitePayCheckoutResponse;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.auth.security.JwtService;
import br.com.bratatouille.management.payment.gateway.InfinitePayCheckoutGateway;
import br.com.bratatouille.management.payment.gateway.InfinitePayCheckoutGateway.InfinitePayCheckoutResult;
import br.com.bratatouille.management.support.TestDatabaseSeeder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InfinitePayCheckoutServiceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private br.com.bratatouille.management.auth.repository.AuthUserRepository authUserRepository;

    @Autowired
    private br.com.bratatouille.management.customer.repository.CustomerProfileRepository customerProfileRepository;

    @Autowired
    private br.com.bratatouille.management.partner.repository.PartnerRepository partnerRepository;

    @Autowired
    private br.com.bratatouille.management.item.repository.ItemRepository itemRepository;

    @Autowired
    private br.com.bratatouille.management.stock.repository.StockRepository stockRepository;

    @Autowired
    private br.com.bratatouille.management.sellableStock.repository.SellableStockRepository sellableStockRepository;

    @Autowired
    private br.com.bratatouille.management.sales.service.SalesOrderService salesOrderService;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private InfinitePayCheckoutGateway infinitePayCheckoutGateway;

    @Test
    void createsCheckoutLinkForPublicGuestFlow() throws Exception {
        TestDatabaseSeeder.TestDatabaseSnapshot snapshot = new TestDatabaseSeeder(
                authUserRepository,
                customerProfileRepository,
                partnerRepository,
                itemRepository,
                stockRepository,
                sellableStockRepository
        ).seedDefault();

        var item = snapshot.item("pizzaMargherita");

        when(infinitePayCheckoutGateway.createCheckout(any()))
                .thenReturn(new InfinitePayCheckoutResult("https://buy.test/checkout/abc123", "abc123"));

        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 5));
        request.setCustomerType(SalesOrderCustomerType.PJ);
        request.setCustomerName("Guest Buyer");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(item.getId());
        itemRequest.setQuantity(new BigDecimal("1"));
        request.setItems(List.of(itemRequest));

        MvcResult result = mockMvc.perform(post("/api/payments/infinitepay/checkouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.provider").value("INFINITEPAY"))
                .andExpect(jsonPath("$.checkoutUrl").value("https://buy.test/checkout/abc123"))
                .andExpect(jsonPath("$.invoiceSlug").value("abc123"))
                .andExpect(jsonPath("$.paymentStatus").value("PENDING"))
                .andExpect(jsonPath("$.orderId").isNumber())
                .andReturn();

        long orderId = objectMapper.readTree(result.getResponse().getContentAsString()).get("orderId").asLong();
        SalesOrderResponse persistedOrder = salesOrderService.findById(orderId);
        org.junit.jupiter.api.Assertions.assertEquals(SalesOrderCustomerType.GUEST, persistedOrder.getCustomerType());
        org.junit.jupiter.api.Assertions.assertEquals(new BigDecimal("18.50"), persistedOrder.getTotalAmount());
        org.junit.jupiter.api.Assertions.assertEquals("https://buy.test/checkout/abc123", persistedOrder.getPaymentCheckoutUrl());
        org.junit.jupiter.api.Assertions.assertEquals("abc123", persistedOrder.getPaymentInvoiceSlug());
    }

    @Test
    void usesAuthenticatedBusinessProfileAndPjPrice() throws Exception {
        TestDatabaseSeeder.TestDatabaseSnapshot snapshot = new TestDatabaseSeeder(
                authUserRepository,
                customerProfileRepository,
                partnerRepository,
                itemRepository,
                stockRepository,
                sellableStockRepository
        ).seedDefault();

        var item = snapshot.item("pizzaMargherita");
        String token = jwtService.generateToken(snapshot.authUser("customerPj"));

        when(infinitePayCheckoutGateway.createCheckout(any()))
                .thenReturn(new InfinitePayCheckoutResult("https://buy.test/checkout/pj", "pj"));

        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 5));
        request.setCustomerType(SalesOrderCustomerType.PF);
        request.setCustomerName("Manipulated name");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(item.getId());
        itemRequest.setQuantity(new BigDecimal("1"));
        request.setItems(List.of(itemRequest));

        MvcResult result = mockMvc.perform(post("/api/payments/infinitepay/checkouts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        long orderId = objectMapper.readTree(result.getResponse().getContentAsString()).get("orderId").asLong();
        SalesOrderResponse persistedOrder = salesOrderService.findById(orderId);
        org.junit.jupiter.api.Assertions.assertEquals(SalesOrderCustomerType.PJ, persistedOrder.getCustomerType());
        org.junit.jupiter.api.Assertions.assertEquals("Loja PJ Teste LTDA", persistedOrder.getCustomerName());
        org.junit.jupiter.api.Assertions.assertEquals(new BigDecimal("24.90"), persistedOrder.getTotalAmount());
    }
}
