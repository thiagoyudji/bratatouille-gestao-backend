package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Códigos estáveis de erro suportados pela API.
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public enum ApiErrorCode {
  
  VALIDATION_ERROR("VALIDATION_ERROR"),
  
  INVALID_REQUEST("INVALID_REQUEST"),
  
  BUSINESS_RULE_VIOLATION("BUSINESS_RULE_VIOLATION"),
  
  NOT_FOUND("NOT_FOUND"),
  
  CONFLICT("CONFLICT"),
  
  UNAUTHORIZED("UNAUTHORIZED"),
  
  FORBIDDEN("FORBIDDEN"),
  
  AUTH_INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS"),
  
  AUTH_USER_INACTIVE("AUTH_USER_INACTIVE"),
  
  AUTH_LOGIN_NOT_ALLOWED("AUTH_LOGIN_NOT_ALLOWED"),
  
  AUTH_BOOTSTRAP_ALREADY_COMPLETED("AUTH_BOOTSTRAP_ALREADY_COMPLETED"),
  
  AUTH_USERNAME_ALREADY_EXISTS("AUTH_USERNAME_ALREADY_EXISTS"),
  
  AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE("AUTH_CUSTOMER_ROLE_VIA_ECOMMERCE"),
  
  INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");

  private final String value;

  ApiErrorCode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ApiErrorCode fromValue(String value) {
    for (ApiErrorCode b : ApiErrorCode.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

