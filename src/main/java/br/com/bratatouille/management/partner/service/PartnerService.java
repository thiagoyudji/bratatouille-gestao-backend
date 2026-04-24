package br.com.bratatouille.management.partner.service;

import br.com.bratatouille.management.partner.dto.CreatePartnerRequest;
import br.com.bratatouille.management.partner.dto.PartnerResponse;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public PartnerResponse create(CreatePartnerRequest request) {
        Set<PartnerRole> roles = request.roles() == null || request.roles().isEmpty()
                ? Set.of(PartnerRole.VIEWER)
                : new HashSet<>(request.roles());

        Partner partner = new Partner(
                request.name(),
                true,
                LocalDateTime.now(),
                roles
        );

        Partner saved = partnerRepository.save(partner);

        return PartnerResponse.from(saved);
    }

    public List<PartnerResponse> findAll() {
        return partnerRepository.findAll()
                .stream()
                .map(PartnerResponse::from)
                .toList();
    }

    public PartnerResponse findById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        return PartnerResponse.from(partner);
    }
}