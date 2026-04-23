package br.com.bratatouille.management.purchase.entity;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.partner.entity.Partner;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_splits")
public class PurchaseSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal owedAmount;

    protected PurchaseSplit() {
    }

    private PurchaseSplit(Purchase purchase, Partner partner, BigDecimal owedAmount) {
        validate(purchase, partner, owedAmount);

        this.purchase = purchase;
        this.partner = partner;
        this.owedAmount = MoneyUtils.normalize(owedAmount);
    }

    public static PurchaseSplit create(
            Purchase purchase,
            Partner partner,
            BigDecimal owedAmount
    ) {
        return new PurchaseSplit(purchase, partner, owedAmount);
    }

    private static void validate(
            Purchase purchase,
            Partner partner,
            BigDecimal owedAmount
    ) {
        if (purchase == null) {
            throw new IllegalArgumentException("purchase is required");
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

    public Purchase getPurchase() {
        return purchase;
    }

    public Partner getPartner() {
        return partner;
    }

    public BigDecimal getOwedAmount() {
        return owedAmount;
    }
}