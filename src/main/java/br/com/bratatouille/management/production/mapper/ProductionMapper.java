package br.com.bratatouille.management.production.mapper;

import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.production.entity.Production;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ProductionMapper {

    public ProductionResponse toResponse(Production production) {
        ProductionResponse response = new ProductionResponse();

        response.setId(production.getId());
        response.setRecipeId(production.getRecipe().getId());
        response.setRecipeName(production.getRecipe().getName());
        response.setOutputItemId(production.getRecipe().getOutputItem().getId());
        response.setOutputItemName(production.getRecipe().getOutputItem().getName());
        response.setProducedQuantity(production.getProducedQuantity());
        response.setTotalCost(production.getTotalCost());
        response.setUnitCost(production.getUnitCost());
        response.setCreatedAt(production.getCreatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }
}