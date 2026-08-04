package br.com.bratatouille.management.common.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    private final ApiErrorResponseFactory factory;
    private final ApiErrorResponseWriter writer;

    public ApiAccessDeniedHandler(ApiErrorResponseFactory factory, ApiErrorResponseWriter writer) {
        this.factory = factory;
        this.writer = writer;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        writer.write(
                response,
                factory.create(
                        request,
                        HttpStatus.FORBIDDEN,
                        ApiErrorCode.FORBIDDEN,
                        accessDeniedException.getMessage(),
                        null
                )
        );
    }
}
