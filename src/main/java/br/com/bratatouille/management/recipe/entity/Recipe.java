package br.com.bratatouille.management.recipe.entity;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.recipe.domain.ItemQuantityData;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    private Recipe(String name, Item outputItem) {
        validateHeader(name, outputItem);

        this.name = name;
        this.outputItem = outputItem;
        this.active = true;
    }

    public static Recipe create(String name, Item outputItem, List<ItemQuantityData> itemsData) {
        validateItemsData(itemsData);
        validateDuplicatedItems(itemsData);
        validateOutputItemIsNotInput(outputItem, itemsData);

        Recipe recipe = new Recipe(name, outputItem);
        recipe.addItems(itemsData);

        return recipe;
    }

    private void addItems(List<ItemQuantityData> itemsData) {
        itemsData.forEach(itemData -> {
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
    }

    private static void validateItemsData(List<ItemQuantityData> itemsData) {
        if (itemsData == null || itemsData.isEmpty()) {
            throw new IllegalArgumentException("recipe must have at least one item");
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