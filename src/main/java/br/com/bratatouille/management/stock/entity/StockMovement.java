package br.com.bratatouille.management.stock.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @Enumerated(EnumType.STRING)
    private StockMovementType type;

    private BigDecimal quantity;

    private String sourceType;

    private Long sourceId;

    private LocalDateTime createdAt;

    // getters/setters
}