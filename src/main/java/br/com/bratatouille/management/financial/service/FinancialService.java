package br.com.bratatouille.management.financial.service;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.financial.domain.FinancialSettlementCalculator;
import br.com.bratatouille.management.financial.domain.PartnerBalance;
import br.com.bratatouille.management.financial.domain.PartnerBalanceAccumulator;
import br.com.bratatouille.management.financial.domain.PixTransfer;
import br.com.bratatouille.management.financial.mapper.FinancialMapper;
import br.com.bratatouille.management.generated.model.PartnerBalanceResponse;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
import br.com.bratatouille.management.financial.domain.PartnerFinancialSummaryAccumulator;
import br.com.bratatouille.management.generated.model.FinancialPartnerSummaryResponse;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import br.com.bratatouille.management.operationalCost.entity.OperationalCost;
import br.com.bratatouille.management.operationalCost.entity.OperationalCostSplit;
import br.com.bratatouille.management.operationalCost.repository.OperationalCostRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseSplit;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialService {

    private final OperationalCostRepository operationalCostRepository;
    private final PurchaseRepository purchaseRepository;
    private final FinancialMapper financialMapper;
    private final FinancialSettlementCalculator settlementCalculator;

    public FinancialService(
            PurchaseRepository purchaseRepository,
            OperationalCostRepository operationalCostRepository,
            FinancialMapper financialMapper,
            FinancialSettlementCalculator settlementCalculator
    ) {
        this.purchaseRepository = purchaseRepository;
        this.operationalCostRepository = operationalCostRepository;
        this.financialMapper = financialMapper;
        this.settlementCalculator = settlementCalculator;
    }

    @Transactional(readOnly = true)
    public List<PartnerBalanceResponse> getBalances() {
        return calculateBalances(
                purchaseRepository.findAll(),
                operationalCostRepository.findAll()
        ).stream()
                .map(financialMapper::toBalanceResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PixSettlementResponse> getPixSettlement() {
        List<PixTransfer> transfers = settlementCalculator.calculate(
                calculateBalances(
                        purchaseRepository.findAll(),
                        operationalCostRepository.findAll()
                )
        );

        return transfers.stream()
                .map(financialMapper::toPixSettlementResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PartnerBalanceResponse> getBalancesByPeriod(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        return calculateBalances(
                purchaseRepository.findByPurchaseDateBetween(startDate, endDate),
                operationalCostRepository.findByCostDateBetween(startDate, endDate)
        ).stream()
                .map(financialMapper::toBalanceResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PixSettlementResponse> getPixSettlementByPeriod(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        List<PixTransfer> transfers = settlementCalculator.calculate(
                calculateBalances(
                        purchaseRepository.findByPurchaseDateBetween(startDate, endDate),
                        operationalCostRepository.findByCostDateBetween(startDate, endDate)
                )
        );

        return transfers.stream()
                .map(financialMapper::toPixSettlementResponse)
                .toList();
    }

    private List<PartnerBalance> calculateBalances(
            List<Purchase> purchases,
            List<OperationalCost> operationalCosts
    ) {
        Map<Long, PartnerBalanceAccumulator> accumulators = new HashMap<>();

        purchases.forEach(purchase ->
                applyPurchaseToBalances(accumulators, purchase)
        );

        operationalCosts.forEach(operationalCost ->
                applyOperationalCostToBalances(accumulators, operationalCost)
        );

        return accumulators.values()
                .stream()
                .map(PartnerBalanceAccumulator::toBalance)
                .filter(this::isNotZero)
                .toList();
    }

    private void applyPurchaseToBalances(
            Map<Long, PartnerBalanceAccumulator> accumulators,
            Purchase purchase
    ) {
        getOrCreateAccumulator(accumulators, purchase.getPaidBy())
                .addCredit(purchase.getTotalAmount());

        purchase.getSplits().forEach(split ->
                applySplitToBalances(accumulators, split)
        );
    }

    private void applySplitToBalances(
            Map<Long, PartnerBalanceAccumulator> accumulators,
            PurchaseSplit split
    ) {
        getOrCreateAccumulator(accumulators, split.getPartner())
                .addDebit(split.getOwedAmount());
    }

    private void applyOperationalCostToBalances(
            Map<Long, PartnerBalanceAccumulator> accumulators,
            OperationalCost operationalCost
    ) {
        getOrCreateAccumulator(accumulators, operationalCost.getPaidBy())
                .addCredit(operationalCost.getAmount());

        operationalCost.getSplits().forEach(split ->
                applyOperationalCostSplitToBalances(accumulators, split)
        );
    }

    private void applyOperationalCostSplitToBalances(
            Map<Long, PartnerBalanceAccumulator> accumulators,
            OperationalCostSplit split
    ) {
        getOrCreateAccumulator(accumulators, split.getPartner())
                .addDebit(split.getOwedAmount());
    }

    private PartnerBalanceAccumulator getOrCreateAccumulator(
            Map<Long, PartnerBalanceAccumulator> accumulators,
            Partner partner
    ) {
        return accumulators.computeIfAbsent(
                partner.getId(),
                ignored -> new PartnerBalanceAccumulator(partner)
        );
    }

    private boolean isNotZero(PartnerBalance balance) {
        return !MoneyUtils.equals(balance.amount(), BigDecimal.ZERO);
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

    @Transactional(readOnly = true)
    public FinancialSummaryResponse getFinancialSummaryByPeriod(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        List<Purchase> purchases = purchaseRepository.findByPurchaseDateBetween(startDate, endDate);
        List<OperationalCost> operationalCosts = operationalCostRepository.findByCostDateBetween(startDate, endDate);

        List<PartnerBalance> balances = calculateBalances(purchases, operationalCosts);

        List<PixTransfer> transfers = settlementCalculator.calculate(balances);

        BigDecimal totalPurchases = MoneyUtils.normalize(
                purchases.stream()
                        .map(Purchase::getTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        BigDecimal totalOperationalCosts = MoneyUtils.normalize(
                operationalCosts.stream()
                        .map(OperationalCost::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        FinancialSummaryResponse response = new FinancialSummaryResponse();

        response.setStartDate(startDate);
        response.setEndDate(endDate);
        response.setTotalPurchases(totalPurchases);
        response.setTotalOperationalCosts(totalOperationalCosts);
        response.setTotalSpent(MoneyUtils.normalize(totalPurchases.add(totalOperationalCosts)));

        response.setPartners(
                calculatePartnerSummaries(purchases, operationalCosts)
                        .stream()
                        .map(financialMapper::toPartnerSummaryResponse)
                        .toList()
        );

        response.setPixSettlement(
                transfers.stream()
                        .map(financialMapper::toPixSettlementResponse)
                        .toList()
        );

        return response;
    }

    private List<PartnerFinancialSummaryAccumulator> calculatePartnerSummaries(
            List<Purchase> purchases,
            List<OperationalCost> operationalCosts
    ) {
        Map<Long, PartnerFinancialSummaryAccumulator> accumulators = new HashMap<>();

        purchases.forEach(purchase -> {
            getOrCreateSummaryAccumulator(accumulators, purchase.getPaidBy())
                    .addPaid(purchase.getTotalAmount());

            purchase.getSplits().forEach(split ->
                    getOrCreateSummaryAccumulator(accumulators, split.getPartner())
                            .addOwed(split.getOwedAmount())
            );
        });

        operationalCosts.forEach(operationalCost -> {
            getOrCreateSummaryAccumulator(accumulators, operationalCost.getPaidBy())
                    .addPaid(operationalCost.getAmount());

            operationalCost.getSplits().forEach(split ->
                    getOrCreateSummaryAccumulator(accumulators, split.getPartner())
                            .addOwed(split.getOwedAmount())
            );
        });

        return accumulators.values()
                .stream()
                .toList();
    }

    private PartnerFinancialSummaryAccumulator getOrCreateSummaryAccumulator(
            Map<Long, PartnerFinancialSummaryAccumulator> accumulators,
            Partner partner
    ) {
        return accumulators.computeIfAbsent(
                partner.getId(),
                ignored -> new PartnerFinancialSummaryAccumulator(partner)
        );
    }
}