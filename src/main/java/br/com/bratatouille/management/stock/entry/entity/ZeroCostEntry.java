package br.com.bratatouille.management.stock.entry.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "zero_cost_entry")
public class ZeroCostEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ZeroCostEntryReason reason;

    private String note;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected ZeroCostEntry() {}

    private ZeroCostEntry(Item item, BigDecimal quantity, ZeroCostEntryReason reason, String note) {
        this.item = item;
        this.quantity = quantity;
        this.reason = reason;
        this.note = note;
    }

    public static ZeroCostEntry create(Item item, BigDecimal quantity, ZeroCostEntryReason reason, String note) {
        if (item == null) throw new IllegalArgumentException("item is required");
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("quantity must be greater than zero");
        if (reason == null) throw new IllegalArgumentException("reason is required");

        return new ZeroCostEntry(item, quantity, reason, note);
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

    public ZeroCostEntryReason getReason() {
        return reason;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}