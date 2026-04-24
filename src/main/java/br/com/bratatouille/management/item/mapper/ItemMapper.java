package br.com.bratatouille.management.item.mapper;

import br.com.bratatouille.management.generated.model.ItemResponse;
import br.com.bratatouille.management.item.entity.Item;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ItemMapper {

    public ItemResponse toResponse(Item item) {
        ItemResponse response = new ItemResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setType(ItemResponse.TypeEnum.valueOf(item.getType().name()));
        response.setBaseUnit(ItemResponse.BaseUnitEnum.valueOf(item.getBaseUnit().name()));
        response.setActive(item.isActive());

        response.setCreatedAt(item.getCreatedAt().atOffset(ZoneOffset.UTC));
        response.setUpdatedAt(item.getUpdatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }
}