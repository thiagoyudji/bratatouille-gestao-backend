package br.com.bratatouille.management.partner.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean active;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal defaultSplitPercentage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "partner_roles",
            joinColumns = @JoinColumn(name = "partner_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<PartnerRole> roles = new HashSet<>();

    public Partner() {
    }

    public Partner(
            String name,
            Boolean active,
            BigDecimal defaultSplitPercentage,
            LocalDateTime createdAt,
            Set<PartnerRole> roles
    ) {
        validate(name, defaultSplitPercentage);

        this.name = name;
        this.active = active;
        this.defaultSplitPercentage = defaultSplitPercentage;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    private static void validate(String name, BigDecimal defaultSplitPercentage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("partner name is required");
        }

        if (defaultSplitPercentage == null || defaultSplitPercentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("defaultSplitPercentage must be zero or greater");
        }

        if (defaultSplitPercentage.compareTo(new BigDecimal("100.00")) > 0) {
            throw new IllegalArgumentException("defaultSplitPercentage cannot be greater than 100");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public BigDecimal getDefaultSplitPercentage() {
        return defaultSplitPercentage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<PartnerRole> getRoles() {
        return roles;
    }
}