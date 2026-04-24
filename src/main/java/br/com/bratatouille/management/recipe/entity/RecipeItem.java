package br.com.bratatouille.management.recipe.entity;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_items")
public class RecipeItem {

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

    protected RecipeItem() {
    }

    private RecipeItem(Recipe recipe, Item item, BigDecimal quantity) {
        validate(recipe, item, quantity);

        this.recipe = recipe;
        this.item = item;
        this.quantity = quantity;
    }

    public static RecipeItem create(Recipe recipe, Item item, BigDecimal quantity) {
        return new RecipeItem(recipe, item, quantity);
    }

    private static void validate(Recipe recipe, Item item, BigDecimal quantity) {
        if (recipe == null) {
            throw new IllegalArgumentException("recipe is required");
        }

        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
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
}