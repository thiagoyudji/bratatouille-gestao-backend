package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PurchaseSplitRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PurchaseSplitRequest {

  private Long partnerId;

  private BigDecimal percentage;

  public PurchaseSplitRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PurchaseSplitRequest(Long partnerId, BigDecimal percentage) {
    this.partnerId = partnerId;
    this.percentage = percentage;
  }

  public PurchaseSplitRequest partnerId(Long partnerId) {
    this.partnerId = partnerId;
    return this;
  }

  /**
   * Get partnerId
   * @return partnerId
   */
  @NotNull 
  @Schema(name = "partnerId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("partnerId")
  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public PurchaseSplitRequest percentage(BigDecimal percentage) {
    this.percentage = percentage;
    return this;
  }

  /**
   * Get percentage
   * @return percentage
   */
  @NotNull @Valid 
  @Schema(name = "percentage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("percentage")
  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PurchaseSplitRequest purchaseSplitRequest = (PurchaseSplitRequest) o;
    return Objects.equals(this.partnerId, purchaseSplitRequest.partnerId) &&
        Objects.equals(this.percentage, purchaseSplitRequest.percentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partnerId, percentage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PurchaseSplitRequest {\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
    sb.append("    percentage: ").append(toIndentedString(percentage)).append("\n");
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

