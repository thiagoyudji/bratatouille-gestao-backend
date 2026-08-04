package br.com.bratatouille.management.tempbootstrap.controller;

import br.com.bratatouille.management.auth.dto.AuthResponse;
import br.com.bratatouille.management.auth.dto.BootstrapAdminRequest;
import br.com.bratatouille.management.auth.dto.CreateDashboardUserRequest;
import br.com.bratatouille.management.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Temporary bootstrap endpoint. Delete this package after initial user seeding.
@RestController
@RequestMapping("/api/_temp/bootstrap")
public class TempBootstrapController {

    private final AuthService authService;

    public TempBootstrapController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthResponse> bootstrapAdmin(@Valid @RequestBody BootstrapAdminRequest request) {
        return ResponseEntity.ok(authService.bootstrapAdmin(request));
    }

    @PostMapping("/dashboard/users")
    public ResponseEntity<AuthResponse> createDashboardUser(@Valid @RequestBody CreateDashboardUserRequest request) {
        return ResponseEntity.ok(authService.createDashboardUser(request));
    }
}
