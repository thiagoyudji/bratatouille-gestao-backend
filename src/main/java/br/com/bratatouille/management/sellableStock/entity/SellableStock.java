package br.com.bratatouille.management.sellableStock.entity;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.common.util.MoneyUtils;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "sellable_stocks",
        uniqueConstraints = @UniqueConstraint(name = "uk_sellable_stock_item", columnNames = "item_id")
)
public class SellableStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Boolean infinite;

    @Column(nullable = false)
    private Boolean active;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePf;

    @Column(precision = 19, scale = 6)
    private BigDecimal pricePj;

    protected SellableStock() {
    }

    private SellableStock(
            Item item,
            Boolean infinite,
            Boolean active,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        validate(item, infinite, active);

        this.item = item;
        this.infinite = infinite;
        this.active = active;
        this.pricePf = normalizeMoney(pricePf);
        this.pricePj = normalizeMoney(pricePj);
    }

    public static SellableStock create(Item item, Boolean infinite, Boolean active) {
        return new SellableStock(item, infinite, active, null, null);
    }

    public static SellableStock create(
            Item item,
            Boolean infinite,
            Boolean active,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        return new SellableStock(item, infinite, active, pricePf, pricePj);
    }

    public void update(Boolean infinite, Boolean active) {
        update(infinite, active, null, null);
    }

    public void update(
            Boolean infinite,
            Boolean active,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
        validate(this.item, infinite, active);

        this.infinite = infinite;
        this.active = active;
        this.pricePf = normalizeMoney(pricePf);
        this.pricePj = normalizeMoney(pricePj);
    }

    private static void validate(Item item, Boolean infinite, Boolean active) {
        if (item == null) {
            throw new IllegalArgumentException("item is required");
        }

        if (infinite == null) {
            throw new IllegalArgumentException("infinite is required");
        }

        if (active == null) {
            throw new IllegalArgumentException("active is required");
        }
    }

    private static BigDecimal normalizeMoney(BigDecimal value) {
        return value == null ? null : MoneyUtils.normalize(value);
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Boolean getInfinite() {
        return infinite;
    }

    public Boolean getActive() {
        return active;
    }


    public BigDecimal getPricePf() {
        return pricePf;
    }

    public BigDecimal getPricePj() {
        return pricePj;
    }
}
