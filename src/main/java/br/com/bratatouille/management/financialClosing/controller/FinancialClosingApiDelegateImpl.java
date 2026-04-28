package br.com.bratatouille.management.financialClosing.controller;

import br.com.bratatouille.management.financialClosing.service.FinancialClosingService;
import br.com.bratatouille.management.generated.api.FinancialClosingApiDelegate;
import br.com.bratatouille.management.generated.model.FinancialClosingResponse;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FinancialClosingApiDelegateImpl implements FinancialClosingApiDelegate {

    private final FinancialClosingService service;

    public FinancialClosingApiDelegateImpl(FinancialClosingService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<FinancialClosingResponse> closeFinancialPeriod(LocalDate startDate, LocalDate endDate) {
        Long id = service.closePeriod(startDate, endDate);

        FinancialClosingResponse response = new FinancialClosingResponse();
        response.setId(id);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FinancialSummaryResponse> getFinancialClosing(Long id) {
        return ResponseEntity.ok(service.getClosedSummary(id));
    }
}