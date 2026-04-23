package br.com.bratatouille.management.purchase.domain;

import br.com.bratatouille.management.item.entity.Item;

import java.math.BigDecimal;

public record PurchaseItemData(
    Item item,
    BigDecimal quantity,
    String unit,
    BigDecimal totalValue
) {}