package br.com.bratatouille.management.item.mapper;

import br.com.bratatouille.management.generated.model.CreateItemRequest;
import br.com.bratatouille.management.generated.model.ItemResponse;
import br.com.bratatouille.management.common.mapper.ApiResponseMapperSupport;
import br.com.bratatouille.management.item.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemResponse toResponse(Item item) {
        ItemResponse response = new ItemResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setType(ItemResponse.TypeEnum.valueOf(item.getType().name()));
        response.setBaseUnit(ItemResponse.BaseUnitEnum.valueOf(item.getBaseUnit().name()));
        response.setActive(item.isActive());
        response.setLowStockThreshold(item.getLowStockThreshold());
        response.setCriticalStockThreshold(item.getCriticalStockThreshold());
        response.setPricePf(item.getPricePf());
        response.setPricePj(item.getPricePj());

        response.setCreatedAt(ApiResponseMapperSupport.toUtc(item.getCreatedAt()));
        response.setUpdatedAt(ApiResponseMapperSupport.toUtc(item.getUpdatedAt()));

        return response;
    }

}
