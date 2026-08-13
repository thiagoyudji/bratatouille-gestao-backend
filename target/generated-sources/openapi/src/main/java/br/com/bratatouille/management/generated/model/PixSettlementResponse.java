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
 * PixSettlementResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PixSettlementResponse {

  private @Nullable Long fromPartnerId;

  private @Nullable String fromPartnerName;

  private @Nullable Long toPartnerId;

  private @Nullable String toPartnerName;

  private @Nullable BigDecimal amount;

  public PixSettlementResponse fromPartnerId(@Nullable Long fromPartnerId) {
    this.fromPartnerId = fromPartnerId;
    return this;
  }

  /**
   * Get fromPartnerId
   * @return fromPartnerId
   */
  
  @Schema(name = "fromPartnerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fromPartnerId")
  public @Nullable Long getFromPartnerId() {
    return fromPartnerId;
  }

  public void setFromPartnerId(@Nullable Long fromPartnerId) {
    this.fromPartnerId = fromPartnerId;
  }

  public PixSettlementResponse fromPartnerName(@Nullable String fromPartnerName) {
    this.fromPartnerName = fromPartnerName;
    return this;
  }

  /**
   * Get fromPartnerName
   * @return fromPartnerName
   */
  
  @Schema(name = "fromPartnerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fromPartnerName")
  public @Nullable String getFromPartnerName() {
    return fromPartnerName;
  }

  public void setFromPartnerName(@Nullable String fromPartnerName) {
    this.fromPartnerName = fromPartnerName;
  }

  public PixSettlementResponse toPartnerId(@Nullable Long toPartnerId) {
    this.toPartnerId = toPartnerId;
    return this;
  }

  /**
   * Get toPartnerId
   * @return toPartnerId
   */
  
  @Schema(name = "toPartnerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("toPartnerId")
  public @Nullable Long getToPartnerId() {
    return toPartnerId;
  }

  public void setToPartnerId(@Nullable Long toPartnerId) {
    this.toPartnerId = toPartnerId;
  }

  public PixSettlementResponse toPartnerName(@Nullable String toPartnerName) {
    this.toPartnerName = toPartnerName;
    return this;
  }

  /**
   * Get toPartnerName
   * @return toPartnerName
   */
  
  @Schema(name = "toPartnerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("toPartnerName")
  public @Nullable String getToPartnerName() {
    return toPartnerName;
  }

  public void setToPartnerName(@Nullable String toPartnerName) {
    this.toPartnerName = toPartnerName;
  }

  public PixSettlementResponse amount(@Nullable BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   */
  @Valid 
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amount")
  public @Nullable BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(@Nullable BigDecimal amount) {
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
    PixSettlementResponse pixSettlementResponse = (PixSettlementResponse) o;
    return Objects.equals(this.fromPartnerId, pixSettlementResponse.fromPartnerId) &&
        Objects.equals(this.fromPartnerName, pixSettlementResponse.fromPartnerName) &&
        Objects.equals(this.toPartnerId, pixSettlementResponse.toPartnerId) &&
        Objects.equals(this.toPartnerName, pixSettlementResponse.toPartnerName) &&
        Objects.equals(this.amount, pixSettlementResponse.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromPartnerId, fromPartnerName, toPartnerId, toPartnerName, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PixSettlementResponse {\n");
    sb.append("    fromPartnerId: ").append(toIndentedString(fromPartnerId)).append("\n");
    sb.append("    fromPartnerName: ").append(toIndentedString(fromPartnerName)).append("\n");
    sb.append("    toPartnerId: ").append(toIndentedString(toPartnerId)).append("\n");
    sb.append("    toPartnerName: ").append(toIndentedString(toPartnerName)).append("\n");
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

