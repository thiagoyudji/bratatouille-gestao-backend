package br.com.bratatouille.management.purchase.mapper;

import br.com.bratatouille.management.generated.model.PurchaseItemResponse;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
import br.com.bratatouille.management.generated.model.PurchaseSplitResponse;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseItem;
import br.com.bratatouille.management.purchase.entity.PurchaseSplit;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class PurchaseMapper {

    public PurchaseResponse toResponse(Purchase purchase) {
        PurchaseResponse response = new PurchaseResponse();

        response.setId(purchase.getId());
        response.setPurchaseDate(purchase.getPurchaseDate());
        response.setPaidByPartnerId(purchase.getPaidBy().getId());
        response.setPaidByPartnerName(purchase.getPaidBy().getName());
        response.setTotalAmount(purchase.getTotalAmount());
        response.setNote(purchase.getNote());
        response.setCreatedAt(purchase.getCreatedAt().atOffset(ZoneOffset.UTC));

        response.setItems(
                purchase.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );

        response.setSplits(
                purchase.getSplits()
                        .stream()
                        .map(this::toSplitResponse)
                        .toList()
        );

        return response;
    }

    public PurchaseItemResponse toItemResponse(PurchaseItem item) {
        PurchaseItemResponse response = new PurchaseItemResponse();

        response.setId(item.getId());
        response.setItemId(item.getItem().getId());
        response.setItemName(item.getItem().getName());
        response.setQuantity(item.getQuantity());
        response.setUnit(item.getUnit());
        response.setTotalValue(item.getTotalValue());

        return response;
    }

    public PurchaseSplitResponse toSplitResponse(PurchaseSplit split) {
        PurchaseSplitResponse response = new PurchaseSplitResponse();

        response.setId(split.getId());
        response.setPartnerId(split.getPartner().getId());
        response.setPartnerName(split.getPartner().getName());
        response.setOwedAmount(split.getOwedAmount());

        return response;
    }
}