package br.com.bratatouille.management.purchase.entity;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.UnitType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_items")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @Column(nullable = false)
    private UnitType unit;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalValue;

    protected PurchaseItem() {
    }

    private PurchaseItem(
            Purchase purchase,
            Item item,
            BigDecimal quantity,
            UnitType unit,
            BigDecimal totalValue
    ) {
        validate(purchase, item, quantity, String.valueOf(unit), totalValue);

        this.purchase = purchase;
        this.item = item;
        this.quantity = quantity;
        this.unit = unit;
        this.totalValue = MoneyUtils.normalize(totalValue);
    }

    public static PurchaseItem create(
            Purchase purchase,
            Item item,
            BigDecimal quantity,
            UnitType unit,
            BigDecimal totalValue
    ) {
        return new PurchaseItem(purchase, item, quantity, unit, totalValue);
    }

    private static void validate(
            Purchase purchase,
            Item item,
            BigDecimal quantity,
            String unit,
            BigDecimal totalValue
    ) {
        if (purchase == null) {
            throw new IllegalArgumentException("purchase is required");
        }

        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit is required");
        }

        if (!unit.equals(item.getBaseUnit().name())) {
            throw new IllegalArgumentException("purchase item unit must match item base unit");
        }

        if (totalValue == null || totalValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("totalValue must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public UnitType getUnit() {
        return unit;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }
}