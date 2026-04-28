package br.com.bratatouille.management.purchase.entity;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paid_by_partner_id", nullable = false)
    private Partner paidBy;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    private String note;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String supplier;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PurchaseItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PurchaseSplit> splits = new ArrayList<>();

    protected Purchase() {
    }

    private Purchase(LocalDate purchaseDate, Partner paidBy, String supplier, String note) {
        validateHeader(purchaseDate, paidBy, supplier);

        this.purchaseDate = purchaseDate;
        this.paidBy = paidBy;
        this.supplier = supplier;
        this.note = note;
    }

    public static Purchase create(
            LocalDate purchaseDate,
            Partner paidBy,
            String supplier,
            String note,
            List<PurchaseItemData> itemsData,
            List<PurchaseSplitData> splitsData
    ) {
        validateItemsData(itemsData);
        validateDuplicatedItems(itemsData);
        validateSplitsData(splitsData);
        validateDuplicatedSplitPartners(splitsData);

        Purchase purchase = new Purchase(purchaseDate, paidBy, supplier, note);

        purchase.addItems(itemsData);
        purchase.defineTotalFromItems();
        purchase.addSplits(splitsData);
        purchase.validateSplitTotal();

        return purchase;
    }

    private static void validateDuplicatedSplitPartners(List<PurchaseSplitData> splitsData) {
        long distinctPartners = splitsData.stream()
                .map(split -> split.partner().getId())
                .distinct()
                .count();

        if (distinctPartners != splitsData.size()) {
            throw new IllegalArgumentException("purchase split cannot have duplicated partners");
        }
    }

    private static void validateDuplicatedItems(List<PurchaseItemData> itemsData) {
        long distinctItems = itemsData.stream()
                .map(item -> item.item().getId())
                .distinct()
                .count();

        if (distinctItems != itemsData.size()) {
            throw new IllegalArgumentException("purchase cannot have duplicated items");
        }
    }

    private void addItems(List<PurchaseItemData> itemsData) {
        itemsData.forEach(itemData -> {
            PurchaseItem item = PurchaseItem.create(
                    this,
                    itemData.item(),
                    itemData.quantity(),
                    UnitType.valueOf(itemData.unit()),
                    itemData.totalValue()
            );

            this.items.add(item);
        });
    }

    private void addSplits(List<PurchaseSplitData> splitsData) {
        splitsData.forEach(splitData -> {
            PurchaseSplit split = PurchaseSplit.create(
                    this,
                    splitData.partner(),
                    splitData.amount()
            );

            this.splits.add(split);
        });
    }

    private void defineTotalFromItems() {
        this.totalAmount = MoneyUtils.normalize(
                this.items.stream()
                        .map(PurchaseItem::getTotalValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    private void validateSplitTotal() {
        BigDecimal splitTotal = MoneyUtils.normalize(
                this.splits.stream()
                        .map(PurchaseSplit::getOwedAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        if (!MoneyUtils.equals(this.totalAmount, splitTotal)) {
            throw new IllegalArgumentException("split total must be equal to purchase total");
        }
    }

    private static void validateHeader(LocalDate purchaseDate, Partner paidBy, String supplier) {
        if (purchaseDate == null) {
            throw new IllegalArgumentException("purchaseDate is required");
        }

        if (paidBy == null) {
            throw new IllegalArgumentException("paidBy is required");
        }

        if (supplier == null || supplier.isBlank()) {
            throw new IllegalArgumentException("supplier is required");
        }
    }

    private static void validateItemsData(List<PurchaseItemData> itemsData) {
        if (itemsData == null || itemsData.isEmpty()) {
            throw new IllegalArgumentException("purchase must have at least one item");
        }
    }

    private static void validateSplitsData(List<PurchaseSplitData> splitsData) {
        if (splitsData == null || splitsData.isEmpty()) {
            throw new IllegalArgumentException("purchase must have at least one split");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Partner getPaidBy() {
        return paidBy;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<PurchaseItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<PurchaseSplit> getSplits() {
        return Collections.unmodifiableList(splits);
    }

    public String getSupplier() {
        return supplier;
    }
}