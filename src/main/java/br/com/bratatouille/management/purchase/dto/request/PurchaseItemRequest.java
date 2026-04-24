package br.com.bratatouille.management.purchase.dto.request;

import java.math.BigDecimal;

public record PurchaseItemRequest(
        Long itemId,
        BigDecimal quantity,
        String unit,
        BigDecimal totalValue
) {
}