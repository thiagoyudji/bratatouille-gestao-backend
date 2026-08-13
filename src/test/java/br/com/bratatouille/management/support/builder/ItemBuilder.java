package br.com.bratatouille.management.support.builder;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

public class ItemBuilder {

    private Long id = 1L;
    private String name = "Item";
    private ItemType type = ItemType.INGREDIENT;
    private UnitType baseUnit = UnitType.UN;
    private BigDecimal lowStockThreshold = new BigDecimal("10.000");
    private BigDecimal criticalStockThreshold = new BigDecimal("5.000");
    private BigDecimal pricePf = new BigDecimal("18.50");
    private BigDecimal pricePj = new BigDecimal("24.90");

    public ItemBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder withType(ItemType type) {
        this.type = type;
        return this;
    }

    public ItemBuilder withBaseUnit(UnitType baseUnit) {
        this.baseUnit = baseUnit;
        return this;
    }

    public ItemBuilder withLowStockThreshold(BigDecimal lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
        return this;
    }

    public ItemBuilder withCriticalStockThreshold(BigDecimal criticalStockThreshold) {
        this.criticalStockThreshold = criticalStockThreshold;
        return this;
    }

    public ItemBuilder withPricePf(BigDecimal pricePf) {
        this.pricePf = pricePf;
        return this;
    }

    public ItemBuilder withPricePj(BigDecimal pricePj) {
        this.pricePj = pricePj;
        return this;
    }

    public Item build() {
        Item item = new Item(name, type, baseUnit, lowStockThreshold, criticalStockThreshold, pricePf, pricePj);
        ReflectionTestUtils.setField(item, "id", id);
        return item;
    }
}
