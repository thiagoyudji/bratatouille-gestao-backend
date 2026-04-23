package br.com.bratatouille.management.financial.dto;

import java.math.BigDecimal;

public record PixSettlementResponse(
    Long fromPartnerId,
    String fromPartnerName,
    Long toPartnerId,
    String toPartnerName,
    BigDecimal amount
) {}