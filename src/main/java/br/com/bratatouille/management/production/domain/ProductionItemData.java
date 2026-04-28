package br.com.bratatouille.management.production.domain;

import br.com.bratatouille.management.item.entity.Item;

import java.math.BigDecimal;

public record ProductionItemData(
        Item item,
        BigDecimal consumedQuantity,
        BigDecimal usableQuantity,
        BigDecimal lossQuantity,
        BigDecimal yieldPercentage,
        BigDecimal unitCost,
        BigDecimal totalCost
) {
}