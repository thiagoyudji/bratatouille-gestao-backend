// TODO move this class to src/main/java/br/com/bratatouille/management/auth/dto/LoginRequest.java when directory creation is available.
package br.com.bratatouille.management.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
