package br.com.bratatouille.management.auth.dto;

import br.com.bratatouille.management.auth.entity.UserRole;

import java.time.Instant;

public record AuthResponse(
        String token,
        String tokenType,
        String username,
        UserRole role,
        Instant expiresAt
) {
}
