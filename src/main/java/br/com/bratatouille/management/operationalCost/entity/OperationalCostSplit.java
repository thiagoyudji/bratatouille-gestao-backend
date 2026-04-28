package br.com.bratatouille.management.operationalCost.entity;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.partner.entity.Partner;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "operational_cost_splits")
public class OperationalCostSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operational_cost_id", nullable = false)
    private OperationalCost operationalCost;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal owedAmount;

    protected OperationalCostSplit() {
    }

    private OperationalCostSplit(
            OperationalCost operationalCost,
            Partner partner,
            BigDecimal owedAmount
    ) {
        validate(operationalCost, partner, owedAmount);

        this.operationalCost = operationalCost;
        this.partner = partner;
        this.owedAmount = MoneyUtils.normalize(owedAmount);
    }

    public static OperationalCostSplit create(
            OperationalCost operationalCost,
            Partner partner,
            BigDecimal owedAmount
    ) {
        return new OperationalCostSplit(operationalCost, partner, owedAmount);
    }

    private static void validate(
            OperationalCost operationalCost,
            Partner partner,
            BigDecimal owedAmount
    ) {
        if (operationalCost == null) {
            throw new IllegalArgumentException("operationalCost is required");
        }

        if (partner == null) {
            throw new IllegalArgumentException("partner is required");
        }

        if (owedAmount == null || owedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("owedAmount must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public OperationalCost getOperationalCost() {
        return operationalCost;
    }

    public Partner getPartner() {
        return partner;
    }

    public BigDecimal getOwedAmount() {
        return owedAmount;
    }
}