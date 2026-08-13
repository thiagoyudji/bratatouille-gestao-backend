package br.com.bratatouille.management.auth.controller;

import br.com.bratatouille.management.auth.dto.AuthResponse;
import br.com.bratatouille.management.auth.dto.BootstrapAdminRequest;
import br.com.bratatouille.management.auth.dto.CreateDashboardUserRequest;
import br.com.bratatouille.management.auth.dto.LoginRequest;
import br.com.bratatouille.management.auth.dto.RegisterCustomerRequest;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.service.AuthService;
import br.com.bratatouille.management.customer.mapper.CustomerContractMapper;
import br.com.bratatouille.management.generated.api.AuthApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthApiDelegateImpl implements AuthApiDelegate {

    private final AuthService authService;
    private final CustomerContractMapper customerContractMapper;

    public AuthApiDelegateImpl(AuthService authService, CustomerContractMapper customerContractMapper) {
        this.authService = authService;
        this.customerContractMapper = customerContractMapper;
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.AuthResponse> bootstrapAdmin(
            br.com.bratatouille.management.generated.model.BootstrapAdminRequest request
    ) {
        return ResponseEntity.ok(toGeneratedResponse(authService.bootstrapAdmin(toDomainRequest(request))));
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.AuthResponse> loginDashboard(
            br.com.bratatouille.management.generated.model.LoginRequest request
    ) {
        return ResponseEntity.ok(toGeneratedResponse(authService.loginDashboard(toDomainRequest(request))));
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.AuthResponse> loginEcommerce(
            br.com.bratatouille.management.generated.model.LoginRequest request
    ) {
        return ResponseEntity.ok(toGeneratedResponse(authService.loginEcommerce(toDomainRequest(request))));
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.AuthResponse> registerCustomer(
            br.com.bratatouille.management.generated.model.RegisterCustomerRequest request
    ) {
        return ResponseEntity.ok(toGeneratedResponse(authService.registerCustomer(toDomainRequest(request))));
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.AuthResponse> createDashboardUser(
            br.com.bratatouille.management.generated.model.CreateDashboardUserRequest request
    ) {
        return ResponseEntity.ok(toGeneratedResponse(authService.createDashboardUser(toDomainRequest(request))));
    }

    private BootstrapAdminRequest toDomainRequest(br.com.bratatouille.management.generated.model.BootstrapAdminRequest request) {
        return new BootstrapAdminRequest(request.getUsername(), request.getPassword());
    }

    private LoginRequest toDomainRequest(br.com.bratatouille.management.generated.model.LoginRequest request) {
        return new LoginRequest(request.getUsername(), request.getPassword());
    }

    private RegisterCustomerRequest toDomainRequest(br.com.bratatouille.management.generated.model.RegisterCustomerRequest request) {
        return new RegisterCustomerRequest(
                request.getUsername(),
                request.getPassword(),
                customerContractMapper.toDomainProfile(request.getProfile())
        );
    }

    private CreateDashboardUserRequest toDomainRequest(br.com.bratatouille.management.generated.model.CreateDashboardUserRequest request) {
        return new CreateDashboardUserRequest(
                request.getUsername(),
                request.getPassword(),
                toDomainRole(request.getRole()),
                customerContractMapper.toDomainProfile(request.getProfile())
        );
    }

    private UserRole toDomainRole(br.com.bratatouille.management.generated.model.CreateDashboardUserRequest.RoleEnum role) {
        return UserRole.valueOf(role.getValue());
    }

    private br.com.bratatouille.management.generated.model.AuthResponse toGeneratedResponse(AuthResponse response) {
        return new br.com.bratatouille.management.generated.model.AuthResponse()
                .token(response.token())
                .tokenType(response.tokenType())
                .username(response.username())
                .role(br.com.bratatouille.management.generated.model.AuthResponse.RoleEnum.fromValue(response.role().name()))
                .expiresAt(response.expiresAt().atOffset(java.time.ZoneOffset.UTC));
    }
}
