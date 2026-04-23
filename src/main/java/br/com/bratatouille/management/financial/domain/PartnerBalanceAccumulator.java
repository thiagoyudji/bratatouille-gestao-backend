package br.com.bratatouille.management.financial.domain;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;

public class PartnerBalanceAccumulator {

    private final Partner partner;
    private BigDecimal amount;

    public PartnerBalanceAccumulator(Partner partner) {
        if (partner == null) {
            throw new IllegalArgumentException("partner is required");
        }

        this.partner = partner;
        this.amount = BigDecimal.ZERO;
    }

    public void addCredit(BigDecimal value) {
        this.amount = MoneyUtils.normalize(this.amount.add(value));
    }

    public void addDebit(BigDecimal value) {
        this.amount = MoneyUtils.normalize(this.amount.subtract(value));
    }

    public PartnerBalance toBalance() {
        return new PartnerBalance(partner, MoneyUtils.normalize(amount));
    }
}