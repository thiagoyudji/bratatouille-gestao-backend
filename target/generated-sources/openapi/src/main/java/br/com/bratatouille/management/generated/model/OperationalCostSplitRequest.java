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
 * OperationalCostSplitRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class OperationalCostSplitRequest {

  private Long partnerId;

  private BigDecimal amount;

  public OperationalCostSplitRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OperationalCostSplitRequest(Long partnerId, BigDecimal amount) {
    this.partnerId = partnerId;
    this.amount = amount;
  }

  public OperationalCostSplitRequest partnerId(Long partnerId) {
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

  public OperationalCostSplitRequest amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationalCostSplitRequest operationalCostSplitRequest = (OperationalCostSplitRequest) o;
    return Objects.equals(this.partnerId, operationalCostSplitRequest.partnerId) &&
        Objects.equals(this.amount, operationalCostSplitRequest.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partnerId, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationalCostSplitRequest {\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

