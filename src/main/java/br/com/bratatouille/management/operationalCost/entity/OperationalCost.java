package br.com.bratatouille.management.operationalCost.entity;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.operationalCost.domain.OperationalCostSplitData;
import br.com.bratatouille.management.partner.entity.Partner;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "operational_costs")
public class OperationalCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate costDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationalCostCategory category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paid_by_partner_id", nullable = false)
    private Partner paidBy;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "operationalCost", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OperationalCostSplit> splits = new ArrayList<>();

    protected OperationalCost() {
    }

    private OperationalCost(
            LocalDate costDate,
            OperationalCostCategory category,
            Partner paidBy,
            BigDecimal amount,
            String description
    ) {
        validateHeader(costDate, category, paidBy, amount);

        this.costDate = costDate;
        this.category = category;
        this.paidBy = paidBy;
        this.amount = MoneyUtils.normalize(amount);
        this.description = description;
    }

    public static OperationalCost create(
            LocalDate costDate,
            OperationalCostCategory category,
            Partner paidBy,
            BigDecimal amount,
            String description,
            List<OperationalCostSplitData> splitsData
    ) {
        validateSplitsData(splitsData);
        validateDuplicatedSplitPartners(splitsData);

        OperationalCost operationalCost = new OperationalCost(
                costDate,
                category,
                paidBy,
                amount,
                description
        );

        operationalCost.addSplits(splitsData);
        operationalCost.validateSplitTotal();

        return operationalCost;
    }

    private void addSplits(List<OperationalCostSplitData> splitsData) {
        splitsData.forEach(splitData -> {
            OperationalCostSplit split = OperationalCostSplit.create(
                    this,
                    splitData.partner(),
                    splitData.amount()
            );

            this.splits.add(split);
        });
    }

    private void validateSplitTotal() {
        BigDecimal splitTotal = MoneyUtils.normalize(
                this.splits.stream()
                        .map(OperationalCostSplit::getOwedAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        if (!MoneyUtils.equals(this.amount, splitTotal)) {
            throw new IllegalArgumentException("split total must be equal to operational cost amount");
        }
    }

    private static void validateHeader(
            LocalDate costDate,
            OperationalCostCategory category,
            Partner paidBy,
            BigDecimal amount
    ) {
        if (costDate == null) {
            throw new IllegalArgumentException("costDate is required");
        }

        if (category == null) {
            throw new IllegalArgumentException("category is required");
        }

        if (paidBy == null) {
            throw new IllegalArgumentException("paidBy is required");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    private static void validateSplitsData(List<OperationalCostSplitData> splitsData) {
        if (splitsData == null || splitsData.isEmpty()) {
            throw new IllegalArgumentException("operational cost must have at least one split");
        }
    }

    private static void validateDuplicatedSplitPartners(List<OperationalCostSplitData> splitsData) {
        long distinctPartners = splitsData.stream()
                .map(split -> split.partner().getId())
                .distinct()
                .count();

        if (distinctPartners != splitsData.size()) {
            throw new IllegalArgumentException("operational cost split cannot have duplicated partners");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCostDate() {
        return costDate;
    }

    public OperationalCostCategory getCategory() {
        return category;
    }

    public Partner getPaidBy() {
        return paidBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OperationalCostSplit> getSplits() {
        return Collections.unmodifiableList(splits);
    }
}