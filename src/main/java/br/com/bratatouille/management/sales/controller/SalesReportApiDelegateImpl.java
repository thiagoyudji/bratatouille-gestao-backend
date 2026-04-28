package br.com.bratatouille.management.sales.controller;

import br.com.bratatouille.management.generated.api.SalesReportsApiDelegate;
import br.com.bratatouille.management.generated.model.SalesProductPerformanceResponse;
import br.com.bratatouille.management.generated.model.SalesSummaryResponse;
import br.com.bratatouille.management.sales.service.SalesReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SalesReportApiDelegateImpl implements SalesReportsApiDelegate {

    private final SalesReportService salesReportService;

    public SalesReportApiDelegateImpl(SalesReportService salesReportService) {
        this.salesReportService = salesReportService;
    }

    @Override
    public ResponseEntity<SalesSummaryResponse> getSalesSummary(LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok(salesReportService.getSummary(startDate, endDate));
    }

    @Override
    public ResponseEntity<List<SalesProductPerformanceResponse>> getSalesProductPerformance(LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok(salesReportService.getProductPerformance(startDate, endDate));
    }
}