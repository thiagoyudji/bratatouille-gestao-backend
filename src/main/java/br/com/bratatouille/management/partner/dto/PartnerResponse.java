package br.com.bratatouille.management.partner.dto;

import br.com.bratatouille.management.partner.entity.PartnerRole;

import java.util.Set;

import br.com.bratatouille.management.partner.entity.Partner;
import java.time.LocalDateTime;

public record PartnerResponse(
        Long id,
        String name,
        Boolean active,
        LocalDateTime createdAt,
        Set<PartnerRole> roles
) {
    public static PartnerResponse from(Partner partner) {
        return new PartnerResponse(
                partner.getId(),
                partner.getName(),
                partner.getActive(),
                partner.getCreatedAt(),
                partner.getRoles()
        );
    }
}