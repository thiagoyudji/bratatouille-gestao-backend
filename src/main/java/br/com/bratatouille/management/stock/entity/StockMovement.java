package br.com.bratatouille.management.stock.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class StockMovement {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Item item;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private StockMovementType type;

    private LocalDateTime createdAt;

    public StockMovement(Item item, BigDecimal quantity, StockMovementType type) {
        this.item = item;
        this.quantity = quantity;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    protected StockMovement() {
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

    public StockMovementType getType() {
        return type;
    }

    public void setType(StockMovementType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}