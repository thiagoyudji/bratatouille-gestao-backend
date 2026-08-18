package br.com.bratatouille.management.dashboard.service;

import br.com.bratatouille.management.generated.model.CashFlowEntryResponse;
import br.com.bratatouille.management.generated.model.DashboardOverviewResponse;
import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostSplitRequest;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.generated.model.InfinitePayWebhookRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.operationalCost.repository.OperationalCostRepository;
import br.com.bratatouille.management.operationalCost.service.OperationalCostService;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import br.com.bratatouille.management.payment.service.InfinitePayWebhookService;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway;
import br.com.bratatouille.management.payment.gateway.InfinitePayPaymentVerificationGateway.InfinitePayPaymentVerification;
import br.com.bratatouille.management.sales.service.SalesOrderService;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DashboardServiceIT {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private OperationalCostService operationalCostService;

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SellableStockService sellableStockService;

    @Autowired
    private InfinitePayWebhookService infinitePayWebhookService;

    @MockBean
    private InfinitePayPaymentVerificationGateway paymentVerificationGateway;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OperationalCostRepository operationalCostRepository;

    @Test
    void getOverviewAggregatesSalesCostsAlertsAndBalances() {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);

        Partner payer = savePartner("Main Partner");
        Partner splitPartner = savePartner("Partner B");

        Item flour = saveItem("Flour", ItemType.INGREDIENT, new BigDecimal("10.000"), new BigDecimal("5.000"));
        Item pizza = saveItem("Pizza", ItemType.FINISHED_PRODUCT, new BigDecimal("10.000"), new BigDecimal("5.000"));

        seedPurchase(startDate.plusDays(1), payer, splitPartner, flour, new BigDecimal("40.00"), new BigDecimal("4.000"));
        seedOperationalCost(startDate.plusDays(2), payer, splitPartner, OperationalCostCreateRequest.CategoryEnum.FIXED, new BigDecimal("10.00"));
        seedOperationalCost(startDate.plusDays(3), payer, splitPartner, OperationalCostCreateRequest.CategoryEnum.VARIABLE, new BigDecimal("5.00"));
        seedOperationalCost(startDate.plusDays(4), payer, splitPartner, OperationalCostCreateRequest.CategoryEnum.FINANCIAL, new BigDecimal("15.00"));

        upsertSellableStock(pizza.getId(), new BigDecimal("10.000"));
        seedSale(startDate.plusDays(5), pizza, SalesOrderCustomerType.PF, new BigDecimal("1"), new BigDecimal("18.50"));
        seedSale(startDate.plusDays(6), pizza, SalesOrderCustomerType.PJ, new BigDecimal("1"), new BigDecimal("24.90"));

        DashboardOverviewResponse overview = dashboardService.getOverview(startDate, endDate);

        assertEquals(startDate, overview.getStartDate());
        assertEquals(endDate, overview.getEndDate());
        assertEquals(2L, overview.getTotalOrders());
        assertEquals(new BigDecimal("43.40"), overview.getTotalRevenue());
        assertEquals(new BigDecimal("0.00"), overview.getTotalCost());
        assertEquals(new BigDecimal("43.40"), overview.getGrossProfit());
        assertEquals(new BigDecimal("100.00"), overview.getGrossMarginPercentage());
        assertEquals(new BigDecimal("30.00"), overview.getTotalOperationalCost());
        assertEquals(new BigDecimal("10.00"), overview.getFixedOperationalCost());
        assertEquals(new BigDecimal("5.00"), overview.getVariableOperationalCost());
        assertEquals(new BigDecimal("15.00"), overview.getFinancialOperationalCost());
        assertEquals(new BigDecimal("13.40"), overview.getNetProfit());
        assertEquals(new BigDecimal("30.88"), overview.getNetMarginPercentage());
        assertEquals(1L, overview.getCriticalStockAlerts());
        assertEquals(0L, overview.getLowStockAlerts());
        assertEquals(0L, overview.getNearZeroStockAlerts());
        assertEquals(2L, overview.getOpenPartnerBalances());
        assertTrue(Boolean.TRUE.equals(overview.getHasCostIncomplete()));
        assertEquals(2L, overview.getCostIncompleteItems());
        assertTrue(Boolean.FALSE.equals(overview.getMarginReliable()));
        assertNotNull(overview.getMarginWarning());
    }

    @Test
    void getOverviewRejectsInvalidPeriod() {
        LocalDate startDate = LocalDate.of(2026, 8, 31);
        LocalDate endDate = LocalDate.of(2026, 8, 1);

        assertThrows(IllegalArgumentException.class, () -> dashboardService.getOverview(startDate, endDate));
    }

    private Partner savePartner(String name) {
        return partnerRepository.save(new Partner(
                name,
                true,
                new BigDecimal("0.00"),
                LocalDateTime.of(2026, 8, 4, 10, 0),
                Set.of(PartnerRole.ADMIN)
        ));
    }

    private Item saveItem(String name, ItemType type, BigDecimal lowThreshold, BigDecimal criticalThreshold) {
        return itemRepository.save(new Item(
                name,
                type,
                UnitType.G,
                lowThreshold,
                criticalThreshold,
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));
    }

    private void seedPurchase(LocalDate purchaseDate, Partner paidBy, Partner splitPartner, Item item, BigDecimal totalValue, BigDecimal quantity) {
        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(paidBy.getId());
        request.setPurchaseDate(purchaseDate);
        request.setSupplier("Dashboard supplier");
        request.setNote("dashboard seed");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(item.getId());
        purchaseItem.setQuantity(quantity);
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(totalValue);
        request.setItems(List.of(purchaseItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(splitPartner.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        purchaseService.create(request);
    }

    private void seedOperationalCost(LocalDate costDate, Partner paidBy, Partner splitPartner, OperationalCostCreateRequest.CategoryEnum category, BigDecimal amount) {
        OperationalCostCreateRequest request = new OperationalCostCreateRequest();
        request.setCostDate(costDate);
        request.setCategory(category);
        request.setPaidByPartnerId(paidBy.getId());
        request.setAmount(amount);
        request.setDescription("dashboard seed");

        OperationalCostSplitRequest split = new OperationalCostSplitRequest();
        split.setPartnerId(splitPartner.getId());
        split.setAmount(amount);
        request.setSplits(List.of(split));

        operationalCostService.create(request);
    }

    private void upsertSellableStock(Long itemId, BigDecimal ignoredQuantity) {
        SellableStockUpsertRequest request = new SellableStockUpsertRequest();
        request.setInfinite(true);
        request.setActive(true);
        sellableStockService.upsert(itemId, request);
    }

    private void seedSale(LocalDate saleDate, Item item, SalesOrderCustomerType customerType, BigDecimal quantity, BigDecimal unitPrice) {
        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(saleDate);
        request.setCustomerType(customerType);
        request.setCustomerName("Walk-in customer");
        request.setNote("dashboard seed");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(item.getId());
        itemRequest.setQuantity(quantity);
        request.setItems(List.of(itemRequest));

        br.com.bratatouille.management.generated.model.SalesOrderResponse order = salesOrderService.create(request);
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
