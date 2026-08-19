package br.com.bratatouille.management.stock.entry.mapper;

import br.com.bratatouille.management.generated.model.ZeroCostEntryResponse;
import br.com.bratatouille.management.stock.entry.entity.ZeroCostEntry;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ZeroCostEntryMapper {

    public ZeroCostEntryResponse toResponse(ZeroCostEntry entry) {
        ZeroCostEntryResponse response = new ZeroCostEntryResponse();

        response.setId(entry.getId());
        response.setItemId(entry.getItem().getId());
        response.setItemName(entry.getItem().getName());
        response.setQuantity(entry.getQuantity());
        response.setReason(ZeroCostEntryResponse.ReasonEnum.valueOf(entry.getReason().name()));
        response.setNote(entry.getNote());

        if (entry.getCreatedAt() != null) {
            response.setCreatedAt(entry.getCreatedAt().atOffset(ZoneOffset.UTC));
        }

        return response;
    }
}