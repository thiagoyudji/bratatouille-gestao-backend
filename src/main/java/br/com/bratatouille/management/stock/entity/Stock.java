package br.com.bratatouille.management.stock.entity;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.common.util.MoneyUtils;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "stocks",
        uniqueConstraints = @UniqueConstraint(name = "uk_stock_item", columnNames = "item_id")
)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePf;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePj;

    protected Stock() {
    }

    public Stock(Item item, BigDecimal quantity) {
        this(item, quantity, null, null);
    }

    public Stock(Item item, BigDecimal quantity, BigDecimal pricePf, BigDecimal pricePj) {
        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }

        this.item = item;
        this.quantity = quantity;
        this.pricePf = pricePf;
        this.pricePj = pricePj;
    }

    public void add(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.quantity = this.quantity.add(quantity);
    }

    public void remove(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (this.quantity.compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        this.quantity = this.quantity.subtract(quantity);
    }

    public void adjust(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        this.quantity = quantity;
    }

    public void syncPrices(BigDecimal pricePf, BigDecimal pricePj) {
        this.pricePf = pricePf == null ? null : MoneyUtils.normalize(pricePf);
        this.pricePj = pricePj == null ? null : MoneyUtils.normalize(pricePj);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePf() {
        return pricePf;
    }

    public BigDecimal getPricePj() {
        return pricePj;
    }

}
