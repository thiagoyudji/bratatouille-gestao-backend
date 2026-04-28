package br.com.bratatouille.management.production.mapper;

import br.com.bratatouille.management.generated.model.ProductionItemResponse;
import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.production.entity.Production;
import br.com.bratatouille.management.production.entity.ProductionItem;
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
        response.setItems(
                production.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );
        response.setCreatedAt(production.getCreatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }

    private ProductionItemResponse toItemResponse(ProductionItem item) {
        ProductionItemResponse response = new ProductionItemResponse();

        response.setItemId(item.getItem().getId());
        response.setItemName(item.getItem().getName());
        response.setConsumedQuantity(item.getConsumedQuantity());
        response.setUsableQuantity(item.getUsableQuantity());
        response.setLossQuantity(item.getLossQuantity());
        response.setYieldPercentage(item.getYieldPercentage());
        response.setUnitCost(item.getUnitCost());
        response.setTotalCost(item.getTotalCost());

        return response;
    }
}