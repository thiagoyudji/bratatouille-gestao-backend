package br.com.bratatouille.management.production.entity;

import br.com.bratatouille.management.recipe.entity.Recipe;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "productions")
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal producedQuantity;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal unitCost;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Production() {
    }

    private Production(
            Recipe recipe,
            BigDecimal producedQuantity,
            BigDecimal totalCost,
            BigDecimal unitCost
    ) {
        validate(recipe, producedQuantity, totalCost, unitCost);

        this.recipe = recipe;
        this.producedQuantity = producedQuantity;
        this.totalCost = totalCost;
        this.unitCost = unitCost;
    }

    public static Production create(
            Recipe recipe,
            BigDecimal producedQuantity,
            BigDecimal totalCost
    ) {
        BigDecimal unitCost = totalCost.divide(producedQuantity, 6, RoundingMode.HALF_UP);

        return new Production(
                recipe,
                producedQuantity,
                totalCost,
                unitCost
        );
    }

    private static void validate(
            Recipe recipe,
            BigDecimal producedQuantity,
            BigDecimal totalCost,
            BigDecimal unitCost
    ) {
        if (recipe == null) {
            throw new IllegalArgumentException("recipe is required");
        }

        if (producedQuantity == null || producedQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("producedQuantity must be > 0");
        }

        if (totalCost == null || totalCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("totalCost must be > 0");
        }

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitCost must be > 0");
        }
    }

    public Long getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public BigDecimal getProducedQuantity() {
        return producedQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}