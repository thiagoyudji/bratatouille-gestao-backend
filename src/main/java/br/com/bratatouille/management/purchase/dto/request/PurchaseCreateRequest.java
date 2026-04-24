package br.com.bratatouille.management.purchase.dto.request;

import java.time.LocalDate;
import java.util.List;

public record PurchaseCreateRequest(
        Long paidByPartnerId,
        LocalDate purchaseDate,
        String note,
        List<PurchaseItemRequest> items,
        List<PurchaseSplitRequest> splits
) {
}