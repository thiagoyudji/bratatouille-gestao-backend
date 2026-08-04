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
import br.com.bratatouille.management.common.error.ApiErrorCode;
import br.com.bratatouille.management.common.error.AuthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import br.com.bratatouille.management.support.builder.AuthUserBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void bootstrapAdminCreatesFirstAdminUser() {
        Instant expiresAt = Instant.parse("2026-08-04T12:00:00Z");

        when(authUserRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode("secret123")).thenReturn("hashed-secret");
        when(jwtService.generateToken(any(AuthUser.class))).thenReturn("jwt-token");
        when(jwtService.extractExpiresAt("jwt-token")).thenReturn(expiresAt);
        when(authUserRepository.save(any(AuthUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthResponse response = authService.bootstrapAdmin(
                new BootstrapAdminRequest("admin", "secret123")
        );

        ArgumentCaptor<AuthUser> captor = ArgumentCaptor.forClass(AuthUser.class);
        verify(authUserRepository).save(captor.capture());

        AuthUser saved = captor.getValue();
        assertEquals("admin", saved.getUsername());
        assertEquals("hashed-secret", saved.getPasswordHash());
        assertEquals(UserRole.ADMIN, saved.getRole());
        assertTrue(Boolean.TRUE.equals(saved.getActive()));

        assertEquals("jwt-token", response.token());
        assertEquals("Bearer", response.tokenType());
        assertEquals("admin", response.username());
        assertEquals(UserRole.ADMIN, response.role());
        assertEquals(expiresAt, response.expiresAt());
    }

    @Test
    void bootstrapAdminRejectsWhenAlreadyInitialized() {
        when(authUserRepository.count()).thenReturn(1L);

        AuthException exception = assertThrows(
                AuthException.class,
                () -> authService.bootstrapAdmin(new BootstrapAdminRequest("admin", "secret123"))
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals(ApiErrorCode.AUTH_BOOTSTRAP_ALREADY_COMPLETED, exception.getCode());
        assertEquals("bootstrap already completed", exception.getMessage());
    }

    @Test
    void registerCustomerRejectsDuplicateUsername() {
        when(authUserRepository.existsByUsername("customer")).thenReturn(true);

        AuthException exception = assertThrows(
                AuthException.class,
                () -> authService.registerCustomer(new RegisterCustomerRequest("customer", "secret123"))
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals(ApiErrorCode.AUTH_USERNAME_ALREADY_EXISTS, exception.getCode());
        assertEquals("username already exists", exception.getMessage());
        verify(authUserRepository, never()).save(any());
    }

    @Test
    void createDashboardUserRejectsCustomerRole() {
        AuthException exception = assertThrows(
                AuthException.class,
                () -> authService.createDashboardUser(
                        new CreateDashboardUserRequest("employee", "secret123", UserRole.CUSTOMER)
                )
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals(ApiErrorCode.AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE, exception.getCode());
        assertEquals("customer role must be created via ecommerce", exception.getMessage());
    }

    @Test
    void loginDashboardRejectsCustomerUser() {
        AuthUser user = new AuthUserBuilder()
                .withUsername("customer")
                .withPasswordHash("hashed-secret")
                .withRole(UserRole.CUSTOMER)
                .build();

        when(authUserRepository.findByUsername("customer")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret123", "hashed-secret")).thenReturn(true);

        AuthException exception = assertThrows(
                AuthException.class,
                () -> authService.loginDashboard(new LoginRequest("customer", "secret123"))
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        assertEquals(ApiErrorCode.AUTH_LOGIN_NOT_ALLOWED, exception.getCode());
        assertEquals("user is not allowed to login here", exception.getMessage());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void loginEcommerceRejectsInactiveUser() {
        AuthUser user = new AuthUserBuilder()
                .withUsername("customer")
                .withPasswordHash("hashed-secret")
                .withRole(UserRole.CUSTOMER)
                .inactive()
                .build();

        when(authUserRepository.findByUsername("customer")).thenReturn(Optional.of(user));

        AuthException exception = assertThrows(
                AuthException.class,
                () -> authService.loginEcommerce(new LoginRequest("customer", "secret123"))
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals(ApiErrorCode.AUTH_USER_INACTIVE, exception.getCode());
        assertEquals("user is inactive", exception.getMessage());
        verify(passwordEncoder, never()).matches(any(), any());
    }
}
