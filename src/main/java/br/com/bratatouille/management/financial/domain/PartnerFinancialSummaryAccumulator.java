package br.com.bratatouille.management.financial.domain;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;

public class PartnerFinancialSummaryAccumulator {

    private final Partner partner;
    private BigDecimal totalPaid;
    private BigDecimal totalOwed;

    public PartnerFinancialSummaryAccumulator(Partner partner) {
        if (partner == null) {
            throw new IllegalArgumentException("partner is required");
        }

        this.partner = partner;
        this.totalPaid = BigDecimal.ZERO;
        this.totalOwed = BigDecimal.ZERO;
    }

    public void addPaid(BigDecimal value) {
        this.totalPaid = MoneyUtils.normalize(this.totalPaid.add(value));
    }

    public void addOwed(BigDecimal value) {
        this.totalOwed = MoneyUtils.normalize(this.totalOwed.add(value));
    }

    public Partner getPartner() {
        return partner;
    }

    public BigDecimal getTotalPaid() {
        return MoneyUtils.normalize(totalPaid);
    }

    public BigDecimal getTotalOwed() {
        return MoneyUtils.normalize(totalOwed);
    }

    public BigDecimal getBalance() {
        return MoneyUtils.normalize(totalPaid.subtract(totalOwed));
    }
}