package br.com.bratatouille.management.common.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final ApiErrorResponseFactory factory;

    public ApiExceptionHandler(ApiErrorResponseFactory factory) {
        this.factory = factory;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ApiFieldErrorResponse> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldError)
                .toList();

        return build(
                request,
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                "Existem campos inválidos na requisição.",
                fieldErrors
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiErrorResponse> handleBindException(
            BindException ex,
            HttpServletRequest request
    ) {
        List<ApiFieldErrorResponse> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldError)
                .toList();

        return build(
                request,
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                "Existem campos inválidos na requisição.",
                fieldErrors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<ApiFieldErrorResponse> fieldErrors = ex.getConstraintViolations().stream()
                .map(this::toFieldError)
                .toList();

        return build(
                request,
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                "Existem campos inválidos na requisição.",
                fieldErrors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        log.warn("Corpo da requisição inválido em {} {}", request.getMethod(), request.getRequestURI());
        return build(
                request,
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.INVALID_REQUEST,
                "O corpo da requisição está inválido ou malformado.",
                null
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingRequestParameter(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        List<ApiFieldErrorResponse> fieldErrors = List.of(
                factory.translatedFieldError(
                        ex.getParameterName(),
                        "MissingParameter",
                        "O parâmetro '" + ex.getParameterName() + "' é obrigatório."
                )
        );

        return build(
                request,
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.INVALID_REQUEST,
                "Existem parâmetros obrigatórios ausentes.",
                fieldErrors
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String field = ex.getName();
        List<ApiFieldErrorResponse> fieldErrors = List.of(
                factory.translatedFieldError(
                        field,
                        "TypeMismatch",
                        "O valor informado para o campo '" + field + "' é inválido."
                )
        );

        return build(
                request,
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.INVALID_REQUEST,
                "Existem parâmetros inválidos na requisição.",
                fieldErrors
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ApiErrorCode code = switch (status) {
            case BAD_REQUEST -> ApiErrorCode.INVALID_REQUEST;
            case UNAUTHORIZED -> ApiErrorCode.UNAUTHORIZED;
            case FORBIDDEN -> ApiErrorCode.FORBIDDEN;
            case NOT_FOUND -> ApiErrorCode.NOT_FOUND;
            case CONFLICT -> ApiErrorCode.CONFLICT;
            case UNPROCESSABLE_ENTITY -> ApiErrorCode.BUSINESS_RULE_VIOLATION;
            default -> ApiErrorCode.INTERNAL_SERVER_ERROR;
        };

        log.warn(
                "Erro esperado [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getReason()
        );

        return build(request, status, code, ex.getReason(), null);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthException(
            AuthException ex,
            HttpServletRequest request
    ) {
        log.warn(
                "Falha de autenticação [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        return build(request, ex.getStatus(), ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        log.warn(
                "Violação de regra [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        return build(
                request,
                HttpStatus.UNPROCESSABLE_ENTITY,
                ApiErrorCode.BUSINESS_RULE_VIOLATION,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalState(
            IllegalStateException ex,
            HttpServletRequest request
    ) {
        log.warn(
                "Estado inválido [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        return build(
                request,
                HttpStatus.CONFLICT,
                ApiErrorCode.CONFLICT,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponse> handleNoSuchElement(
            NoSuchElementException ex,
            HttpServletRequest request
    ) {
        log.warn(
                "Recurso ausente [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        return build(
                request,
                HttpStatus.NOT_FOUND,
                ApiErrorCode.NOT_FOUND,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        return build(
                request,
                HttpStatus.UNAUTHORIZED,
                ApiErrorCode.UNAUTHORIZED,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        return build(
                request,
                HttpStatus.FORBIDDEN,
                ApiErrorCode.FORBIDDEN,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error(
                "Erro inesperado em {} {}",
                request.getMethod(),
                request.getRequestURI(),
                ex
        );

        return build(
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ApiErrorCode.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado.",
                null
        );
    }

    private ResponseEntity<ApiErrorResponse> build(
            HttpServletRequest request,
            HttpStatus status,
            ApiErrorCode code,
            String message,
            List<ApiFieldErrorResponse> fieldErrors
    ) {
        return ResponseEntity.status(status).body(factory.create(request, status, code, message, fieldErrors));
    }

    private ApiFieldErrorResponse toFieldError(FieldError error) {
        return factory.fieldError(
                error.getField(),
                firstCode(error.getCode(), error.getCodes()),
                error.getDefaultMessage()
        );
    }

    private ApiFieldErrorResponse toFieldError(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath() == null ? null : violation.getPropertyPath().toString();
        String field = path == null || path.isBlank() ? "field" : path;
        String code = violation.getConstraintDescriptor() == null || violation.getConstraintDescriptor().getAnnotation() == null
                ? "ConstraintViolation"
                : violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();

        return factory.fieldError(field, code, violation.getMessage());
    }

    private String firstCode(String code, String[] codes) {
        if (code != null && !code.isBlank()) {
            return code;
        }

        if (codes != null) {
            for (String candidate : codes) {
                if (candidate != null && !candidate.isBlank()) {
                    return candidate;
                }
            }
        }

        return "Validation";
    }
}
