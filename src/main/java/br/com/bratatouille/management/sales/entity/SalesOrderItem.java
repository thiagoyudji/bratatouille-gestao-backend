package br.com.bratatouille.management.sales.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalPrice;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal unitCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal grossProfit;

    protected SalesOrderItem() {
    }

    private SalesOrderItem(SalesOrder salesOrder, Item item, BigDecimal quantity, BigDecimal unitPrice, BigDecimal unitCost) {
        this.salesOrder = salesOrder;
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.totalPrice = unitPrice.multiply(quantity);
        this.totalCost = unitCost.multiply(quantity);
        this.grossProfit = totalPrice.subtract(totalCost);
    }

    public static SalesOrderItem create(SalesOrder salesOrder, Item item, BigDecimal quantity, BigDecimal unitPrice, BigDecimal unitCost) {
        if (salesOrder == null) throw new IllegalArgumentException("salesOrder is required");
        if (item == null) throw new IllegalArgumentException("item is required");
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("quantity must be greater than zero");
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("unitPrice must be greater than zero");
        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("unitCost cannot be negative");

        return new SalesOrderItem(salesOrder, item, quantity, unitPrice, unitCost);
    }

    public Long getId() { return id; }

    public Item getItem() { return item; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public BigDecimal getUnitCost() { return unitCost; }

    public BigDecimal getTotalCost() { return totalCost; }

    public BigDecimal getGrossProfit() { return grossProfit; }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }
}