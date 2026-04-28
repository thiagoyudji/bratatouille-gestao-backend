package br.com.bratatouille.management.sales.domain;

import br.com.bratatouille.management.item.entity.Item;

import java.math.BigDecimal;

public record SalesOrderItemData(
        Item item,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal unitCost
) {
}