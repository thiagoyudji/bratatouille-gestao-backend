package br.com.bratatouille.management.financialClosing.service;

import br.com.bratatouille.management.financialClosing.repository.FinancialClosingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialClosingValidationServiceTest {

    @Mock
    private FinancialClosingRepository repository;

    @Test
    void validateNotClosedAllowsOpenPeriod() {
        FinancialClosingValidationService service = new FinancialClosingValidationService(repository);

        assertDoesNotThrow(() -> service.validateNotClosed(LocalDate.of(2026, 8, 15)));
    }

    @Test
    void validateNotClosedRejectsClosedPeriod() {
        when(repository.existsOverlapping(LocalDate.of(2026, 8, 15), LocalDate.of(2026, 8, 15)))
                .thenReturn(true);

        FinancialClosingValidationService service = new FinancialClosingValidationService(repository);

        assertThrows(IllegalStateException.class, () -> service.validateNotClosed(LocalDate.of(2026, 8, 15)));
    }
}
