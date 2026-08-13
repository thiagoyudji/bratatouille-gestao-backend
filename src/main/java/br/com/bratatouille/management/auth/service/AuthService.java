package br.com.bratatouille.management.auth.service;

import br.com.bratatouille.management.auth.dto.AuthResponse;
import br.com.bratatouille.management.auth.dto.BootstrapAdminRequest;
import br.com.bratatouille.management.auth.dto.CreateDashboardUserRequest;
import br.com.bratatouille.management.auth.dto.LoginRequest;
import br.com.bratatouille.management.auth.dto.RegisterCustomerRequest;
import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.auth.security.JwtService;
import br.com.bratatouille.management.customer.dto.CustomerAddressRequest;
import br.com.bratatouille.management.customer.dto.CustomerProfileRequest;
import br.com.bratatouille.management.customer.entity.CustomerAddress;
import br.com.bratatouille.management.customer.entity.CustomerProfile;
import br.com.bratatouille.management.customer.entity.CustomerType;
import br.com.bratatouille.management.customer.repository.CustomerProfileRepository;
import br.com.bratatouille.management.common.error.ApiErrorCode;
import br.com.bratatouille.management.common.error.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.EnumSet;
import java.util.Set;

@Service
public class AuthService {

    private static final EnumSet<UserRole> DASHBOARD_ROLES = EnumSet.of(UserRole.ADMIN, UserRole.EMPLOYEE);

    private final AuthUserRepository authUserRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AuthUserRepository authUserRepository,
            CustomerProfileRepository customerProfileRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authUserRepository = authUserRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse bootstrapAdmin(BootstrapAdminRequest request) {
        if (authUserRepository.count() > 0) {
            throw new AuthException(
                    HttpStatus.CONFLICT,
                    ApiErrorCode.AUTH_BOOTSTRAP_ALREADY_COMPLETED,
                    "bootstrap already completed"
            );
        }

        AuthUser user = new AuthUser(
                request.username(),
                passwordEncoder.encode(request.password()),
                UserRole.ADMIN
        );

        return toResponse(authUserRepository.save(user));
    }

    public AuthResponse loginDashboard(LoginRequest request) {
        return authenticate(request, DASHBOARD_ROLES);
    }

    public AuthResponse loginEcommerce(LoginRequest request) {
        return authenticate(request, Set.of(UserRole.CUSTOMER));
    }

    @Transactional
    public AuthResponse registerCustomer(RegisterCustomerRequest request) {
        if (authUserRepository.existsByUsername(request.username())) {
            throw new AuthException(
                    HttpStatus.CONFLICT,
                    ApiErrorCode.AUTH_USERNAME_ALREADY_EXISTS,
                    "username already exists"
            );
        }

        AuthUser user = new AuthUser(
                request.username(),
                passwordEncoder.encode(request.password()),
                UserRole.CUSTOMER
        );

        AuthUser savedUser = authUserRepository.save(user);
        createCustomerProfile(savedUser, request.profile(), CustomerType.PF);

        return toResponse(savedUser);
    }

    @Transactional
    public AuthResponse createDashboardUser(CreateDashboardUserRequest request) {
        if (authUserRepository.existsByUsername(request.username())) {
            throw new AuthException(
                    HttpStatus.CONFLICT,
                    ApiErrorCode.AUTH_USERNAME_ALREADY_EXISTS,
                    "username already exists"
            );
        }

        AuthUser user = new AuthUser(
                request.username(),
                passwordEncoder.encode(request.password()),
                request.role()
        );

        AuthUser savedUser = authUserRepository.save(user);

        if (request.role() == UserRole.CUSTOMER) {
            createCustomerProfile(savedUser, request.profile(), CustomerType.PJ);
        }

        return toResponse(savedUser);
    }

    private AuthResponse authenticate(LoginRequest request, Set<UserRole> allowedRoles) {
        AuthUser user = authUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new AuthException(
                        HttpStatus.UNAUTHORIZED,
                        ApiErrorCode.AUTH_INVALID_CREDENTIALS,
                        "invalid credentials"
                ));

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new AuthException(
                    HttpStatus.UNAUTHORIZED,
                    ApiErrorCode.AUTH_USER_INACTIVE,
                    "user is inactive"
            );
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new AuthException(
                    HttpStatus.UNAUTHORIZED,
                    ApiErrorCode.AUTH_INVALID_CREDENTIALS,
                    "invalid credentials"
            );
        }

        if (!allowedRoles.contains(user.getRole())) {
            throw new AuthException(
                    HttpStatus.FORBIDDEN,
                    ApiErrorCode.AUTH_LOGIN_NOT_ALLOWED,
                    "user is not allowed to login here"
            );
        }

        return toResponse(user);
    }

    private AuthResponse toResponse(AuthUser user) {
        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                "Bearer",
                user.getUsername(),
                user.getRole(),
                jwtService.extractExpiresAt(token)
        );
    }

    private void createCustomerProfile(AuthUser user, CustomerProfileRequest profileRequest, CustomerType expectedType) {
        if (profileRequest == null) {
            throw new AuthException(
                    HttpStatus.BAD_REQUEST,
                    ApiErrorCode.INVALID_REQUEST,
                    "customer profile is required"
            );
        }

        if (profileRequest.customerType() != expectedType) {
            throw new AuthException(
                    HttpStatus.BAD_REQUEST,
                    ApiErrorCode.INVALID_REQUEST,
                    "customer type is invalid for this registration flow"
            );
        }

        if (profileRequest.addresses() == null || profileRequest.addresses().isEmpty()) {
            throw new AuthException(
                    HttpStatus.BAD_REQUEST,
                    ApiErrorCode.INVALID_REQUEST,
                    "customer profile must have at least one address"
            );
        }

        CustomerProfile profile = new CustomerProfile(
                user,
                expectedType,
                profileRequest.fullName(),
                profileRequest.email(),
                profileRequest.phone(),
                profileRequest.addresses().stream().map(this::toAddress).toList()
        );

        customerProfileRepository.save(profile);
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
