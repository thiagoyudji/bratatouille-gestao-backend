package br.com.bratatouille.management.common.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiSecurityErrorHandlersTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final ApiErrorResponseFactory factory = new ApiErrorResponseFactory();
    private final ApiErrorResponseWriter writer = new ApiErrorResponseWriter(objectMapper);
    private final ApiAuthenticationEntryPoint authenticationEntryPoint = new ApiAuthenticationEntryPoint(factory, writer);
    private final ApiAccessDeniedHandler accessDeniedHandler = new ApiAccessDeniedHandler(factory, writer);

    @Test
    void authenticationEntryPointWritesStructuredResponse() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/items");
        MockHttpServletResponse response = new MockHttpServletResponse();

        authenticationEntryPoint.commence(request, response, new BadCredentialsException("invalid credentials"));

        assertEquals(401, response.getStatus());
        ApiErrorResponse error = objectMapper.readValue(response.getContentAsByteArray(), ApiErrorResponse.class);
        assertEquals("UNAUTHORIZED", error.code());
        assertEquals("Credenciais inválidas.", error.message());
        assertEquals("/api/items", error.path());
    }

    @Test
    void accessDeniedHandlerWritesStructuredResponse() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/financial/balances");
        MockHttpServletResponse response = new MockHttpServletResponse();

        accessDeniedHandler.handle(request, response, new AccessDeniedException("user is not allowed to login here"));

        assertEquals(403, response.getStatus());
        ApiErrorResponse error = objectMapper.readValue(response.getContentAsByteArray(), ApiErrorResponse.class);
        assertEquals("FORBIDDEN", error.code());
        assertEquals("Usuário não tem permissão para acessar este login.", error.message());
        assertEquals("/api/financial/balances", error.path());
    }
}
