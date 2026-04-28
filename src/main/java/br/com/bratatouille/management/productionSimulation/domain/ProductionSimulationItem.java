package br.com.bratatouille.management.productionSimulation.domain;

import java.math.BigDecimal;

public record ProductionSimulationItem(
        Long itemId,
        String itemName,
        BigDecimal requiredQuantity,
        BigDecimal currentStock,
        BigDecimal missingQuantity,
        BigDecimal unitCost,
        BigDecimal totalCost
) {
}