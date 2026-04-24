package br.com.bratatouille.management.financial.service;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.financial.domain.FinancialSettlementCalculator;
import br.com.bratatouille.management.financial.domain.PartnerBalance;
import br.com.bratatouille.management.financial.domain.PartnerBalanceAccumulator;
import br.com.bratatouille.management.financial.domain.PixTransfer;
import br.com.bratatouille.management.financial.mapper.FinancialMapper;
import br.com.bratatouille.management.generated.model.PartnerBalanceResponse;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseSplit;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialService {

    private final PurchaseRepository purchaseRepository;
    private final FinancialMapper financialMapper;
    private final FinancialSettlementCalculator settlementCalculator;

    public FinancialService(
            PurchaseRepository purchaseRepository,
            FinancialMapper financialMapper,
            FinancialSettlementCalculator settlementCalculator
    ) {
        this.purchaseRepository = purchaseRepository;
        this.financialMapper = financialMapper;
        this.settlementCalculator = settlementCalculator;
    }

    @Transactional(readOnly = true)
    public List<PartnerBalanceResponse> getBalances() {
        return calculateBalances().stream()
                .map(financialMapper::toBalanceResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PixSettlementResponse> getPixSettlement() {
        List<PixTransfer> transfers = settlementCalculator.calculate(calculateBalances());

        return transfers.stream()
                .map(financialMapper::toPixSettlementResponse)
                .toList();
    }

    private List<PartnerBalance> calculateBalances() {
        Map<Long, PartnerBalanceAccumulator> accumulators = new HashMap<>();

        purchaseRepository.findAll().forEach(purchase ->
                applyPurchaseToBalances(accumulators, purchase)
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
}