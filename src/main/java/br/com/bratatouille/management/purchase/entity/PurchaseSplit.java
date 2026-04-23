package br.com.bratatouille.management.purchase.entity;

import br.com.bratatouille.management.partner.entity.Partner;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_splits")
public class PurchaseSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Purchase purchase;

    @ManyToOne
    private Partner partner;

    private BigDecimal owedAmount;
}