package br.com.bratatouille.management.financial.service;

import br.com.bratatouille.management.generated.model.CashFlowEntryResponse;
import br.com.bratatouille.management.generated.model.CashFlowSummaryResponse;
import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostSplitRequest;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.operationalCost.repository.OperationalCostRepository;
import br.com.bratatouille.management.operationalCost.service.OperationalCostService;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import br.com.bratatouille.management.purchase.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialServiceIT {

    @Autowired
    private FinancialService financialService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private OperationalCostService operationalCostService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private OperationalCostRepository operationalCostRepository;

    @Test
    void getCashFlowByPeriodReturnsOpeningBalanceEntriesAndClosingBalance() {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);

        Partner payer = savePartner("Cash Flow Partner");
        Item flour = saveItem("Flour");

        seedPurchase(LocalDate.of(2026, 7, 28), payer, flour, new BigDecimal("40.00"));
        seedPurchase(startDate.plusDays(1), payer, flour, new BigDecimal("120.00"));
        seedOperationalCost(startDate.plusDays(1), payer, new BigDecimal("35.00"));

        CashFlowSummaryResponse summary = financialService.getCashFlowByPeriod(startDate, endDate);

        assertEquals(startDate, summary.getStartDate());
        assertEquals(endDate, summary.getEndDate());
        assertEquals(new BigDecimal("-40.00"), summary.getOpeningBalance());
        assertEquals(0, new BigDecimal("0.00").compareTo(summary.getTotalIn()));
        assertEquals(new BigDecimal("155.00"), summary.getTotalOut());
        assertEquals(new BigDecimal("-195.00"), summary.getClosingBalance());

        assertEquals(2, summary.getEntries().size());
        assertEquals(1, summary.getEntries().stream().filter(entry -> entry.getType() == CashFlowEntryResponse.TypeEnum.PURCHASE).count());
        assertEquals(1, summary.getEntries().stream().filter(entry -> entry.getType() == CashFlowEntryResponse.TypeEnum.OPERATIONAL_COST).count());

        CashFlowEntryResponse firstEntry = summary.getEntries().get(0);
        CashFlowEntryResponse secondEntry = summary.getEntries().get(1);

        assertEquals(0, new BigDecimal("-40.00").subtract(firstEntry.getAmount()).compareTo(firstEntry.getBalanceAfter()));
        assertEquals(0, firstEntry.getBalanceAfter().subtract(secondEntry.getAmount()).compareTo(secondEntry.getBalanceAfter()));

        CashFlowEntryResponse purchaseEntry = summary.getEntries().stream()
                .filter(entry -> entry.getType() == CashFlowEntryResponse.TypeEnum.PURCHASE)
                .findFirst()
                .orElseThrow();
        CashFlowEntryResponse costEntry = summary.getEntries().stream()
                .filter(entry -> entry.getType() == CashFlowEntryResponse.TypeEnum.OPERATIONAL_COST)
                .findFirst()
                .orElseThrow();

        assertEquals(new BigDecimal("120.00"), purchaseEntry.getAmount());
        assertEquals(new BigDecimal("35.00"), costEntry.getAmount());
    }

    @Test
    void getCashFlowByPeriodRejectsInvalidPeriod() {
        LocalDate startDate = LocalDate.of(2026, 8, 31);
        LocalDate endDate = LocalDate.of(2026, 8, 1);

        assertThrows(IllegalArgumentException.class, () -> financialService.getCashFlowByPeriod(startDate, endDate));
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

    private Item saveItem(String name) {
        return itemRepository.save(new Item(
                name,
                ItemType.INGREDIENT,
                UnitType.G,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));
    }

    private void seedPurchase(LocalDate purchaseDate, Partner paidBy, Item item, BigDecimal totalValue) {
        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(paidBy.getId());
        request.setPurchaseDate(purchaseDate);
        request.setSupplier("Cash flow supplier");
        request.setNote("cash flow seed");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(item.getId());
        purchaseItem.setQuantity(new BigDecimal("3.000"));
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(totalValue);
        request.setItems(List.of(purchaseItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(paidBy.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        purchaseService.create(request);
    }

    private void seedOperationalCost(LocalDate costDate, Partner paidBy, BigDecimal amount) {
        OperationalCostCreateRequest request = new OperationalCostCreateRequest();
        request.setCostDate(costDate);
        request.setCategory(OperationalCostCreateRequest.CategoryEnum.FIXED);
        request.setPaidByPartnerId(paidBy.getId());
        request.setAmount(amount);
        request.setDescription("cash flow seed");

        OperationalCostSplitRequest split = new OperationalCostSplitRequest();
        split.setPartnerId(paidBy.getId());
        split.setAmount(amount);
        request.setSplits(List.of(split));

        operationalCostService.create(request);
    }
}
