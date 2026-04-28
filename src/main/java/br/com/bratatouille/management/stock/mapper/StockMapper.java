package br.com.bratatouille.management.stock.mapper;

import br.com.bratatouille.management.generated.model.StockMovementResponse;
import br.com.bratatouille.management.generated.model.StockResponse;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.entity.StockMovement;
import org.springframework.stereotype.Component;
import br.com.bratatouille.management.generated.model.StockAlertResponse;
import br.com.bratatouille.management.stock.domain.StockAlertData;

import java.time.ZoneOffset;

@Component
public class StockMapper {

    public StockResponse toResponse(Stock stock) {
        StockResponse response = new StockResponse();

        response.setId(stock.getId());
        response.setItemId(stock.getItem().getId());
        response.setItemName(stock.getItem().getName());
        response.setItemType(StockResponse.ItemTypeEnum.valueOf(stock.getItem().getType().name()));
        response.setBaseUnit(StockResponse.BaseUnitEnum.valueOf(stock.getItem().getBaseUnit().name()));
        response.setQuantity(stock.getQuantity());

        return response;
    }

    public StockMovementResponse toMovementResponse(StockMovement movement) {
        StockMovementResponse response = new StockMovementResponse();

        response.setId(movement.getId());
        response.setItemId(movement.getItem().getId());
        response.setItemName(movement.getItem().getName());
        response.setSourceId(movement.getSourceId());
        response.setQuantity(movement.getQuantity());
        response.setType(StockMovementResponse.TypeEnum.valueOf(movement.getType().name()));
        response.setCreatedAt(movement.getCreatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }

    public StockAlertResponse toAlertResponse(StockAlertData alert) {
        StockAlertResponse response = new StockAlertResponse();

        response.setItemId(alert.item().getId());
        response.setItemName(alert.item().getName());
        response.setItemType(StockAlertResponse.ItemTypeEnum.valueOf(alert.item().getType().name()));
        response.setBaseUnit(StockAlertResponse.BaseUnitEnum.valueOf(alert.item().getBaseUnit().name()));
        response.setCurrentQuantity(alert.currentQuantity());
        response.setLowStockThreshold(alert.lowStockThreshold());
        response.setCriticalStockThreshold(alert.criticalStockThreshold());
        response.setStatus(StockAlertResponse.StatusEnum.valueOf(alert.status().name()));

        return response;
    }
}