package br.com.bratatouille.management.stock.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockMovementType type;

    private Long sourceId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected StockMovement() {
    }

    public StockMovement(Item item, BigDecimal quantity, StockMovementType type, Long sourceId) {
        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("quantity cannot be zero");
        }

        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }

        if (type != StockMovementType.MANUAL_ADJUSTMENT && sourceId == null) {
            throw new IllegalArgumentException("sourceId is required");
        }

        this.item = item;
        this.quantity = quantity;
        this.type = type;
        this.sourceId = sourceId;
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public StockMovementType getType() {
        return type;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}