package br.com.bratatouille.management.financialClosing.service;

import br.com.bratatouille.management.financial.service.FinancialService;
import br.com.bratatouille.management.financialClosing.entity.FinancialClosing;
import br.com.bratatouille.management.financialClosing.repository.FinancialClosingRepository;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import br.com.bratatouille.management.support.builder.FinancialSummaryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialClosingServiceTest {

    @Mock
    private FinancialService financialService;

    @Mock
    private FinancialClosingRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void closePeriodStoresSnapshotWhenPeriodIsOpen() throws Exception {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);
        FinancialSummaryResponse summary = new FinancialSummaryBuilder()
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withTotalPurchases(new BigDecimal("150.00"))
                .withTotalOperationalCosts(new BigDecimal("35.00"))
                .withTotalSpent(new BigDecimal("185.00"))
                .build();

        when(repository.existsOverlapping(startDate, endDate)).thenReturn(false);
        when(financialService.getFinancialSummaryByPeriod(startDate, endDate)).thenReturn(summary);
        when(repository.save(any(FinancialClosing.class))).thenAnswer(invocation -> {
            FinancialClosing closing = invocation.getArgument(0);
            ReflectionTestUtils.setField(closing, "id", 77L);
            return closing;
        });

        FinancialClosingService service = new FinancialClosingService(financialService, repository, objectMapper);

        Long id = service.closePeriod(startDate, endDate);

        assertEquals(77L, id);
        verify(financialService).getFinancialSummaryByPeriod(startDate, endDate);

        ArgumentCaptor<FinancialClosing> captor = ArgumentCaptor.forClass(FinancialClosing.class);
        verify(repository).save(captor.capture());
        FinancialClosing saved = captor.getValue();
        assertEquals(startDate, saved.getStartDate());
        assertEquals(endDate, saved.getEndDate());
        assertEquals(summary.getStartDate(), objectMapper.readValue(saved.getSummaryJson(), FinancialSummaryResponse.class).getStartDate());
    }

    @Test
    void closePeriodRejectsOverlappingRange() {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);

        when(repository.existsOverlapping(startDate, endDate)).thenReturn(true);

        FinancialClosingService service = new FinancialClosingService(financialService, repository, objectMapper);

        assertThrows(IllegalArgumentException.class, () -> service.closePeriod(startDate, endDate));
        verify(financialService, never()).getFinancialSummaryByPeriod(any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void getClosedSummaryRestoresSnapshotFromJson() throws Exception {
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 31);
        FinancialSummaryResponse summary = new FinancialSummaryBuilder()
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withTotalPurchases(new BigDecimal("150.00"))
                .withTotalOperationalCosts(new BigDecimal("35.00"))
                .withTotalSpent(new BigDecimal("185.00"))
                .build();
        String json = objectMapper.writeValueAsString(summary);
        FinancialClosing closing = new FinancialClosing(startDate, endDate, json);

        when(repository.findById(10L)).thenReturn(Optional.of(closing));

        FinancialClosingService service = new FinancialClosingService(financialService, repository, objectMapper);
        FinancialSummaryResponse restored = service.getClosedSummary(10L);

        assertEquals(startDate, restored.getStartDate());
        assertEquals(endDate, restored.getEndDate());
        assertEquals(new BigDecimal("150.00"), restored.getTotalPurchases());
        assertEquals(new BigDecimal("35.00"), restored.getTotalOperationalCosts());
        assertEquals(new BigDecimal("185.00"), restored.getTotalSpent());
    }

    @Test
    void getClosedSummaryRejectsMissingClosing() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        FinancialClosingService service = new FinancialClosingService(financialService, repository, objectMapper);

        assertThrows(NoSuchElementException.class, () -> service.getClosedSummary(10L));
    }

}
