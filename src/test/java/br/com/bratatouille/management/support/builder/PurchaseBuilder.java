package br.com.bratatouille.management.support.builder;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;
import br.com.bratatouille.management.purchase.entity.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseBuilder {

    private LocalDate purchaseDate = LocalDate.of(2026, 8, 1);
    private Partner paidBy = new PartnerBuilder().withId(1L).withName("Main Partner").build();
    private String supplier = "Supplier";
    private String note = "note";
    private final List<PurchaseItemData> items = new ArrayList<>();
    private final List<PurchaseSplitData> splits = new ArrayList<>();

    public PurchaseBuilder withPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public PurchaseBuilder withPaidBy(Partner paidBy) {
        this.paidBy = paidBy;
        return this;
    }

    public PurchaseBuilder withSupplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public PurchaseBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    public PurchaseBuilder addItem(Item item, BigDecimal quantity, UnitType unit, BigDecimal totalValue) {
        items.add(new PurchaseItemData(item, quantity, unit.name(), totalValue));
        return this;
    }

    public PurchaseBuilder addItem(Item item, BigDecimal quantity, String unit, BigDecimal totalValue) {
        items.add(new PurchaseItemData(item, quantity, unit, totalValue));
        return this;
    }

    public PurchaseBuilder addSplit(Partner partner, BigDecimal percentage, BigDecimal owedAmount) {
        splits.add(new PurchaseSplitData(partner, percentage, owedAmount));
        return this;
    }

    public PurchaseBuilder withItems(List<PurchaseItemData> items) {
        this.items.clear();
        this.items.addAll(items);
        return this;
    }

    public PurchaseBuilder withSplits(List<PurchaseSplitData> splits) {
        this.splits.clear();
        this.splits.addAll(splits);
        return this;
    }

    public Purchase build() {
        return Purchase.create(purchaseDate, paidBy, supplier, note, List.copyOf(items), List.copyOf(splits));
    }
}
