package br.com.bratatouille.management.item.entity;

import jakarta.persistence.*;
import br.com.bratatouille.management.common.util.MoneyUtils;
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

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePf;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePj;

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
        this(name, type, baseUnit, lowStockThreshold, criticalStockThreshold, null, null);
    }

    public Item(
            String name,
            ItemType type,
            UnitType baseUnit,
            BigDecimal lowStockThreshold,
            BigDecimal criticalStockThreshold,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }

        if (baseUnit == null) {
            throw new IllegalArgumentException("baseUnit is required");
        }

        validateThresholds(lowStockThreshold, criticalStockThreshold);
        validatePrices(pricePf, pricePj);

        this.name = name;
        this.type = type;
        this.baseUnit = baseUnit;
        this.lowStockThreshold = lowStockThreshold;
        this.criticalStockThreshold = criticalStockThreshold;
        this.pricePf = normalizeMoney(pricePf);
        this.pricePj = normalizeMoney(pricePj);
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

    public void update(
            String name,
            BigDecimal lowStockThreshold,
            BigDecimal criticalStockThreshold,
            Boolean active
    ) {
        update(name, lowStockThreshold, criticalStockThreshold, active, null, null);
    }

    public void update(
            String name,
            BigDecimal lowStockThreshold,
            BigDecimal criticalStockThreshold,
            Boolean active,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        validateThresholds(lowStockThreshold, criticalStockThreshold);
        validatePrices(pricePf, pricePj);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        this.name = name;
        this.lowStockThreshold = lowStockThreshold;
        this.criticalStockThreshold = criticalStockThreshold;
        this.pricePf = normalizeMoney(pricePf);
        this.pricePj = normalizeMoney(pricePj);

        if (active != null) {
            this.active = active;
        }
    }

    @Deprecated
    public void update(String name, ItemType ignoredType, BigDecimal lowStockThreshold,
                       BigDecimal criticalStockThreshold, Boolean active) {
        update(name, lowStockThreshold, criticalStockThreshold, active, pricePf, pricePj);
    }

    private void validatePrices(BigDecimal pricePf, BigDecimal pricePj) {
        if (pricePf != null && pricePf.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("pricePf cannot be negative");
        }

        if (pricePj != null && pricePj.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("pricePj cannot be negative");
        }
    }

    private BigDecimal normalizeMoney(BigDecimal value) {
        return value == null ? null : MoneyUtils.normalize(value);
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

    public BigDecimal getPricePf() {
        return pricePf;
    }

    public BigDecimal getPricePj() {
        return pricePj;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
