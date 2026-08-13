package br.com.bratatouille.management.financialClosing.service;

import br.com.bratatouille.management.financialClosing.entity.FinancialClosing;
import br.com.bratatouille.management.financialClosing.repository.FinancialClosingRepository;
import br.com.bratatouille.management.generated.model.FinancialPartnerSummaryResponse;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostSplitRequest;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialClosingServiceIT {

    @Autowired
    private FinancialClosingService financialClosingService;

    @Autowired
    private FinancialClosingRepository financialClosingRepository;

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
    void closePeriodSnapshotsFinancialSummaryAndRestoresIt() {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);

        Partner creditor = savePartner("Creditor Partner");
        Partner debtor = savePartner("Debtor Partner");
        Item flour = saveItem("Flour");

        seedPurchase(startDate.plusDays(3), creditor, debtor, flour, new BigDecimal("120.00"));
        seedOperationalCost(startDate.plusDays(10), creditor, debtor, new BigDecimal("35.00"));

        assertEquals(1L, purchaseRepository.count());
        assertEquals(1L, operationalCostRepository.count());

        Long closingId = financialClosingService.closePeriod(startDate, endDate);

        assertNotNull(closingId);
        assertEquals(1L, financialClosingRepository.count());

        FinancialClosing closing = financialClosingRepository.findById(closingId).orElseThrow();
        assertEquals(startDate, closing.getStartDate());
        assertEquals(endDate, closing.getEndDate());

        FinancialSummaryResponse summary = financialClosingService.getClosedSummary(closingId);

        assertEquals(startDate, summary.getStartDate());
        assertEquals(endDate, summary.getEndDate());
        assertEquals(new BigDecimal("120.00"), summary.getTotalPurchases());
        assertEquals(new BigDecimal("35.00"), summary.getTotalOperationalCosts());
        assertEquals(new BigDecimal("155.00"), summary.getTotalSpent());

        Map<Long, FinancialPartnerSummaryResponse> partnerSummaries = summary.getPartners().stream()
                .collect(Collectors.toMap(FinancialPartnerSummaryResponse::getPartnerId, it -> it));

        assertEquals(2, partnerSummaries.size());
        assertEquals(new BigDecimal("155.00"), partnerSummaries.get(creditor.getId()).getTotalPaid());
        assertEquals(new BigDecimal("0.00"), partnerSummaries.get(creditor.getId()).getTotalOwed());
        assertEquals(new BigDecimal("155.00"), partnerSummaries.get(creditor.getId()).getBalance());
        assertEquals(new BigDecimal("0.00"), partnerSummaries.get(debtor.getId()).getTotalPaid());
        assertEquals(new BigDecimal("155.00"), partnerSummaries.get(debtor.getId()).getTotalOwed());
        assertEquals(new BigDecimal("-155.00"), partnerSummaries.get(debtor.getId()).getBalance());

        List<PixSettlementResponse> settlements = summary.getPixSettlement();
        assertEquals(1, settlements.size());
        assertEquals(debtor.getId(), settlements.get(0).getFromPartnerId());
        assertEquals(creditor.getId(), settlements.get(0).getToPartnerId());
        assertEquals(new BigDecimal("155.00"), settlements.get(0).getAmount());
    }

    @Test
    void closePeriodRejectsOverlappingClosing() {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);

        Partner creditor = savePartner("Creditor Partner");
        Partner debtor = savePartner("Debtor Partner");
        Item flour = saveItem("Flour");

        seedPurchase(startDate.plusDays(3), creditor, debtor, flour, new BigDecimal("120.00"));
        seedOperationalCost(startDate.plusDays(10), creditor, debtor, new BigDecimal("35.00"));

        financialClosingService.closePeriod(startDate, endDate);

        LocalDate overlappingStart = startDate.plusDays(10);
        LocalDate overlappingEnd = endDate.plusDays(10);

        assertThrows(
                IllegalArgumentException.class,
                () -> financialClosingService.closePeriod(overlappingStart, overlappingEnd)
        );
        assertEquals(1L, financialClosingRepository.count());
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

    private void seedPurchase(LocalDate purchaseDate, Partner paidBy, Partner splitPartner, Item item, BigDecimal totalValue) {
        PurchaseCreateRequest request = new PurchaseCreateRequest();
        request.setPaidByPartnerId(paidBy.getId());
        request.setPurchaseDate(purchaseDate);
        request.setSupplier("Seed supplier");
        request.setNote("financial closing seed");

        PurchaseItemRequest purchaseItem = new PurchaseItemRequest();
        purchaseItem.setItemId(item.getId());
        purchaseItem.setQuantity(new BigDecimal("3.000"));
        purchaseItem.setUnit(PurchaseItemRequest.UnitEnum.G);
        purchaseItem.setTotalValue(totalValue);
        request.setItems(List.of(purchaseItem));

        PurchaseSplitRequest split = new PurchaseSplitRequest();
        split.setPartnerId(splitPartner.getId());
        split.setPercentage(new BigDecimal("100.00"));
        request.setSplits(List.of(split));

        purchaseService.create(request);
    }

    private void seedOperationalCost(LocalDate costDate, Partner paidBy, Partner splitPartner, BigDecimal amount) {
        OperationalCostCreateRequest request = new OperationalCostCreateRequest();
        request.setCostDate(costDate);
        request.setCategory(OperationalCostCreateRequest.CategoryEnum.FIXED);
        request.setPaidByPartnerId(paidBy.getId());
        request.setAmount(amount);
        request.setDescription("financial closing seed");

        OperationalCostSplitRequest split = new OperationalCostSplitRequest();
        split.setPartnerId(splitPartner.getId());
        split.setAmount(amount);
        request.setSplits(List.of(split));

        operationalCostService.create(request);
    }
}
