package br.com.bratatouille.management.item.dto;

import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;

public record CreateItemRequest(
        String name,
        ItemType type,
        UnitType baseUnit
) {}