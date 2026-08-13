package br.com.bratatouille.management.customer.service;

import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.customer.dto.CustomerAddressRequest;
import br.com.bratatouille.management.customer.dto.CustomerProfileRequest;
import br.com.bratatouille.management.customer.dto.CustomerProfileResponse;
import br.com.bratatouille.management.customer.entity.CustomerAddress;
import br.com.bratatouille.management.customer.entity.CustomerProfile;
import br.com.bratatouille.management.customer.mapper.CustomerProfileMapper;
import br.com.bratatouille.management.customer.repository.CustomerProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerProfileService {

    private final AuthUserRepository authUserRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerProfileMapper customerProfileMapper;

    public CustomerProfileService(
            AuthUserRepository authUserRepository,
            CustomerProfileRepository customerProfileRepository,
            CustomerProfileMapper customerProfileMapper
    ) {
        this.authUserRepository = authUserRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.customerProfileMapper = customerProfileMapper;
    }

    @Transactional(readOnly = true)
    public CustomerProfileResponse getMyProfile() {
        return customerProfileMapper.toResponse(findCurrentProfile());
    }

    @Transactional(readOnly = true)
    public Optional<CustomerProfileResponse> findAuthenticatedProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || authentication instanceof AnonymousAuthenticationToken
                || !authentication.isAuthenticated()
                || authentication.getName() == null
                || authentication.getName().isBlank()) {
            return Optional.empty();
        }

        return customerProfileRepository.findByAuthUserUsername(authentication.getName())
                .map(customerProfileMapper::toResponse);
    }

    @Transactional
    public CustomerProfileResponse updateMyProfile(CustomerProfileRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("customer profile is required");
        }

        if (request.addresses() == null || request.addresses().isEmpty()) {
            throw new IllegalArgumentException("customer profile must have at least one address");
        }

        CustomerProfile profile = findCurrentProfile();
        profile.update(
                request.customerType(),
                request.fullName(),
                request.email(),
                request.phone(),
                request.addresses().stream().map(this::toAddress).toList()
        );

        return customerProfileMapper.toResponse(profile);
    }

    private CustomerProfile findCurrentProfile() {
        String username = currentUsername();

        return customerProfileRepository.findByAuthUserUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Customer profile not found"));
    }

    private String currentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().isBlank()) {
            throw new IllegalStateException("authenticated user is required");
        }

        return authentication.getName();
    }

    private CustomerAddress toAddress(CustomerAddressRequest request) {
        return new CustomerAddress(
                request.label(),
                request.zipCode(),
                request.street(),
                request.number(),
                request.neighborhood(),
                request.state(),
                request.city(),
                request.complement(),
                request.defaultAddress()
        );
    }
}
