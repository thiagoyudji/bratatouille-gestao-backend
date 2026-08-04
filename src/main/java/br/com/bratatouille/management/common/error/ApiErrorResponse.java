package br.com.bratatouille.management.common.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "ApiErrorResponse")
public record ApiErrorResponse(
        @Schema(example = "2026-08-04T10:15:30-03:00")
        OffsetDateTime timestamp,
        @Schema(example = "400")
        int status,
        @Schema(example = "Requisição inválida")
        String error,
        @Schema(example = "INVALID_REQUEST")
        String code,
        @Schema(example = "A requisição contém dados inválidos.")
        String message,
        @Schema(example = "/api/items")
        String path,
        @Schema(example = "POST")
        String method,
        @Schema(example = "7d8f0f2d8d0a4d1a9c7e8c5b3d2a1f0e")
        String traceId,
        List<ApiFieldErrorResponse> fieldErrors
) {
}
