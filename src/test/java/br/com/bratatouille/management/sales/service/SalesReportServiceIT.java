package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.SalesProductPerformanceResponse;
import br.com.bratatouille.management.generated.model.SalesSummaryResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway.InfinitePayPaymentVerification;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SalesReportServiceIT {

    @Autowired
    private SalesReportService salesReportService;

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SellableStockService sellableStockService;

    @Autowired
    private br.com.bratatouille.management.payment.service.InfinitePayWebhookService infinitePayWebhookService;

    @Autowired
    private ItemRepository itemRepository;

    @MockBean
    private InfinitePayPaymentVerificationGateway paymentVerificationGateway;

    @Test
    void reportUsesActualSoldPriceForPfAndPjOrders() {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);

        Item pizza = itemRepository.save(new Item(
                "Pizza Margherita",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setAvailableQuantity(new BigDecimal("10.000"));
        sellableStockRequest.setInfinite(false);
        sellableStockRequest.setEnabled(true);
        sellableStockService.upsert(pizza.getId(), sellableStockRequest);

        approve(salesOrderService.create(order(startDate.plusDays(1), SalesOrderCustomerType.PF, pizza.getId())));
        approve(salesOrderService.create(order(startDate.plusDays(2), SalesOrderCustomerType.PJ, pizza.getId())));

        SalesSummaryResponse summary = salesReportService.getSummary(startDate, endDate);
        assertEquals(2L, summary.getTotalOrders());
        assertEquals(new BigDecimal("43.40"), summary.getTotalAmount());
        assertEquals(new BigDecimal("43.40"), summary.getGrossProfit());
        assertEquals(new BigDecimal("21.70"), summary.getAverageTicket());

        List<SalesProductPerformanceResponse> performance = salesReportService.getProductPerformance(startDate, endDate);
        assertEquals(1, performance.size());

        SalesProductPerformanceResponse pizzaPerformance = performance.getFirst();
        assertNotNull(pizzaPerformance.getItemId());
        assertEquals(pizza.getId(), pizzaPerformance.getItemId());
        assertEquals(new BigDecimal("2.000"), pizzaPerformance.getSoldQuantity());
        assertEquals(new BigDecimal("43.40"), pizzaPerformance.getTotalAmount());
        assertEquals(new BigDecimal("43.40"), pizzaPerformance.getGrossProfit());
        assertEquals(new BigDecimal("100.00"), pizzaPerformance.getGrossMarginPercentage());
    }

    private SalesOrderCreateRequest order(LocalDate saleDate, SalesOrderCustomerType customerType, Long itemId) {
        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(saleDate);
        request.setCustomerType(customerType);
        request.setCustomerName(customerType.name() + " customer");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(itemId);
        itemRequest.setQuantity(new BigDecimal("1"));

        request.setItems(List.of(itemRequest));
        return request;
    }

    private void approve(br.com.bratatouille.management.generated.model.SalesOrderResponse order) {
        long amount = order.getTotalAmount().movePointRight(2).longValueExact();
        String orderNsu = String.valueOf(order.getId());
        String transactionNsu = "TXN-" + order.getId();
        String invoiceSlug = "invoice-" + order.getId();

        when(paymentVerificationGateway.verify(orderNsu, transactionNsu, invoiceSlug))
                .thenReturn(new InfinitePayPaymentVerification(true, amount, amount, "pix"));

        InfinitePayWebhookRequest webhookRequest = new InfinitePayWebhookRequest();
        webhookRequest.setOrderNsu(orderNsu);
        webhookRequest.setTransactionNsu(transactionNsu);
        webhookRequest.setInvoiceSlug(invoiceSlug);
        webhookRequest.setAmount(amount);
        webhookRequest.setPaidAmount(amount);
        webhookRequest.setReceiptUrl("https://receipt.test/" + order.getId());

        infinitePayWebhookService.handle(webhookRequest);
    }
}
