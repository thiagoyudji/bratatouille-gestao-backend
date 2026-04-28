package br.com.bratatouille.management.lot.entity;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.production.entity.Production;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lots")
public class Lot {

    private static final int DEFAULT_EXPIRATION_MONTHS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "production_id", nullable = false, unique = true)
    private Production production;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Lot() {
    }

    private Lot(
            Production production,
            Item item,
            LocalDate productionDate,
            BigDecimal quantity
    ) {
        validate(production, item, productionDate, quantity);

        this.production = production;
        this.item = item;
        this.productionDate = productionDate;
        this.expirationDate = productionDate.plusMonths(DEFAULT_EXPIRATION_MONTHS);
        this.quantity = quantity;
    }

    public static Lot create(
            Production production,
            Item item,
            LocalDate productionDate,
            BigDecimal quantity
    ) {
        return new Lot(production, item, productionDate, quantity);
    }

    private static void validate(
            Production production,
            Item item,
            LocalDate productionDate,
            BigDecimal quantity
    ) {
        if (production == null) {
            throw new IllegalArgumentException("production is required");
        }

        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (productionDate == null) {
            throw new IllegalArgumentException("productionDate is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public Production getProduction() {
        return production;
    }

    public Item getItem() {
        return item;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}