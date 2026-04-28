package br.com.bratatouille.management.recipe.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_items")
public class RecipeItem {

    private static final BigDecimal FULL_YIELD = BigDecimal.ONE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal yieldPercentage;

    protected RecipeItem() {
    }

    private RecipeItem(
            Recipe recipe,
            Item item,
            BigDecimal quantity,
            BigDecimal yieldPercentage
    ) {
        validate(recipe, item, quantity, yieldPercentage);

        this.recipe = recipe;
        this.item = item;
        this.quantity = quantity;
        this.yieldPercentage = normalizeYield(yieldPercentage);
    }

    public static RecipeItem create(
            Recipe recipe,
            Item item,
            BigDecimal quantity,
            BigDecimal yieldPercentage
    ) {
        return new RecipeItem(recipe, item, quantity, yieldPercentage);
    }

    private static void validate(
            Recipe recipe,
            Item item,
            BigDecimal quantity,
            BigDecimal yieldPercentage
    ) {
        if (recipe == null) {
            throw new IllegalArgumentException("recipe is required");
        }

        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        BigDecimal normalizedYield = normalizeYield(yieldPercentage);

        if (normalizedYield.compareTo(BigDecimal.ZERO) <= 0 || normalizedYield.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("yieldPercentage must be between 0 and 1");
        }
    }

    private static BigDecimal normalizeYield(BigDecimal yieldPercentage) {
        return yieldPercentage == null ? FULL_YIELD : yieldPercentage;
    }

    public Long getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getYieldPercentage() {
        return yieldPercentage;
    }
}