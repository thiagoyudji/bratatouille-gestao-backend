package br.com.bratatouille.management.sales.entity;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.common.util.MoneyUtils;
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

    @Column(precision = 19, scale = 6)
    private BigDecimal unitPricePf;

    @Column(precision = 19, scale = 6)
    private BigDecimal unitPricePj;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalPrice;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal unitCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal grossProfit;

    @Column(nullable = false)
    private Boolean costIncomplete;

    protected SalesOrderItem() {
    }

    private SalesOrderItem(
            SalesOrder salesOrder,
            Item item,
            BigDecimal quantity,
            BigDecimal unitPrice,
            BigDecimal unitPricePf,
            BigDecimal unitPricePj,
            BigDecimal unitCost,
            Boolean costIncomplete
    ) {
        this.salesOrder = salesOrder;
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = MoneyUtils.normalize(unitPrice);
        this.unitPricePf = normalizeMoney(unitPricePf);
        this.unitPricePj = normalizeMoney(unitPricePj);
        this.unitCost = unitCost;
        this.costIncomplete = costIncomplete;
        this.totalPrice = MoneyUtils.normalize(unitPrice.multiply(quantity));
        this.totalCost = MoneyUtils.normalize(unitCost.multiply(quantity));
        this.grossProfit = MoneyUtils.normalize(totalPrice.subtract(totalCost));
    }

    public static SalesOrderItem create(
            SalesOrder salesOrder,
            Item item,
            BigDecimal quantity,
            BigDecimal unitPrice,
            BigDecimal unitPricePf,
            BigDecimal unitPricePj,
            BigDecimal unitCost,
            Boolean costIncomplete
    ) {
        if (salesOrder == null) throw new IllegalArgumentException("salesOrder is required");
        if (item == null) throw new IllegalArgumentException("item is required");
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("quantity must be greater than zero");
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("unitPrice must be greater than zero");
        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("unitCost cannot be negative");
        if (costIncomplete == null) throw new IllegalArgumentException("costIncomplete is required");

        return new SalesOrderItem(salesOrder, item, quantity, unitPrice, unitPricePf, unitPricePj, unitCost, costIncomplete);
    }

    private BigDecimal normalizeMoney(BigDecimal value) {
        return value == null ? null : MoneyUtils.normalize(value);
    }

    public Long getId() { return id; }

    public Item getItem() { return item; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }

    public BigDecimal getUnitPricePf() { return unitPricePf; }

    public BigDecimal getUnitPricePj() { return unitPricePj; }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public BigDecimal getUnitCost() { return unitCost; }

    public BigDecimal getTotalCost() { return totalCost; }

    public BigDecimal getGrossProfit() { return grossProfit; }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public Boolean getCostIncomplete() {
        return costIncomplete;
    }
}
