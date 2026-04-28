package br.com.bratatouille.management.sales.entity;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.sales.domain.SalesOrderItemData;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "sales_orders")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate saleDate;

    private String customerName;

    private String note;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalAmount;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal grossProfit;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<SalesOrderItem> items = new ArrayList<>();

    protected SalesOrder() {
    }

    private SalesOrder(LocalDate saleDate, String customerName, String note) {
        if (saleDate == null) {
            throw new IllegalArgumentException("saleDate is required");
        }

        this.saleDate = saleDate;
        this.customerName = customerName;
        this.note = note;
    }

    public static SalesOrder create(LocalDate saleDate, String customerName, String note, List<SalesOrderItemData> itemsData) {
        if (itemsData == null || itemsData.isEmpty()) {
            throw new IllegalArgumentException("sale must have at least one item");
        }

        SalesOrder salesOrder = new SalesOrder(saleDate, customerName, note);

        itemsData.forEach(itemData -> salesOrder.items.add(
                SalesOrderItem.create(
                        salesOrder,
                        itemData.item(),
                        itemData.quantity(),
                        itemData.unitPrice(),
                        itemData.unitCost()
                )
        ));

        salesOrder.calculateTotals();

        return salesOrder;
    }

    private void calculateTotals() {
        this.totalAmount = MoneyUtils.normalize(
                this.items.stream()
                        .map(SalesOrderItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        this.totalCost = MoneyUtils.normalize(
                this.items.stream()
                        .map(SalesOrderItem::getTotalCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        this.grossProfit = MoneyUtils.normalize(this.totalAmount.subtract(this.totalCost));
    }

    public Long getId() { return id; }

    public LocalDate getSaleDate() { return saleDate; }

    public String getCustomerName() { return customerName; }

    public String getNote() { return note; }

    public BigDecimal getTotalAmount() { return totalAmount; }

    public BigDecimal getTotalCost() { return totalCost; }

    public BigDecimal getGrossProfit() { return grossProfit; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public List<SalesOrderItem> getItems() { return Collections.unmodifiableList(items); }
}