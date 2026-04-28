package br.com.bratatouille.management.dashboard.service;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.financial.service.FinancialService;
import br.com.bratatouille.management.generated.model.DashboardOverviewResponse;
import br.com.bratatouille.management.generated.model.SalesSummaryResponse;
import br.com.bratatouille.management.generated.model.StockAlertResponse;
import br.com.bratatouille.management.operationalCost.entity.OperationalCostCategory;
import br.com.bratatouille.management.operationalCost.repository.OperationalCostRepository;
import br.com.bratatouille.management.sales.service.SalesReportService;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final SalesReportService salesReportService;
    private final StockService stockService;
    private final FinancialService financialService;
    private final OperationalCostRepository operationalCostRepository;

    public DashboardService(
            SalesReportService salesReportService,
            StockService stockService,
            FinancialService financialService,
            OperationalCostRepository operationalCostRepository
    ) {
        this.salesReportService = salesReportService;
        this.stockService = stockService;
        this.financialService = financialService;
        this.operationalCostRepository = operationalCostRepository;
    }

    @Transactional(readOnly = true)
    public DashboardOverviewResponse getOverview(LocalDate startDate, LocalDate endDate) {
        SalesSummaryResponse salesSummary = salesReportService.getSummary(startDate, endDate);
        List<StockAlertResponse> stockAlerts = stockService.findAlerts();

        BigDecimal totalOperationalCost = MoneyUtils.normalize(
                operationalCostRepository.sumAmountBetween(startDate, endDate)
        );

        BigDecimal fixedOperationalCost = MoneyUtils.normalize(
                operationalCostRepository.sumAmountByCategoryBetween(
                        OperationalCostCategory.FIXED,
                        startDate,
                        endDate
                )
        );

        BigDecimal variableOperationalCost = MoneyUtils.normalize(
                operationalCostRepository.sumAmountByCategoryBetween(
                        OperationalCostCategory.VARIABLE,
                        startDate,
                        endDate
                )
        );

        BigDecimal financialOperationalCost = MoneyUtils.normalize(
                operationalCostRepository.sumAmountByCategoryBetween(
                        OperationalCostCategory.FINANCIAL,
                        startDate,
                        endDate
                )
        );

        BigDecimal grossProfit = salesSummary.getGrossProfit() == null
                ? BigDecimal.ZERO
                : salesSummary.getGrossProfit();

        BigDecimal totalRevenue = salesSummary.getTotalAmount() == null
                ? BigDecimal.ZERO
                : salesSummary.getTotalAmount();

        BigDecimal netProfit = MoneyUtils.normalize(
                grossProfit.subtract(totalOperationalCost)
        );

        BigDecimal netMarginPercentage = calculateMarginPercentage(
                netProfit,
                totalRevenue
        );

        DashboardOverviewResponse response = new DashboardOverviewResponse();

        response.setStartDate(startDate);
        response.setEndDate(endDate);

        response.setTotalOrders(salesSummary.getTotalOrders());
        response.setTotalRevenue(salesSummary.getTotalAmount());
        response.setTotalCost(salesSummary.getTotalCost());
        response.setGrossProfit(salesSummary.getGrossProfit());
        response.setGrossMarginPercentage(salesSummary.getGrossMarginPercentage());

        response.setTotalOperationalCost(totalOperationalCost);
        response.setFixedOperationalCost(fixedOperationalCost);
        response.setVariableOperationalCost(variableOperationalCost);
        response.setFinancialOperationalCost(financialOperationalCost);

        response.setNetProfit(netProfit);
        response.setNetMarginPercentage(netMarginPercentage);

        response.setCriticalStockAlerts(countAlerts(stockAlerts, StockAlertResponse.StatusEnum.CRITICAL));
        response.setLowStockAlerts(countAlerts(stockAlerts, StockAlertResponse.StatusEnum.LOW));
        response.setNearZeroStockAlerts(countAlerts(stockAlerts, StockAlertResponse.StatusEnum.NEAR_ZERO));

        response.setOpenPartnerBalances((long) financialService.getBalances().size());

        return response;
    }

    private BigDecimal calculateMarginPercentage(BigDecimal profit, BigDecimal revenue) {
        if (revenue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return profit.multiply(BigDecimal.valueOf(100))
                .divide(revenue, 2, RoundingMode.HALF_UP);
    }

    private Long countAlerts(List<StockAlertResponse> alerts, StockAlertResponse.StatusEnum status) {
        return alerts.stream()
                .filter(alert -> alert.getStatus() == status)
                .count();
    }
}