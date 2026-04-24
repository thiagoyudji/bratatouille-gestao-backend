package br.com.bratatouille.management.purchase.dto.response;

import br.com.bratatouille.management.purchase.entity.PurchaseSplit;

import java.math.BigDecimal;

public record PurchaseSplitResponse(
        Long id,
        Long partnerId,
        String partnerName,
        BigDecimal owedAmount
) {
    public static PurchaseSplitResponse from(PurchaseSplit split) {
        return new PurchaseSplitResponse(
                split.getId(),
                split.getPartner().getId(),
                split.getPartner().getName(),
                split.getOwedAmount()
        );
    }
}