package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ApiFieldErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ApiFieldErrorResponse {

  private String field;

  private String code;

  private String message;

  public ApiFieldErrorResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ApiFieldErrorResponse(String field, String code, String message) {
    this.field = field;
    this.code = code;
    this.message = message;
  }

  public ApiFieldErrorResponse field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Campo com erro.
   * @return field
   */
  @NotNull 
  @Schema(name = "field", description = "Campo com erro.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("field")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public ApiFieldErrorResponse code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Código da violação do campo.
   * @return code
   */
  @NotNull 
  @Schema(name = "code", description = "Código da violação do campo.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ApiFieldErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Mensagem pública do campo.
   * @return message
   */
  @NotNull 
  @Schema(name = "message", description = "Mensagem pública do campo.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiFieldErrorResponse apiFieldErrorResponse = (ApiFieldErrorResponse) o;
    return Objects.equals(this.field, apiFieldErrorResponse.field) &&
        Objects.equals(this.code, apiFieldErrorResponse.code) &&
        Objects.equals(this.message, apiFieldErrorResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, code, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiFieldErrorResponse {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

