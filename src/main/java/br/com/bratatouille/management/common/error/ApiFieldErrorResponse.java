package br.com.bratatouille.management.common.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiFieldErrorResponse")
public record ApiFieldErrorResponse(
        @Schema(example = "username")
        String field,
        @Schema(example = "NotBlank")
        String code,
        @Schema(example = "O campo 'username' é obrigatório.")
        String message
) {
}
