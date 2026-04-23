package br.com.bratatouille.management.purchase.dto;

import java.math.BigDecimal;

public record PurchaseSplitRequest(
        Long partnerId,
        BigDecimal amount
) {
}