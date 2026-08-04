package br.com.bratatouille.management.common.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
public class ApiErrorResponseFactory {

    private static final ZoneId TIME_ZONE = ZoneId.of("America/Sao_Paulo");

    public ApiErrorResponse create(
            HttpServletRequest request,
            HttpStatus status,
            ApiErrorCode code,
            String message,
            List<ApiFieldErrorResponse> fieldErrors
    ) {
        return new ApiErrorResponse(
                OffsetDateTime.now(TIME_ZONE),
                status.value(),
                resolveErrorLabel(code),
                code.name(),
                resolveMessage(status, code, message),
                request.getRequestURI(),
                request.getMethod(),
                resolveTraceId(request),
                fieldErrors
        );
    }

    public ApiErrorResponse create(
            HttpServletRequest request,
            ResponseStatusException exception
    ) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        return create(request, status, codeFor(status), exception.getReason(), null);
    }

    public ApiFieldErrorResponse fieldError(String field, String code, String message) {
        return new ApiFieldErrorResponse(field, code, translateFieldMessage(field, code, message));
    }

    public ApiFieldErrorResponse translatedFieldError(String field, String code, String message) {
        return new ApiFieldErrorResponse(field, code, message);
    }

    public String translateGeneralMessage(String rawMessage) {
        if (!StringUtils.hasText(rawMessage)) {
            return "A requisição não pôde ser processada.";
        }

        String normalized = rawMessage.trim().toLowerCase(Locale.ROOT);

        if (normalized.contains("invalid credentials")) {
            return "Credenciais inválidas.";
        }

        if (normalized.contains("user is inactive")) {
            return "Usuário inativo.";
        }

        if (normalized.contains("user is not allowed to login here")) {
            return "Usuário não tem permissão para acessar este login.";
        }

        if (normalized.contains("bootstrap already completed")) {
            return "O bootstrap do administrador já foi concluído.";
        }

        if (normalized.contains("customer role must be created via ecommerce")) {
            return "O papel de cliente deve ser criado pelo fluxo de e-commerce.";
        }

        if (normalized.contains("username already exists")) {
            return "Já existe um usuário com esse nome.";
        }

        if (normalized.contains("not found")) {
            return "Registro não encontrado.";
        }

        if (normalized.contains("insufficient stock")) {
            return "Estoque insuficiente.";
        }

        if (normalized.contains("inactive")) {
            return "Registro inativo.";
        }

        if (normalized.contains("already closed")) {
            return "O período já foi encerrado.";
        }

        if (normalized.contains("closed period")) {
            return "Não é permitido alterar dados de um período encerrado.";
        }

        if (normalized.contains("is required")) {
            return translateRequiredMessage(normalized);
        }

        if (normalized.contains("must be greater than zero")) {
            return translateGreaterThanZero(normalized);
        }

        if (normalized.contains("cannot be negative")) {
            return translateCannotBeNegative(normalized);
        }

        if (normalized.contains("cannot be greater than")) {
            return translateCannotBeGreaterThan(normalized);
        }

        if (normalized.contains("must have at least one")) {
            return "O registro deve conter ao menos um item.";
        }

        if (normalized.contains("cannot have duplicated")) {
            return "O registro não pode conter itens duplicados.";
        }

        if (normalized.contains("invalid period")) {
            return "Período inválido.";
        }

        if (normalized.contains("startdate cannot be after enddate")) {
            return "A data inicial não pode ser posterior à data final.";
        }

        if (normalized.contains("serialization error") || normalized.contains("deserialization error")) {
            return "Falha ao processar os dados da resposta.";
        }

        return "A requisição contém dados inválidos.";
    }

    private String resolveMessage(HttpStatus status, ApiErrorCode code, String message) {
        if (code == ApiErrorCode.VALIDATION_ERROR && StringUtils.hasText(message)) {
            return message;
        }

        if (StringUtils.hasText(message)) {
            return translateGeneralMessage(message);
        }

        return switch (code) {
            case VALIDATION_ERROR -> "Existem campos inválidos na requisição.";
            case INVALID_REQUEST -> "A requisição contém dados inválidos.";
            case BUSINESS_RULE_VIOLATION -> "A operação viola uma regra de negócio.";
            case NOT_FOUND -> "Registro não encontrado.";
            case CONFLICT -> "O estado atual do recurso impede esta operação.";
            case UNAUTHORIZED -> "Autenticação necessária.";
            case FORBIDDEN -> "Você não tem permissão para executar esta operação.";
            case AUTH_INVALID_CREDENTIALS -> "Credenciais inválidas.";
            case AUTH_USER_INACTIVE -> "Usuário inativo.";
            case AUTH_LOGIN_NOT_ALLOWED -> "Usuário não tem permissão para acessar este login.";
            case AUTH_BOOTSTRAP_ALREADY_COMPLETED -> "O bootstrap do administrador já foi concluído.";
            case AUTH_USERNAME_ALREADY_EXISTS -> "Já existe um usuário com esse nome.";
            case AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE -> "O papel de cliente deve ser criado pelo fluxo de e-commerce.";
            case INTERNAL_SERVER_ERROR -> "Ocorreu um erro inesperado.";
        };
    }

    private String resolveErrorLabel(ApiErrorCode code) {
        return switch (code) {
            case VALIDATION_ERROR -> "Validação";
            case INVALID_REQUEST -> "Requisição inválida";
            case BUSINESS_RULE_VIOLATION -> "Regra de negócio";
            case NOT_FOUND -> "Não encontrado";
            case CONFLICT -> "Conflito";
            case UNAUTHORIZED -> "Não autorizado";
            case FORBIDDEN -> "Sem permissão";
            case AUTH_INVALID_CREDENTIALS, AUTH_USER_INACTIVE, AUTH_LOGIN_NOT_ALLOWED, AUTH_BOOTSTRAP_ALREADY_COMPLETED, AUTH_USERNAME_ALREADY_EXISTS, AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE -> "Autenticação";
            case INTERNAL_SERVER_ERROR -> "Erro interno";
        };
    }

    private ApiErrorCode codeFor(HttpStatus status) {
        return switch (status) {
            case BAD_REQUEST -> ApiErrorCode.INVALID_REQUEST;
            case UNAUTHORIZED -> ApiErrorCode.UNAUTHORIZED;
            case FORBIDDEN -> ApiErrorCode.FORBIDDEN;
            case NOT_FOUND -> ApiErrorCode.NOT_FOUND;
            case CONFLICT -> ApiErrorCode.CONFLICT;
            case UNPROCESSABLE_ENTITY -> ApiErrorCode.BUSINESS_RULE_VIOLATION;
            default -> ApiErrorCode.INTERNAL_SERVER_ERROR;
        };
    }

    private String resolveTraceId(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-Id");
        if (StringUtils.hasText(requestId)) {
            return requestId;
        }

        String correlationId = request.getHeader("X-Correlation-Id");
        if (StringUtils.hasText(correlationId)) {
            return correlationId;
        }

        return UUID.randomUUID().toString().replace("-", "");
    }

    private String translateFieldMessage(String field, String code, String defaultMessage) {
        String normalizedCode = code == null ? "" : code.toLowerCase(Locale.ROOT);
        String normalizedMessage = defaultMessage == null ? "" : defaultMessage.trim().toLowerCase(Locale.ROOT);

        if (normalizedCode.contains("notblank") || normalizedCode.contains("notempty") || normalizedCode.contains("notnull")) {
            return "O campo '" + field + "' é obrigatório.";
        }

        if (normalizedCode.contains("size")) {
            return "O campo '" + field + "' possui tamanho inválido.";
        }

        if (normalizedCode.contains("positive") || normalizedCode.contains("positiveorzero")) {
            return "O campo '" + field + "' deve ser maior que zero.";
        }

        if (normalizedCode.contains("min") || normalizedCode.contains("max")) {
            return "O campo '" + field + "' está fora do intervalo permitido.";
        }

        if (normalizedCode.contains("email")) {
            return "O campo '" + field + "' deve ser um e-mail válido.";
        }

        if (normalizedCode.contains("pattern")) {
            return "O campo '" + field + "' possui formato inválido.";
        }

        if (normalizedMessage.contains("must not be blank") || normalizedMessage.contains("must not be null")) {
            return "O campo '" + field + "' é obrigatório.";
        }

        if (normalizedMessage.contains("must be greater than")) {
            return "O campo '" + field + "' deve ser maior que zero.";
        }

        if (normalizedMessage.contains("must be greater than or equal to")) {
            return "O campo '" + field + "' não pode ser negativo.";
        }

        if (normalizedMessage.contains("must be less than or equal to")) {
            return "O campo '" + field + "' está acima do limite permitido.";
        }

        if (StringUtils.hasText(defaultMessage)) {
            return translateGeneralMessage(defaultMessage);
        }

        return "O campo '" + field + "' é inválido.";
    }

    private String translateRequiredMessage(String message) {
        String field = extractField(message);
        if (!StringUtils.hasText(field)) {
            field = extractPrefix(message, " is required");
        }
        if (StringUtils.hasText(field)) {
            return "O campo '" + field + "' é obrigatório.";
        }
        return "O campo informado é obrigatório.";
    }

    private String translateGreaterThanZero(String message) {
        String field = extractField(message);
        if (!StringUtils.hasText(field)) {
            field = extractPrefix(message, " must be greater than zero");
        }
        if (StringUtils.hasText(field)) {
            return "O campo '" + field + "' deve ser maior que zero.";
        }
        return "O valor informado deve ser maior que zero.";
    }

    private String translateCannotBeNegative(String message) {
        String field = extractField(message);
        if (!StringUtils.hasText(field)) {
            field = extractPrefix(message, " cannot be negative");
        }
        if (StringUtils.hasText(field)) {
            return "O campo '" + field + "' não pode ser negativo.";
        }
        return "O valor informado não pode ser negativo.";
    }

    private String translateCannotBeGreaterThan(String message) {
        String field = extractField(message);
        if (!StringUtils.hasText(field)) {
            field = extractPrefix(message, " cannot be greater than");
        }
        if (StringUtils.hasText(field)) {
            return "O campo '" + field + "' excede o limite permitido.";
        }
        return "O valor informado excede o limite permitido.";
    }

    private String extractPrefix(String message, String suffix) {
        if (!StringUtils.hasText(message) || !StringUtils.hasText(suffix)) {
            return null;
        }

        String normalized = message.trim().toLowerCase(Locale.ROOT);
        int index = normalized.indexOf(suffix);
        if (index > 0) {
            return message.substring(0, index).trim();
        }

        return null;
    }

    private String extractField(String message) {
        if (!StringUtils.hasText(message)) {
            return null;
        }

        int firstQuote = message.indexOf('\'');
        int secondQuote = message.indexOf('\'', firstQuote + 1);
        if (firstQuote >= 0 && secondQuote > firstQuote) {
            return message.substring(firstQuote + 1, secondQuote);
        }

        int colon = message.indexOf(':');
        if (colon > 0) {
            return message.substring(0, colon).trim();
        }

        return null;
    }
}
