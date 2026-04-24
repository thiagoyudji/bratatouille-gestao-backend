package br.com.bratatouille.management.purchase.dto.response;

import br.com.bratatouille.management.purchase.entity.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PurchaseResponse(
        Long id,
        LocalDate purchaseDate,
        String note,
        Long paidByPartnerId,
        String paidByPartnerName,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<PurchaseItemResponse> items,
        List<PurchaseSplitResponse> splits
) {
    public static PurchaseResponse from(Purchase purchase) {
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getPurchaseDate(),
                purchase.getNote(),
                purchase.getPaidBy().getId(),
                purchase.getPaidBy().getName(),
                purchase.getTotalAmount(),
                purchase.getCreatedAt(),
                purchase.getItems()
                        .stream()
                        .map(PurchaseItemResponse::from)
                        .toList(),
                purchase.getSplits()
                        .stream()
                        .map(PurchaseSplitResponse::from)
                        .toList()
        );
    }
}