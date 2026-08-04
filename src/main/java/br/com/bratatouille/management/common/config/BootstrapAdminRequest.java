// TODO move this class to src/main/java/br/com/bratatouille/management/auth/dto/BootstrapAdminRequest.java when directory creation is available.
package br.com.bratatouille.management.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BootstrapAdminRequest(
        @NotBlank @Size(min = 3, max = 80) String username,
        @NotBlank @Size(min = 8, max = 72) String password
) {
}
