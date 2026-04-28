package br.com.bratatouille.management.financialClosing.service;

import br.com.bratatouille.management.financial.service.FinancialService;
import br.com.bratatouille.management.financialClosing.entity.FinancialClosing;
import br.com.bratatouille.management.financialClosing.repository.FinancialClosingRepository;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class FinancialClosingService {

    private final FinancialService financialService;
    private final FinancialClosingRepository repository;
    private final ObjectMapper objectMapper;

    public FinancialClosingService(
            FinancialService financialService,
            FinancialClosingRepository repository,
            ObjectMapper objectMapper
    ) {
        this.financialService = financialService;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Long closePeriod(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        if (repository.existsOverlapping(startDate, endDate)) {
            throw new IllegalArgumentException("period already closed or overlapping");
        }

        FinancialSummaryResponse summary =
                financialService.getFinancialSummaryByPeriod(startDate, endDate);

        String json = toJson(summary);

        FinancialClosing closing = new FinancialClosing(startDate, endDate, json);

        return repository.save(closing).getId();
    }

    @Transactional(readOnly = true)
    public FinancialSummaryResponse getClosedSummary(Long id) {
        FinancialClosing closing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("closing not found"));

        return fromJson(closing.getSummaryJson());
    }

    private String toJson(FinancialSummaryResponse summary) {
        try {
            return objectMapper.writeValueAsString(summary);
        } catch (Exception e) {
            throw new RuntimeException("serialization error", e);
        }
    }

    private FinancialSummaryResponse fromJson(String json) {
        try {
            return objectMapper.readValue(json, FinancialSummaryResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("deserialization error", e);
        }
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate is required");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("endDate is required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate cannot be after endDate");
        }
    }
}