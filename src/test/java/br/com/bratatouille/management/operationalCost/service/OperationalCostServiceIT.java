package br.com.bratatouille.management.operationalCost.service;

import br.com.bratatouille.management.financialClosing.service.FinancialClosingService;
import br.com.bratatouille.management.generated.model.CashFlowEntryResponse;
import br.com.bratatouille.management.generated.model.CashFlowSummaryResponse;
import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostResponse;
import br.com.bratatouille.management.generated.model.OperationalCostSplitRequest;
import br.com.bratatouille.management.financial.service.FinancialService;
import br.com.bratatouille.management.operationalCost.repository.OperationalCostRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OperationalCostServiceIT {

    @Autowired
    private OperationalCostService operationalCostService;

    @Autowired
    private OperationalCostRepository operationalCostRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private FinancialClosingService financialClosingService;

    @Test
    void createOperationalCostPersistsSplitsAndImpactsCashFlow() {
        Partner payer = savePartner("Operational Cost Payer");
        Partner splitPartner = savePartner("Operational Cost Split");

        OperationalCostCreateRequest request = new OperationalCostCreateRequest();
        request.setCostDate(LocalDate.of(2026, 8, 10));
        request.setCategory(OperationalCostCreateRequest.CategoryEnum.FIXED);
        request.setPaidByPartnerId(payer.getId());
        request.setAmount(new BigDecimal("145.00"));
        request.setDescription("rent");

        OperationalCostSplitRequest split = new OperationalCostSplitRequest();
        split.setPartnerId(splitPartner.getId());
        split.setAmount(new BigDecimal("145.00"));
        request.setSplits(List.of(split));

        OperationalCostResponse response = operationalCostService.create(request);

        assertNotNull(response.getId());
        assertEquals(LocalDate.of(2026, 8, 10), response.getCostDate());
        assertEquals(new BigDecimal("145.00"), response.getAmount());
        assertEquals(1, response.getSplits().size());
        assertEquals(splitPartner.getId(), response.getSplits().get(0).getPartnerId());
        assertEquals(new BigDecimal("145.00"), response.getSplits().get(0).getOwedAmount());
        assertEquals(1L, operationalCostRepository.count());

        CashFlowSummaryResponse summary = financialService.getCashFlowByPeriod(
                LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 8, 31)
        );

        assertEquals(new BigDecimal("145.00"), summary.getTotalOut());
        assertEquals(1, summary.getEntries().stream()
                .filter(entry -> entry.getType() == CashFlowEntryResponse.TypeEnum.OPERATIONAL_COST)
                .count());

        CashFlowEntryResponse entry = summary.getEntries().stream()
                .filter(value -> value.getType() == CashFlowEntryResponse.TypeEnum.OPERATIONAL_COST)
                .findFirst()
                .orElseThrow();

        assertEquals(response.getId(), entry.getSourceId());
        assertEquals(new BigDecimal("145.00"), entry.getAmount());
    }

    @Test
    void createOperationalCostRejectsClosedPeriod() {
        LocalDate closedDate = LocalDate.of(2026, 8, 10);
        financialClosingService.closePeriod(closedDate, closedDate);

        Partner payer = savePartner("Closed Payer");
        Partner splitPartner = savePartner("Closed Split");

        OperationalCostCreateRequest request = new OperationalCostCreateRequest();
        request.setCostDate(closedDate);
        request.setCategory(OperationalCostCreateRequest.CategoryEnum.FIXED);
        request.setPaidByPartnerId(payer.getId());
        request.setAmount(new BigDecimal("50.00"));
        request.setDescription("blocked cost");

        OperationalCostSplitRequest split = new OperationalCostSplitRequest();
        split.setPartnerId(splitPartner.getId());
        split.setAmount(new BigDecimal("50.00"));
        request.setSplits(List.of(split));

        assertThrows(IllegalStateException.class, () -> operationalCostService.create(request));
        assertEquals(0L, operationalCostRepository.count());
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
}
