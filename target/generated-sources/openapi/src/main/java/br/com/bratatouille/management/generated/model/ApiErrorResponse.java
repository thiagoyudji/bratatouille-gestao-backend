package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.ApiErrorCode;
import br.com.bratatouille.management.generated.model.ApiFieldErrorResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ApiErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ApiErrorResponse {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  private Integer status;

  private String error;

  private ApiErrorCode code;

  private String message;

  private String path;

  private String method;

  private @Nullable String traceId;

  @Valid
  private List<@Valid ApiFieldErrorResponse> fieldErrors = new ArrayList<>();

  public ApiErrorResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ApiErrorResponse(OffsetDateTime timestamp, Integer status, String error, ApiErrorCode code, String message, String path, String method) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.code = code;
    this.message = message;
    this.path = path;
    this.method = method;
  }

  public ApiErrorResponse timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Momento em que o erro foi gerado.
   * @return timestamp
   */
  @NotNull @Valid 
  @Schema(name = "timestamp", description = "Momento em que o erro foi gerado.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("timestamp")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public ApiErrorResponse status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * Status HTTP retornado.
   * @return status
   */
  @NotNull 
  @Schema(name = "status", description = "Status HTTP retornado.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public ApiErrorResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Categoria humana do erro.
   * @return error
   */
  @NotNull 
  @Schema(name = "error", description = "Categoria humana do erro.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public ApiErrorResponse code(ApiErrorCode code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
   */
  @NotNull @Valid 
  @Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public ApiErrorCode getCode() {
    return code;
  }

  public void setCode(ApiErrorCode code) {
    this.code = code;
  }

  public ApiErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Mensagem pública em português.
   * @return message
   */
  @NotNull 
  @Schema(name = "message", description = "Mensagem pública em português.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ApiErrorResponse path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Caminho da requisição.
   * @return path
   */
  @NotNull 
  @Schema(name = "path", description = "Caminho da requisição.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ApiErrorResponse method(String method) {
    this.method = method;
    return this;
  }

  /**
   * Método HTTP da requisição.
   * @return method
   */
  @NotNull 
  @Schema(name = "method", description = "Método HTTP da requisição.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("method")
  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public ApiErrorResponse traceId(@Nullable String traceId) {
    this.traceId = traceId;
    return this;
  }

  /**
   * Identificador de correlação do erro.
   * @return traceId
   */
  
  @Schema(name = "traceId", description = "Identificador de correlação do erro.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("traceId")
  public @Nullable String getTraceId() {
    return traceId;
  }

  public void setTraceId(@Nullable String traceId) {
    this.traceId = traceId;
  }

  public ApiErrorResponse fieldErrors(List<@Valid ApiFieldErrorResponse> fieldErrors) {
    this.fieldErrors = fieldErrors;
    return this;
  }

  public ApiErrorResponse addFieldErrorsItem(ApiFieldErrorResponse fieldErrorsItem) {
    if (this.fieldErrors == null) {
      this.fieldErrors = new ArrayList<>();
    }
    this.fieldErrors.add(fieldErrorsItem);
    return this;
  }

  /**
   * Get fieldErrors
   * @return fieldErrors
   */
  @Valid 
  @Schema(name = "fieldErrors", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fieldErrors")
  public List<@Valid ApiFieldErrorResponse> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(List<@Valid ApiFieldErrorResponse> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiErrorResponse apiErrorResponse = (ApiErrorResponse) o;
    return Objects.equals(this.timestamp, apiErrorResponse.timestamp) &&
        Objects.equals(this.status, apiErrorResponse.status) &&
        Objects.equals(this.error, apiErrorResponse.error) &&
        Objects.equals(this.code, apiErrorResponse.code) &&
        Objects.equals(this.message, apiErrorResponse.message) &&
        Objects.equals(this.path, apiErrorResponse.path) &&
        Objects.equals(this.method, apiErrorResponse.method) &&
        Objects.equals(this.traceId, apiErrorResponse.traceId) &&
        Objects.equals(this.fieldErrors, apiErrorResponse.fieldErrors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, status, error, code, message, path, method, traceId, fieldErrors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiErrorResponse {\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
    sb.append("    traceId: ").append(toIndentedString(traceId)).append("\n");
    sb.append("    fieldErrors: ").append(toIndentedString(fieldErrors)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

