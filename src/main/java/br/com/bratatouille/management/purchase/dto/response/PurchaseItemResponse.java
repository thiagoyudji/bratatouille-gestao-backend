package br.com.bratatouille.management.purchase.dto.response;

import br.com.bratatouille.management.purchase.entity.PurchaseItem;

import java.math.BigDecimal;

public record PurchaseItemResponse(
        Long id,
        Long itemId,
        String itemName,
        BigDecimal quantity,
        String unit,
        BigDecimal totalValue
) {
    public static PurchaseItemResponse from(PurchaseItem item) {
        return new PurchaseItemResponse(
                item.getId(),
                item.getItem().getId(),
                item.getItem().getName(),
                item.getQuantity(),
                item.getUnit(),
                item.getTotalValue()
        );
    }
}