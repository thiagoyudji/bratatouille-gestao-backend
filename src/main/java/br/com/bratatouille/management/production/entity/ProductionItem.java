package br.com.bratatouille.management.production.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "production_items")
public class ProductionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "production_id", nullable = false)
    private Production production;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal consumedQuantity;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal usableQuantity;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal lossQuantity;

    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal yieldPercentage;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal unitCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    protected ProductionItem() {
    }

    private ProductionItem(
            Production production,
            Item item,
            BigDecimal consumedQuantity,
            BigDecimal usableQuantity,
            BigDecimal lossQuantity,
            BigDecimal yieldPercentage,
            BigDecimal unitCost,
            BigDecimal totalCost
    ) {
        this.production = production;
        this.item = item;
        this.consumedQuantity = consumedQuantity;
        this.usableQuantity = usableQuantity;
        this.lossQuantity = lossQuantity;
        this.yieldPercentage = yieldPercentage;
        this.unitCost = unitCost;
        this.totalCost = totalCost;
    }

    public static ProductionItem create(
            Production production,
            Item item,
            BigDecimal consumedQuantity,
            BigDecimal usableQuantity,
            BigDecimal lossQuantity,
            BigDecimal yieldPercentage,
            BigDecimal unitCost,
            BigDecimal totalCost
    ) {
        if (production == null) {
            throw new IllegalArgumentException("production is required");
        }

        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (consumedQuantity == null || consumedQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("consumedQuantity must be > 0");
        }

        if (usableQuantity == null || usableQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("usableQuantity must be > 0");
        }

        if (lossQuantity == null || lossQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("lossQuantity cannot be negative");
        }

        if (yieldPercentage == null
                || yieldPercentage.compareTo(BigDecimal.ZERO) <= 0
                || yieldPercentage.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("yieldPercentage must be between 0 and 1");
        }

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitCost must be > 0");
        }

        if (totalCost == null || totalCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("totalCost must be > 0");
        }

        return new ProductionItem(
                production,
                item,
                consumedQuantity,
                usableQuantity,
                lossQuantity,
                yieldPercentage,
                unitCost,
                totalCost
        );
    }

    public Long getId() {
        return id;
    }

    public Production getProduction() {
        return production;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getConsumedQuantity() {
        return consumedQuantity;
    }

    public BigDecimal getUsableQuantity() {
        return usableQuantity;
    }

    public BigDecimal getLossQuantity() {
        return lossQuantity;
    }

    public BigDecimal getYieldPercentage() {
        return yieldPercentage;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}