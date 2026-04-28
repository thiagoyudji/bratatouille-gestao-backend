package br.com.bratatouille.management.lot.mapper;

import br.com.bratatouille.management.generated.model.LotResponse;
import br.com.bratatouille.management.lot.entity.Lot;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class LotMapper {

    public LotResponse toResponse(Lot lot) {
        LotResponse response = new LotResponse();

        response.setId(lot.getId());
        response.setProductionId(lot.getProduction().getId());
        response.setItemId(lot.getItem().getId());
        response.setItemName(lot.getItem().getName());
        response.setProductionDate(lot.getProductionDate());
        response.setExpirationDate(lot.getExpirationDate());
        response.setQuantity(lot.getQuantity());
        response.setCreatedAt(lot.getCreatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }
}