package br.com.bratatouille.management.stock.dto;

import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.stock.entity.Stock;

import java.math.BigDecimal;

public record StockResponse(
        Long id,
        Long itemId,
        String itemName,
        ItemType itemType,
        UnitType baseUnit,
        BigDecimal quantity
) {
    public static StockResponse from(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getItem().getId(),
                stock.getItem().getName(),
                stock.getItem().getType(),
                stock.getItem().getBaseUnit(),
                stock.getQuantity()
        );
    }
}