package br.com.bratatouille.management.partner.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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

    public Partner(String name, Boolean active, LocalDateTime createdAt, Set<PartnerRole> roles) {
        this.name = name;
        this.active = active;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<PartnerRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PartnerRole> roles) {
        this.roles = roles;
    }
}