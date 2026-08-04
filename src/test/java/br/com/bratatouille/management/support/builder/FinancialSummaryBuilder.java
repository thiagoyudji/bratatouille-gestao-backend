package br.com.bratatouille.management.support.builder;

import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialSummaryBuilder {

    private LocalDate startDate = LocalDate.of(2026, 8, 1);
    private LocalDate endDate = LocalDate.of(2026, 8, 31);
    private BigDecimal totalPurchases = BigDecimal.ZERO;
    private BigDecimal totalOperationalCosts = BigDecimal.ZERO;
    private BigDecimal totalSpent = BigDecimal.ZERO;

    public FinancialSummaryBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public FinancialSummaryBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public FinancialSummaryBuilder withTotalPurchases(BigDecimal totalPurchases) {
        this.totalPurchases = totalPurchases;
        return this;
    }

    public FinancialSummaryBuilder withTotalOperationalCosts(BigDecimal totalOperationalCosts) {
        this.totalOperationalCosts = totalOperationalCosts;
        return this;
    }

    public FinancialSummaryBuilder withTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
        return this;
    }

    public FinancialSummaryResponse build() {
        FinancialSummaryResponse summary = new FinancialSummaryResponse();
        summary.setStartDate(startDate);
        summary.setEndDate(endDate);
        summary.setTotalPurchases(totalPurchases);
        summary.setTotalOperationalCosts(totalOperationalCosts);
        summary.setTotalSpent(totalSpent);
        return summary;
    }
}
