package br.com.bratatouille.management.partner.dto;

import br.com.bratatouille.management.partner.entity.PartnerRole;

import java.util.Set;

public record CreatePartnerRequest(
    String name,
    Set<PartnerRole> roles
) {}