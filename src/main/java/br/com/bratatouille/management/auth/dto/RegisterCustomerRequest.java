package br.com.bratatouille.management.auth.dto;

import br.com.bratatouille.management.customer.dto.CustomerProfileRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterCustomerRequest(
        @NotBlank @Size(min = 3, max = 80) String username,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotNull CustomerProfileRequest profile
) {
}
