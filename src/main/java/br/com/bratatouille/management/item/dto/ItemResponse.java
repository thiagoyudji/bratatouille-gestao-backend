package br.com.bratatouille.management.item.dto;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;

import java.time.LocalDateTime;

public record ItemResponse(
        Long id,
        String name,
        ItemType type,
        UnitType baseUnit,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getType(),
                item.getBaseUnit(),
                item.isActive(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}