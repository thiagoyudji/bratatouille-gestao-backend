package br.com.bratatouille.management.stock.dto;

import br.com.bratatouille.management.stock.entity.StockMovement;
import br.com.bratatouille.management.stock.entity.StockMovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockMovementResponse(
        Long id,
        Long itemId,
        String itemName,
        BigDecimal quantity,
        StockMovementType type,
        LocalDateTime createdAt
) {
    public static StockMovementResponse from(StockMovement movement) {
        return new StockMovementResponse(
                movement.getId(),
                movement.getItem().getId(),
                movement.getItem().getName(),
                movement.getQuantity(),
                movement.getType(),
                movement.getCreatedAt()
        );
    }
}