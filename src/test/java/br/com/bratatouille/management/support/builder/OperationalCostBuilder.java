package br.com.bratatouille.management.support.builder;

import br.com.bratatouille.management.operationalCost.domain.OperationalCostSplitData;
import br.com.bratatouille.management.operationalCost.entity.OperationalCost;
import br.com.bratatouille.management.operationalCost.entity.OperationalCostCategory;
import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperationalCostBuilder {

    private LocalDate costDate = LocalDate.of(2026, 8, 1);
    private OperationalCostCategory category = OperationalCostCategory.FIXED;
    private Partner paidBy = new PartnerBuilder().withId(1L).withName("Main Partner").build();
    private BigDecimal amount = new BigDecimal("100.00");
    private String description = "cost";
    private final List<OperationalCostSplitData> splits = new ArrayList<>();

    public OperationalCostBuilder withCostDate(LocalDate costDate) {
        this.costDate = costDate;
        return this;
    }

    public OperationalCostBuilder withCategory(OperationalCostCategory category) {
        this.category = category;
        return this;
    }

    public OperationalCostBuilder withPaidBy(Partner paidBy) {
        this.paidBy = paidBy;
        return this;
    }

    public OperationalCostBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public OperationalCostBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public OperationalCostBuilder addSplit(Partner partner, BigDecimal amount) {
        splits.add(new OperationalCostSplitData(partner, amount));
        return this;
    }

    public OperationalCostBuilder withSplits(List<OperationalCostSplitData> splits) {
        this.splits.clear();
        this.splits.addAll(splits);
        return this;
    }

    public OperationalCost build() {
        return OperationalCost.create(costDate, category, paidBy, amount, description, List.copyOf(splits));
    }
}
