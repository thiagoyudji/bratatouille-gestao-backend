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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SalesCustomerType customerType;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private String deliveryZipCode;

    private String deliveryStreet;

    private String deliveryNumber;

    private String deliveryNeighborhood;

    private String deliveryState;

    private String deliveryCity;

    private String deliveryComplement;

    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SalesPaymentStatus paymentStatus;

    private String paymentProvider;

    private String paymentProviderTransactionId;

    private String paymentProviderStatus;

    @Column(length = 512)
    private String paymentReceiptUrl;

    @Column(length = 512)
    private String paymentCheckoutUrl;

    private String paymentInvoiceSlug;

    private LocalDateTime paidAt;

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

    private SalesOrder(
            LocalDate saleDate,
            SalesCustomerType customerType,
            String customerName,
            String customerEmail,
            String customerPhone,
            String deliveryZipCode,
            String deliveryStreet,
            String deliveryNumber,
            String deliveryNeighborhood,
            String deliveryState,
            String deliveryCity,
            String deliveryComplement,
            String note
    ) {
        if (saleDate == null) {
            throw new IllegalArgumentException("saleDate is required");
        }

        if (customerType == null) {
            customerType = SalesCustomerType.GUEST;
        }

        this.saleDate = saleDate;
        this.customerType = customerType;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.deliveryZipCode = deliveryZipCode;
        this.deliveryStreet = deliveryStreet;
        this.deliveryNumber = deliveryNumber;
        this.deliveryNeighborhood = deliveryNeighborhood;
        this.deliveryState = deliveryState;
        this.deliveryCity = deliveryCity;
        this.deliveryComplement = deliveryComplement;
        this.note = note;
        this.paymentStatus = SalesPaymentStatus.PENDING;
    }

    public static SalesOrder create(
            LocalDate saleDate,
            SalesCustomerType customerType,
            String customerName,
            String customerEmail,
            String customerPhone,
            String deliveryZipCode,
            String deliveryStreet,
            String deliveryNumber,
            String deliveryNeighborhood,
            String deliveryState,
            String deliveryCity,
            String deliveryComplement,
            String note,
            List<SalesOrderItemData> itemsData
    ) {
        if (itemsData == null || itemsData.isEmpty()) {
            throw new IllegalArgumentException("sale must have at least one item");
        }

        SalesOrder salesOrder = new SalesOrder(
                saleDate,
                customerType,
                customerName,
                customerEmail,
                customerPhone,
                deliveryZipCode,
                deliveryStreet,
                deliveryNumber,
                deliveryNeighborhood,
                deliveryState,
                deliveryCity,
                deliveryComplement,
                note
        );

        itemsData.forEach(itemData -> salesOrder.items.add(
                SalesOrderItem.create(
                        salesOrder,
                        itemData.item(),
                        itemData.quantity(),
                        itemData.unitPrice(),
                        itemData.unitPricePf(),
                        itemData.unitPricePj(),
                        itemData.unitCost(),
                        itemData.costIncomplete()
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

    public SalesCustomerType getCustomerType() { return customerType; }

    public String getCustomerEmail() { return customerEmail; }

    public String getCustomerPhone() { return customerPhone; }

    public String getDeliveryZipCode() { return deliveryZipCode; }

    public String getDeliveryStreet() { return deliveryStreet; }

    public String getDeliveryNumber() { return deliveryNumber; }

    public String getDeliveryNeighborhood() { return deliveryNeighborhood; }

    public String getDeliveryState() { return deliveryState; }

    public String getDeliveryCity() { return deliveryCity; }

    public String getDeliveryComplement() { return deliveryComplement; }

    public String getNote() { return note; }

    public SalesPaymentStatus getPaymentStatus() { return paymentStatus; }

    public String getPaymentProvider() { return paymentProvider; }

    public String getPaymentProviderTransactionId() { return paymentProviderTransactionId; }

    public String getPaymentProviderStatus() { return paymentProviderStatus; }

    public String getPaymentReceiptUrl() { return paymentReceiptUrl; }

    public String getPaymentCheckoutUrl() { return paymentCheckoutUrl; }

    public String getPaymentInvoiceSlug() { return paymentInvoiceSlug; }

    public LocalDateTime getPaidAt() { return paidAt; }

    public BigDecimal getTotalAmount() { return totalAmount; }

    public BigDecimal getTotalCost() { return totalCost; }

    public BigDecimal getGrossProfit() { return grossProfit; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public List<SalesOrderItem> getItems() { return Collections.unmodifiableList(items); }

    public boolean isPaymentFinalized() {
        return paymentStatus != SalesPaymentStatus.PENDING;
    }

    public void updatePayment(
            SalesPaymentStatus paymentStatus,
            String paymentProvider,
            String paymentProviderTransactionId,
            String paymentProviderStatus,
            String paymentReceiptUrl,
            String paymentCheckoutUrl,
            String paymentInvoiceSlug,
            LocalDateTime paidAt
    ) {
        if (paymentStatus == null) {
            throw new IllegalArgumentException("paymentStatus is required");
        }

        this.paymentStatus = paymentStatus;
        this.paymentProvider = paymentProvider;
        this.paymentProviderTransactionId = paymentProviderTransactionId;
        this.paymentProviderStatus = paymentProviderStatus;
        this.paymentReceiptUrl = paymentReceiptUrl;
        this.paymentCheckoutUrl = paymentCheckoutUrl;
        this.paymentInvoiceSlug = paymentInvoiceSlug;

        if (paidAt != null) {
            this.paidAt = paidAt;
        }
    }

    public void updateCheckoutMetadata(String paymentCheckoutUrl, String paymentInvoiceSlug) {
        this.paymentCheckoutUrl = paymentCheckoutUrl;
        this.paymentInvoiceSlug = paymentInvoiceSlug;
    }
}
