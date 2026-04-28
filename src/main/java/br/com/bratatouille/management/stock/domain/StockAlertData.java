package br.com.bratatouille.management.stock.domain;

import br.com.bratatouille.management.item.entity.Item;

import java.math.BigDecimal;

public record StockAlertData(
        Item item,
        BigDecimal currentQuantity,
        BigDecimal lowStockThreshold,
        BigDecimal criticalStockThreshold,
        StockAlertStatus status
) {
}