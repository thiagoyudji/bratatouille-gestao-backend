package br.com.bratatouille.management.recipe.entity;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.recipe.domain.ItemQuantityData;
import br.com.bratatouille.management.item.entity.ItemType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "output_item_id", nullable = false)
    private Item outputItem;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal yieldQuantity;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<RecipeItem> items = new ArrayList<>();

    protected Recipe() {
    }

    private Recipe(String name, Item outputItem, BigDecimal yieldQuantity) {
        validateHeader(name, outputItem);
        validateYieldQuantity(yieldQuantity);

        this.name = name;
        this.outputItem = outputItem;
        this.yieldQuantity = yieldQuantity;
        this.active = true;
    }

    public static Recipe create(String name, Item outputItem, BigDecimal yieldQuantity, List<ItemQuantityData> itemsData) {
        validateItemsData(itemsData);
        validateDuplicatedItems(itemsData);
        validateOutputItemIsNotInput(outputItem, itemsData);

        Recipe recipe = new Recipe(name, outputItem, yieldQuantity);
        recipe.replaceItems(itemsData);

        return recipe;
    }

    @Deprecated
    public static Recipe create(String name, Item outputItem, List<ItemQuantityData> itemsData) {
        return create(name, outputItem, BigDecimal.ONE, itemsData);
    }

    public void update(String name, Item outputItem, BigDecimal yieldQuantity, List<ItemQuantityData> itemsData) {
        validateHeader(name, outputItem);
        validateItemsData(itemsData);
        validateDuplicatedItems(itemsData);
        validateOutputItemIsNotInput(outputItem, itemsData);
        validateYieldQuantity(yieldQuantity);

        this.name = name;
        this.outputItem = outputItem;
        this.yieldQuantity = yieldQuantity;

        replaceItems(itemsData);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private void replaceItems(List<ItemQuantityData> itemsData) {
        this.items.clear();

        itemsData.forEach(itemData -> {
            if (!Boolean.TRUE.equals(itemData.item().isActive())) {
                throw new IllegalArgumentException("recipe input item must be active");
            }

            RecipeItem recipeItem = RecipeItem.create(
                    this,
                    itemData.item(),
                    itemData.quantity()
            );

            this.items.add(recipeItem);
        });
    }

    private static void validateHeader(String name, Item outputItem) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        if (outputItem == null) {
            throw new IllegalArgumentException("outputItem is required");
        }

        if (!Boolean.TRUE.equals(outputItem.isActive())) {
            throw new IllegalArgumentException("outputItem must be active");
        }

        if (outputItem.getType() != ItemType.FINISHED_PRODUCT) {
            throw new IllegalArgumentException("recipe output item must be a finished product");
        }
    }

    private static void validateItemsData(List<ItemQuantityData> itemsData) {
        if (itemsData == null || itemsData.isEmpty()) {
            throw new IllegalArgumentException("recipe must have at least one item");
        }
    }

    private static void validateYieldQuantity(BigDecimal yieldQuantity) {
        if (yieldQuantity == null || yieldQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("yieldQuantity must be greater than zero");
        }
    }

    private static void validateDuplicatedItems(List<ItemQuantityData> itemsData) {
        long distinctItems = itemsData.stream()
                .map(itemData -> itemData.item().getId())
                .distinct()
                .count();

        if (distinctItems != itemsData.size()) {
            throw new IllegalArgumentException("recipe cannot have duplicated items");
        }
    }

    private static void validateOutputItemIsNotInput(Item outputItem, List<ItemQuantityData> itemsData) {
        boolean outputItemIsInput = itemsData.stream()
                .anyMatch(itemData -> itemData.item().getId().equals(outputItem.getId()));

        if (outputItemIsInput) {
            throw new IllegalArgumentException("recipe input item cannot be the same as output item");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Item getOutputItem() {
        return outputItem;
    }

    public BigDecimal getYieldQuantity() {
        return yieldQuantity;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<RecipeItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
