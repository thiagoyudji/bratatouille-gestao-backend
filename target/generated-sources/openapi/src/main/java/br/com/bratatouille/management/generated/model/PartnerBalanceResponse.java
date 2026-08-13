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
 * PartnerBalanceResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PartnerBalanceResponse {

  private @Nullable Long partnerId;

  private @Nullable String partnerName;

  private @Nullable BigDecimal balance;

  public PartnerBalanceResponse partnerId(@Nullable Long partnerId) {
    this.partnerId = partnerId;
    return this;
  }

  /**
   * Get partnerId
   * @return partnerId
   */
  
  @Schema(name = "partnerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partnerId")
  public @Nullable Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(@Nullable Long partnerId) {
    this.partnerId = partnerId;
  }

  public PartnerBalanceResponse partnerName(@Nullable String partnerName) {
    this.partnerName = partnerName;
    return this;
  }

  /**
   * Get partnerName
   * @return partnerName
   */
  
  @Schema(name = "partnerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partnerName")
  public @Nullable String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(@Nullable String partnerName) {
    this.partnerName = partnerName;
  }

  public PartnerBalanceResponse balance(@Nullable BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   */
  @Valid 
  @Schema(name = "balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("balance")
  public @Nullable BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(@Nullable BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartnerBalanceResponse partnerBalanceResponse = (PartnerBalanceResponse) o;
    return Objects.equals(this.partnerId, partnerBalanceResponse.partnerId) &&
        Objects.equals(this.partnerName, partnerBalanceResponse.partnerName) &&
        Objects.equals(this.balance, partnerBalanceResponse.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partnerId, partnerName, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartnerBalanceResponse {\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
    sb.append("    partnerName: ").append(toIndentedString(partnerName)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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

