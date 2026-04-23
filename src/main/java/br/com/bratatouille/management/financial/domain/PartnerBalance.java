package br.com.bratatouille.management.financial.domain;

import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;

public record PartnerBalance(
        Partner partner,
        BigDecimal amount
) {
    public boolean isCreditor() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isDebtor() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public BigDecimal absoluteAmount() {
        return amount.abs();
    }
}