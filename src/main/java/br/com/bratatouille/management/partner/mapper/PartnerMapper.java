package br.com.bratatouille.management.partner.mapper;

import br.com.bratatouille.management.generated.model.PartnerResponse;
import br.com.bratatouille.management.partner.entity.Partner;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
public class PartnerMapper {

    public PartnerResponse toResponse(Partner partner) {
        PartnerResponse response = new PartnerResponse();

        response.setId(partner.getId());
        response.setName(partner.getName());
        response.setActive(partner.getActive());
        response.setCreatedAt(partner.getCreatedAt().atOffset(ZoneOffset.UTC));

        response.setRoles(
                partner.getRoles()
                        .stream()
                        .map(role -> PartnerResponse.RolesEnum.valueOf(role.name()))
                        .collect(Collectors.toSet())
        );

        return response;
    }
}