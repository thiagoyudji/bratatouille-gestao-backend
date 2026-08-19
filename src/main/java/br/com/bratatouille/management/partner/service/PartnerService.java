package br.com.bratatouille.management.partner.service;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.generated.model.CreatePartnerRequest;
import br.com.bratatouille.management.generated.model.PartnerResponse;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.mapper.PartnerMapper;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final AuthUserRepository authUserRepository;

    public PartnerService(
            PartnerRepository partnerRepository,
            PartnerMapper partnerMapper,
            AuthUserRepository authUserRepository
    ) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
        this.authUserRepository = authUserRepository;
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
                request.getDefaultSplitPercentage(),
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
                .orElseThrow(() -> new NoSuchElementException("Partner not found"));

        return partnerMapper.toResponse(partner);
    }

    public PartnerResponse associateUser(Long partnerId, Long authUserId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner not found"));

        AuthUser authUser = authUserRepository.findById(authUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dashboard user not found"));

        if (authUser.getRole() != UserRole.ADMIN || !Boolean.TRUE.equals(authUser.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only active ADMIN users can be associated with a partner"
            );
        }

        if (partner.getAuthUser() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Partner is already associated with a dashboard user"
            );
        }

        if (partnerRepository.existsByAuthUserId(authUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Dashboard user is already associated with a partner"
            );
        }

        partner.associateAuthUser(authUser);
        return partnerMapper.toResponse(partnerRepository.save(partner));
    }
}
