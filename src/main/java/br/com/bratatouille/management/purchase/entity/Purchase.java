package br.com.bratatouille.management.purchase.entity;

import br.com.bratatouille.management.partner.entity.Partner;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "paid_by_partner_id")
    private Partner paidBy;

    private BigDecimal totalAmount;

    private String note;

    @CreationTimestamp
    private LocalDateTime createdAt;
}