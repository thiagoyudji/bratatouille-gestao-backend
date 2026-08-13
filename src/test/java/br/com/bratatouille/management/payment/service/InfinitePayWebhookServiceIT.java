package br.com.bratatouille.management.payment.service;

import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.generated.model.SellableStockResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway.InfinitePayPaymentVerification;
import br.com.bratatouille.management.sales.entity.SalesPaymentStatus;
import br.com.bratatouille.management.sales.service.SalesOrderService;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InfinitePayWebhookServiceIT {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private InfinitePayWebhookService infinitePayWebhookService;

    @Autowired
    private SellableStockService sellableStockService;

    @Autowired
    private ItemRepository itemRepository;

    @MockBean
    private InfinitePayPaymentVerificationGateway paymentVerificationGateway;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void approvesPendingOrderWithoutChangingReservedStock() {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));

        SalesOrderResponse createdOrder = salesOrderService.create(order(item.getId()));
        assertEquals(SalesPaymentStatus.PENDING, SalesPaymentStatus.valueOf(createdOrder.getPaymentStatus().name()));

        mockVerifiedPayment(true, 1850, 1850);
        InfinitePayWebhookRequest request = webhook(createdOrder.getId(), "TXN-" + createdOrder.getId());
        SalesOrderResponse updatedOrder = infinitePayWebhookService.handle(request);

        assertEquals(SalesPaymentStatus.APPROVED, SalesPaymentStatus.valueOf(updatedOrder.getPaymentStatus().name()));
        assertEquals("INFINITEPAY", updatedOrder.getPaymentProvider());
        assertEquals("TXN-" + createdOrder.getId(), updatedOrder.getPaymentProviderTransactionId());
        assertEquals("PAID", updatedOrder.getPaymentProviderStatus());
        assertNotNull(updatedOrder.getPaidAt());

        SellableStockResponse sellableStock = sellableStockService.findByItemId(item.getId());
        assertEquals(new BigDecimal("9.000"), sellableStock.getAvailableQuantity());
    }

    @Test
    void duplicateApprovalWebhookDoesNotApplyTwice() {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));

        SalesOrderResponse createdOrder = salesOrderService.create(order(item.getId()));
        mockVerifiedPayment(true, 1850, 1850);
        InfinitePayWebhookRequest request = webhook(createdOrder.getId(), "TXN-" + createdOrder.getId());

        infinitePayWebhookService.handle(request);
        SalesOrderResponse secondCall = infinitePayWebhookService.handle(request);

        assertEquals(SalesPaymentStatus.APPROVED, SalesPaymentStatus.valueOf(secondCall.getPaymentStatus().name()));

        SellableStockResponse sellableStock = sellableStockService.findByItemId(item.getId());
        assertEquals(new BigDecimal("9.000"), sellableStock.getAvailableQuantity());
    }

    @Test
    void rejectsWebhookWhenPaymentCheckDoesNotConfirmPayment() {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));

        SalesOrderResponse createdOrder = salesOrderService.create(order(item.getId()));
        mockVerifiedPayment(false, 1850, 1850);
        InfinitePayWebhookRequest request = webhook(createdOrder.getId(), "TXN-" + createdOrder.getId());

        assertThrows(IllegalArgumentException.class, () -> infinitePayWebhookService.handle(request));

        SalesOrderResponse unchangedOrder = salesOrderService.findById(createdOrder.getId());
        assertEquals(SalesPaymentStatus.PENDING, SalesPaymentStatus.valueOf(unchangedOrder.getPaymentStatus().name()));

        SellableStockResponse sellableStock = sellableStockService.findByItemId(item.getId());
        assertEquals(new BigDecimal("9.000"), sellableStock.getAvailableQuantity());
    }

    @Test
    void rejectsWebhookWhenVerifiedAmountDoesNotMatchOrder() {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));

        SalesOrderResponse createdOrder = salesOrderService.create(order(item.getId()));
        mockVerifiedPayment(true, 1900, 1900);

        assertThrows(
                IllegalArgumentException.class,
                () -> infinitePayWebhookService.handle(webhook(createdOrder.getId(), "TXN-" + createdOrder.getId()))
        );

        SalesOrderResponse unchangedOrder = salesOrderService.findById(createdOrder.getId());
        assertEquals(SalesPaymentStatus.PENDING, SalesPaymentStatus.valueOf(unchangedOrder.getPaymentStatus().name()));
    }

    @Test
    void rejectsTransactionAlreadyLinkedToAnotherOrder() {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));

        SalesOrderResponse firstOrder = salesOrderService.create(order(item.getId()));
        SalesOrderResponse secondOrder = salesOrderService.create(order(item.getId()));
        String transactionNsu = "TXN-SHARED";
        mockVerifiedPayment(true, 1850, 1850);

        infinitePayWebhookService.handle(webhook(firstOrder.getId(), transactionNsu));

        assertThrows(
                IllegalStateException.class,
                () -> infinitePayWebhookService.handle(webhook(secondOrder.getId(), transactionNsu))
        );
    }

    @Test
    void acceptsOfficialSnakeCasePayloadAndReturnsProviderAcknowledgement() throws Exception {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));
        SalesOrderResponse createdOrder = salesOrderService.create(order(item.getId()));
        mockVerifiedPayment(true, 1850, 1850);

        mockMvc.perform(post("/api/payments/webhooks/infinitepay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "invoice_slug": "invoice-%d",
                                  "amount": 1850,
                                  "paid_amount": 1850,
                                  "installments": 1,
                                  "capture_method": "pix",
                                  "transaction_nsu": "TXN-%d",
                                  "order_nsu": "%d",
                                  "receipt_url": "https://receipt.test/%d"
                                }
                                """.formatted(
                                createdOrder.getId(),
                                createdOrder.getId(),
                                createdOrder.getId(),
                                createdOrder.getId()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void returnsProviderRetryResponseWhenPaymentIsNotConfirmed() throws Exception {
        Item item = seedFinishedProduct();
        upsertSellableStock(item.getId(), new BigDecimal("10.000"));
        SalesOrderResponse createdOrder = salesOrderService.create(order(item.getId()));
        mockVerifiedPayment(false, 1850, 1850);

        mockMvc.perform(post("/api/payments/webhooks/infinitepay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "invoice_slug": "invoice-%d",
                                  "amount": 1850,
                                  "paid_amount": 1850,
                                  "transaction_nsu": "TXN-%d",
                                  "order_nsu": "%d"
                                }
                                """.formatted(
                                createdOrder.getId(),
                                createdOrder.getId(),
                                createdOrder.getId()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    private Item seedFinishedProduct() {
        return itemRepository.save(new Item(
                "InfinitePay Pizza",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));
    }

    private void upsertSellableStock(Long itemId, BigDecimal availableQuantity) {
        SellableStockUpsertRequest request = new SellableStockUpsertRequest();
        request.setAvailableQuantity(availableQuantity);
        request.setInfinite(false);
        request.setEnabled(true);
        sellableStockService.upsert(itemId, request);
    }

    private SalesOrderCreateRequest order(Long itemId) {
        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 5));
        request.setCustomerType(SalesOrderCustomerType.PF);
        request.setCustomerName("Webhook customer");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(itemId);
        itemRequest.setQuantity(new BigDecimal("1"));

        request.setItems(List.of(itemRequest));
        return request;
    }

    private InfinitePayWebhookRequest webhook(Long orderId, String transactionNsu) {
        InfinitePayWebhookRequest request = new InfinitePayWebhookRequest();
        request.setOrderNsu(String.valueOf(orderId));
        request.setTransactionNsu(transactionNsu);
        request.setInvoiceSlug("invoice-" + orderId);
        request.setAmount(1850L);
        request.setPaidAmount(1850L);
        request.setReceiptUrl("https://receipt.test/" + orderId);
        return request;
    }

    private void mockVerifiedPayment(boolean paid, long amount, long paidAmount) {
        when(paymentVerificationGateway.verify(anyString(), anyString(), anyString()))
                .thenReturn(new InfinitePayPaymentVerification(paid, amount, paidAmount, "pix"));
    }
}
