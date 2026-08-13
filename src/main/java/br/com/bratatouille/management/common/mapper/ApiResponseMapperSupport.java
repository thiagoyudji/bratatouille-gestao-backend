package br.com.bratatouille.management.common.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class ApiResponseMapperSupport {

    private ApiResponseMapperSupport() {
    }

    public static OffsetDateTime toUtc(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }
}
