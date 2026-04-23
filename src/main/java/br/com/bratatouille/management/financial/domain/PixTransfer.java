package br.com.bratatouille.management.financial.domain;

import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;

public record PixTransfer(
        Partner from,
        Partner to,
        BigDecimal amount
) {
}