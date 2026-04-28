package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.generated.model.SalesProductPerformanceResponse;
import br.com.bratatouille.management.generated.model.SalesSummaryResponse;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalesReportService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesReportService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public SalesSummaryResponse getSummary(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        BigDecimal totalAmount = MoneyUtils.normalize(salesOrderRepository.sumTotalAmountBetween(startDate, endDate));
        BigDecimal totalCost = MoneyUtils.normalize(salesOrderRepository.sumTotalCostBetween(startDate, endDate));
        BigDecimal grossProfit = MoneyUtils.normalize(salesOrderRepository.sumGrossProfitBetween(startDate, endDate));
        Long totalOrders = salesOrderRepository.countOrdersBetween(startDate, endDate);

        BigDecimal averageTicket = totalOrders == 0
                ? BigDecimal.ZERO
                : totalAmount.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP);

        BigDecimal grossMarginPercentage = totalAmount.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : grossProfit.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 2, RoundingMode.HALF_UP);

        SalesSummaryResponse response = new SalesSummaryResponse();

        response.setStartDate(startDate);
        response.setEndDate(endDate);
        response.setTotalOrders(totalOrders);
        response.setTotalAmount(totalAmount);
        response.setTotalCost(totalCost);
        response.setGrossProfit(grossProfit);
        response.setAverageTicket(averageTicket);
        response.setGrossMarginPercentage(grossMarginPercentage);

        return response;
    }

    public List<SalesProductPerformanceResponse> getProductPerformance(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        return salesOrderRepository.findProductPerformanceBetween(startDate, endDate)
                .stream()
                .map(this::toProductPerformanceResponse)
                .toList();
    }

    private SalesProductPerformanceResponse toProductPerformanceResponse(Object[] row) {
        BigDecimal totalAmount = MoneyUtils.normalize((BigDecimal) row[3]);
        BigDecimal totalCost = MoneyUtils.normalize((BigDecimal) row[4]);
        BigDecimal grossProfit = MoneyUtils.normalize((BigDecimal) row[5]);

        BigDecimal grossMarginPercentage = totalAmount.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : grossProfit.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 2, RoundingMode.HALF_UP);

        SalesProductPerformanceResponse response = new SalesProductPerformanceResponse();

        response.setItemId((Long) row[0]);
        response.setItemName((String) row[1]);
        response.setSoldQuantity((BigDecimal) row[2]);
        response.setTotalAmount(totalAmount);
        response.setTotalCost(totalCost);
        response.setGrossProfit(grossProfit);
        response.setGrossMarginPercentage(grossMarginPercentage);

        return response;
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