package br.com.bratatouille.management.stock.entity;
import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Item item;

    private BigDecimal quantity;

    protected Stock() {
    }

    public Stock(Item item, BigDecimal quantity) {
        this.item = item;
        this.quantity = quantity;
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

}