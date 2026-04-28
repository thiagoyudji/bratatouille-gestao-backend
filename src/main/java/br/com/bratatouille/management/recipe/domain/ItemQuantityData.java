package br.com.bratatouille.management.recipe.domain;

import br.com.bratatouille.management.item.entity.Item;

import java.math.BigDecimal;

public record ItemQuantityData(
        Item item,
        BigDecimal quantity,
        BigDecimal yieldPercentage
) {
}