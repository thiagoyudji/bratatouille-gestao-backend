package br.com.bratatouille.management.partner.service;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.generated.model.CreatePartnerRequest;
import br.com.bratatouille.management.generated.model.PartnerResponse;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PartnerServiceIT {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Test
    void createDefaultsRoleAndFindById() {
        long initialCount = partnerRepository.count();

        CreatePartnerRequest request = new CreatePartnerRequest();
        request.setName("Main Partner");
        request.setDefaultSplitPercentage(new BigDecimal("50.00"));

        PartnerResponse created = partnerService.create(request);

        assertNotNull(created.getId());
        assertEquals("Main Partner", created.getName());
        assertTrue(created.getActive());
        assertEquals(0, new BigDecimal("50.00").compareTo(created.getDefaultSplitPercentage()));
        assertTrue(created.getRoles().contains(PartnerResponse.RolesEnum.VIEWER));

        PartnerResponse found = partnerService.findById(created.getId());
        assertEquals(created.getId(), found.getId());
        assertEquals(initialCount + 1, partnerRepository.count());
    }

    @Test
    void createPersistsExplicitRolesAndRejectsInvalidPercentage() {
        long initialCount = partnerRepository.count();

        CreatePartnerRequest request = new CreatePartnerRequest();
        request.setName("Producer Partner");
        request.setDefaultSplitPercentage(new BigDecimal("25.00"));
        request.setRoles(Set.of(
                CreatePartnerRequest.RolesEnum.ADMIN,
                CreatePartnerRequest.RolesEnum.PRODUCER
        ));

        PartnerResponse created = partnerService.create(request);

        assertEquals(2, created.getRoles().size());
        assertTrue(created.getRoles().contains(PartnerResponse.RolesEnum.ADMIN));
        assertTrue(created.getRoles().contains(PartnerResponse.RolesEnum.PRODUCER));

        CreatePartnerRequest invalid = new CreatePartnerRequest();
        invalid.setName("Invalid Partner");
        invalid.setDefaultSplitPercentage(new BigDecimal("101.00"));

        assertThrows(IllegalArgumentException.class, () -> partnerService.create(invalid));
        assertEquals(initialCount + 1, partnerRepository.count());
    }

    @Test
    void associatesOnlyAdminUserAndCannotBeChanged() {
        PartnerResponse created = partnerService.create(partnerRequest("Associated Partner"));
        AuthUser admin = authUserRepository.save(new AuthUser("partner-admin", "hash", UserRole.ADMIN));

        PartnerResponse associated = partnerService.associateUser(created.getId(), admin.getId());

        assertEquals(admin.getId(), associated.getAuthUserId());
        assertEquals("partner-admin", associated.getAuthUsername());

        AuthUser anotherAdmin = authUserRepository.save(new AuthUser("another-admin", "hash", UserRole.ADMIN));
        assertThrows(ResponseStatusException.class, () -> partnerService.associateUser(created.getId(), anotherAdmin.getId()));
    }

    @Test
    void rejectsEmployeeAndReusingAdminUser() {
        PartnerResponse first = partnerService.create(partnerRequest("First Partner"));
        PartnerResponse second = partnerService.create(partnerRequest("Second Partner"));
        AuthUser employee = authUserRepository.save(new AuthUser("partner-employee", "hash", UserRole.EMPLOYEE));
        AuthUser admin = authUserRepository.save(new AuthUser("shared-admin", "hash", UserRole.ADMIN));

        assertThrows(ResponseStatusException.class, () -> partnerService.associateUser(first.getId(), employee.getId()));
        partnerService.associateUser(first.getId(), admin.getId());
        assertThrows(ResponseStatusException.class, () -> partnerService.associateUser(second.getId(), admin.getId()));
    }

    private CreatePartnerRequest partnerRequest(String name) {
        CreatePartnerRequest request = new CreatePartnerRequest();
        request.setName(name);
        request.setDefaultSplitPercentage(new BigDecimal("50.00"));
        return request;
    }
}
