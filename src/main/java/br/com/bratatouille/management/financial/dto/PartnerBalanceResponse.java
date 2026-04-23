package br.com.bratatouille.management.financial.dto;

import java.math.BigDecimal;

public record PartnerBalanceResponse(
    Long partnerId,
    String partnerName,
    BigDecimal balance
) {}