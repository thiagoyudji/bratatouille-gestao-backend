package br.com.bratatouille.management.partner.service;

import br.com.bratatouille.management.generated.model.CreatePartnerRequest;
import br.com.bratatouille.management.generated.model.PartnerResponse;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.mapper.PartnerMapper;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    public PartnerService(PartnerRepository partnerRepository,
                          PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    public PartnerResponse create(CreatePartnerRequest request) {
        Set<PartnerRole> roles = request.getRoles() == null || request.getRoles().isEmpty()
                ? Set.of(PartnerRole.VIEWER)
                : request.getRoles()
                .stream()
                .map(role -> PartnerRole.valueOf(role.name()))
                .collect(Collectors.toSet());

        Partner partner = new Partner(
                request.getName(),
                true,
                LocalDateTime.now(),
                roles
        );

        Partner saved = partnerRepository.save(partner);

        return partnerMapper.toResponse(saved);
    }

    public List<PartnerResponse> findAll() {
        return partnerRepository.findAll()
                .stream()
                .map(partnerMapper::toResponse)
                .toList();
    }

    public PartnerResponse findById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        return partnerMapper.toResponse(partner);
    }
}