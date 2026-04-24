package br.com.bratatouille.management.recipe.domain;

import br.com.bratatouille.management.item.entity.Item;

import java.math.BigDecimal;

public record RecipeItemData(
        Item item,
        BigDecimal quantity
) {
}