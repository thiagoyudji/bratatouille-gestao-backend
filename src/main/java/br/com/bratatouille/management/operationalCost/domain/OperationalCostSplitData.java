package br.com.bratatouille.management.operationalCost.domain;

import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;

public record OperationalCostSplitData(
        Partner partner,
        BigDecimal amount
) {
}