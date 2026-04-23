package br.com.bratatouille.management.financial.domain;

import br.com.bratatouille.management.common.util.MoneyUtils;

import br.com.bratatouille.management.partner.entity.Partner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class FinancialSettlementCalculator {

    public List<PixTransfer> calculate(List<PartnerBalance> balances) {
        List<MutableBalance> creditors = balances.stream()
                .filter(PartnerBalance::isCreditor)
                .map(balance -> new MutableBalance(balance.partner(), MoneyUtils.normalize(balance.amount())))
                .sorted(Comparator.comparing(MutableBalance::amount).reversed())
                .toList();

        List<MutableBalance> debtors = balances.stream()
                .filter(PartnerBalance::isDebtor)
                .map(balance -> new MutableBalance(balance.partner(), MoneyUtils.normalize(balance.absoluteAmount())))
                .sorted(Comparator.comparing(MutableBalance::amount).reversed())
                .toList();

        Queue<MutableBalance> creditorQueue = new LinkedList<>(creditors);
        Queue<MutableBalance> debtorQueue = new LinkedList<>(debtors);

        List<PixTransfer> transfers = new ArrayList<>();

        while (!creditorQueue.isEmpty() && !debtorQueue.isEmpty()) {
            MutableBalance creditor = creditorQueue.poll();
            MutableBalance debtor = debtorQueue.poll();

            BigDecimal amount = creditor.amount().min(debtor.amount());
            amount = MoneyUtils.normalize(amount);

            transfers.add(new PixTransfer(debtor.partner(), creditor.partner(), amount));

            BigDecimal creditorRemaining = MoneyUtils.normalize(creditor.amount().subtract(amount));
            BigDecimal debtorRemaining = MoneyUtils.normalize(debtor.amount().subtract(amount));

            if (creditorRemaining.compareTo(BigDecimal.ZERO) > 0) {
                creditorQueue.add(new MutableBalance(creditor.partner(), creditorRemaining));
            }

            if (debtorRemaining.compareTo(BigDecimal.ZERO) > 0) {
                debtorQueue.add(new MutableBalance(debtor.partner(), debtorRemaining));
            }
        }

        return transfers;
    }
}