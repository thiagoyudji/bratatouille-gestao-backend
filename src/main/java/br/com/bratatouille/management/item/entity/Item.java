package br.com.bratatouille.management.item.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private UnitType baseUnit;

    private Boolean active;

    @Column(precision = 19, scale = 3)
    private BigDecimal lowStockThreshold;

    @Column(precision = 19, scale = 3)
    private BigDecimal criticalStockThreshold;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Item() {
    }

    public Item(
            String name,
            ItemType type,
            UnitType baseUnit,
            BigDecimal lowStockThreshold,
            BigDecimal criticalStockThreshold
    ) {
        validateThresholds(lowStockThreshold, criticalStockThreshold);

        this.name = name;
        this.type = type;
        this.baseUnit = baseUnit;
        this.lowStockThreshold = lowStockThreshold;
        this.criticalStockThreshold = criticalStockThreshold;
        this.active = true;
    }

    private void validateThresholds(BigDecimal lowStockThreshold, BigDecimal criticalStockThreshold) {
        if (lowStockThreshold != null && lowStockThreshold.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("lowStockThreshold cannot be negative");
        }

        if (criticalStockThreshold != null && criticalStockThreshold.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("criticalStockThreshold cannot be negative");
        }

        if (
                lowStockThreshold != null
                        && criticalStockThreshold != null
                        && criticalStockThreshold.compareTo(lowStockThreshold) > 0
        ) {
            throw new IllegalArgumentException("criticalStockThreshold cannot be greater than lowStockThreshold");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public UnitType getBaseUnit() {
        return baseUnit;
    }

    public Boolean isActive() {
        return active;
    }

    public BigDecimal getLowStockThreshold() {
        return lowStockThreshold;
    }

    public BigDecimal getCriticalStockThreshold() {
        return criticalStockThreshold;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}