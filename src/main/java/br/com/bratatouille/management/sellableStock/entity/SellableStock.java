package br.com.bratatouille.management.sellableStock.entity;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.common.util.MoneyUtils;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "sellable_stocks",
        uniqueConstraints = @UniqueConstraint(name = "uk_sellable_stock_item", columnNames = "item_id")
)
public class SellableStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal availableQuantity;

    @Column(nullable = false)
    private Boolean infinite;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePf;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePj;

    protected SellableStock() {
    }

    private SellableStock(
            Item item,
            BigDecimal availableQuantity,
            Boolean infinite,
            Boolean enabled,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        validate(item, availableQuantity, infinite, enabled);

        this.item = item;
        this.availableQuantity = normalizeQuantity(availableQuantity);
        this.infinite = infinite;
        this.enabled = enabled;
        this.pricePf = normalizeMoney(pricePf);
        this.pricePj = normalizeMoney(pricePj);
    }

    public static SellableStock create(Item item, BigDecimal availableQuantity, Boolean infinite, Boolean enabled) {
        return new SellableStock(item, availableQuantity, infinite, enabled, null, null);
    }

    public static SellableStock create(
            Item item,
            BigDecimal availableQuantity,
            Boolean infinite,
            Boolean enabled,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        return new SellableStock(item, availableQuantity, infinite, enabled, pricePf, pricePj);
    }

    public void update(BigDecimal availableQuantity, Boolean infinite, Boolean enabled) {
        update(availableQuantity, infinite, enabled, null, null);
    }

    public void update(
            BigDecimal availableQuantity,
            Boolean infinite,
            Boolean enabled,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        validate(this.item, availableQuantity, infinite, enabled);

        this.availableQuantity = normalizeQuantity(availableQuantity);
        this.infinite = infinite;
        this.enabled = enabled;
        this.pricePf = normalizeMoney(pricePf);
        this.pricePj = normalizeMoney(pricePj);
    }

    private static void validate(Item item, BigDecimal availableQuantity, Boolean infinite, Boolean enabled) {
        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (infinite == null) {
            throw new IllegalArgumentException("infinite is required");
        }

        if (enabled == null) {
            throw new IllegalArgumentException("enabled is required");
        }

        if (!infinite && (availableQuantity == null || availableQuantity.compareTo(BigDecimal.ZERO) < 0)) {
            throw new IllegalArgumentException("availableQuantity cannot be negative");
        }
    }

    private static BigDecimal normalizeQuantity(BigDecimal availableQuantity) {
        return availableQuantity == null ? BigDecimal.ZERO : availableQuantity;
    }

    private static BigDecimal normalizeMoney(BigDecimal value) {
        return value == null ? null : MoneyUtils.normalize(value);
    }

    public void decrease(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (Boolean.TRUE.equals(this.infinite)) {
            return;
        }

        this.availableQuantity = this.availableQuantity.subtract(quantity);
    }

    public void increase(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (Boolean.TRUE.equals(this.infinite)) {
            return;
        }

        this.availableQuantity = this.availableQuantity.add(quantity);
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public Boolean getInfinite() {
        return infinite;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public BigDecimal getPricePf() {
        return pricePf;
    }

    public BigDecimal getPricePj() {
        return pricePj;
    }
}
