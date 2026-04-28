package br.com.bratatouille.management.operationalLoss.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "operational_losses")
public class OperationalLoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate lossDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationalLossReason reason;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal unitCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    private String note;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected OperationalLoss() {
    }

    private OperationalLoss(
            LocalDate lossDate,
            Item item,
            BigDecimal quantity,
            OperationalLossReason reason,
            BigDecimal unitCost,
            String note
    ) {
        validate(lossDate, item, quantity, reason, unitCost);

        this.lossDate = lossDate;
        this.item = item;
        this.quantity = quantity;
        this.reason = reason;
        this.unitCost = unitCost;
        this.totalCost = unitCost.multiply(quantity);
        this.note = note;
    }

    public static OperationalLoss create(
            LocalDate lossDate,
            Item item,
            BigDecimal quantity,
            OperationalLossReason reason,
            BigDecimal unitCost,
            String note
    ) {
        return new OperationalLoss(lossDate, item, quantity, reason, unitCost, note);
    }

    private static void validate(
            LocalDate lossDate,
            Item item,
            BigDecimal quantity,
            OperationalLossReason reason,
            BigDecimal unitCost
    ) {
        if (lossDate == null) {
            throw new IllegalArgumentException("lossDate is required");
        }

        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (reason == null) {
            throw new IllegalArgumentException("reason is required");
        }

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitCost must be greater than zero");
        }
    }

    public Long getId() { return id; }

    public LocalDate getLossDate() { return lossDate; }

    public Item getItem() { return item; }

    public BigDecimal getQuantity() { return quantity; }

    public OperationalLossReason getReason() { return reason; }

    public BigDecimal getUnitCost() { return unitCost; }

    public BigDecimal getTotalCost() { return totalCost; }

    public String getNote() { return note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}