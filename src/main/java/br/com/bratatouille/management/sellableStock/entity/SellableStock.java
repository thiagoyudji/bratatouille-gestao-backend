package br.com.bratatouille.management.sellableStock.entity;

import br.com.bratatouille.management.item.entity.Item;
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

    protected SellableStock() {
    }

    private SellableStock(Item item, BigDecimal availableQuantity, Boolean infinite, Boolean enabled) {
        validate(item, availableQuantity, infinite, enabled);

        this.item = item;
        this.availableQuantity = normalizeQuantity(availableQuantity);
        this.infinite = infinite;
        this.enabled = enabled;
    }

    public static SellableStock create(Item item, BigDecimal availableQuantity, Boolean infinite, Boolean enabled) {
        return new SellableStock(item, availableQuantity, infinite, enabled);
    }

    public void update(BigDecimal availableQuantity, Boolean infinite, Boolean enabled) {
        validate(this.item, availableQuantity, infinite, enabled);

        this.availableQuantity = normalizeQuantity(availableQuantity);
        this.infinite = infinite;
        this.enabled = enabled;
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

    public void decrease(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (Boolean.TRUE.equals(this.infinite)) {
            return;
        }

        if (this.availableQuantity.compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Insufficient sellable stock");
        }

        this.availableQuantity = this.availableQuantity.subtract(quantity);
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
}