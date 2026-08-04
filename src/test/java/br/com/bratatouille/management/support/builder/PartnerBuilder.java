package br.com.bratatouille.management.support.builder;

import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PartnerBuilder {

    private Long id = 1L;
    private String name = "Partner";
    private boolean active = true;
    private BigDecimal defaultSplitPercentage = new BigDecimal("0.00");
    private LocalDateTime createdAt = LocalDateTime.of(2026, 8, 4, 0, 0);
    private Set<PartnerRole> roles = new HashSet<>(Set.of(PartnerRole.ADMIN));

    public PartnerBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PartnerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PartnerBuilder inactive() {
        this.active = false;
        return this;
    }

    public PartnerBuilder withActive(boolean active) {
        this.active = active;
        return this;
    }

    public PartnerBuilder withDefaultSplitPercentage(BigDecimal defaultSplitPercentage) {
        this.defaultSplitPercentage = defaultSplitPercentage;
        return this;
    }

    public PartnerBuilder withRoles(Set<PartnerRole> roles) {
        this.roles = new HashSet<>(roles);
        return this;
    }

    public Partner build() {
        Partner partner = new Partner(name, active, defaultSplitPercentage, createdAt, roles);
        ReflectionTestUtils.setField(partner, "id", id);
        return partner;
    }
}
