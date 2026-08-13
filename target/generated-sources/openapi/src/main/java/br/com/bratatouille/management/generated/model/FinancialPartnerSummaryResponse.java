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
 * FinancialPartnerSummaryResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class FinancialPartnerSummaryResponse {

  private @Nullable Long partnerId;

  private @Nullable String partnerName;

  private @Nullable BigDecimal totalPaid;

  private @Nullable BigDecimal totalOwed;

  private @Nullable BigDecimal balance;

  public FinancialPartnerSummaryResponse partnerId(@Nullable Long partnerId) {
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

  public FinancialPartnerSummaryResponse partnerName(@Nullable String partnerName) {
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

  public FinancialPartnerSummaryResponse totalPaid(@Nullable BigDecimal totalPaid) {
    this.totalPaid = totalPaid;
    return this;
  }

  /**
   * Get totalPaid
   * @return totalPaid
   */
  @Valid 
  @Schema(name = "totalPaid", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPaid")
  public @Nullable BigDecimal getTotalPaid() {
    return totalPaid;
  }

  public void setTotalPaid(@Nullable BigDecimal totalPaid) {
    this.totalPaid = totalPaid;
  }

  public FinancialPartnerSummaryResponse totalOwed(@Nullable BigDecimal totalOwed) {
    this.totalOwed = totalOwed;
    return this;
  }

  /**
   * Get totalOwed
   * @return totalOwed
   */
  @Valid 
  @Schema(name = "totalOwed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalOwed")
  public @Nullable BigDecimal getTotalOwed() {
    return totalOwed;
  }

  public void setTotalOwed(@Nullable BigDecimal totalOwed) {
    this.totalOwed = totalOwed;
  }

  public FinancialPartnerSummaryResponse balance(@Nullable BigDecimal balance) {
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
    FinancialPartnerSummaryResponse financialPartnerSummaryResponse = (FinancialPartnerSummaryResponse) o;
    return Objects.equals(this.partnerId, financialPartnerSummaryResponse.partnerId) &&
        Objects.equals(this.partnerName, financialPartnerSummaryResponse.partnerName) &&
        Objects.equals(this.totalPaid, financialPartnerSummaryResponse.totalPaid) &&
        Objects.equals(this.totalOwed, financialPartnerSummaryResponse.totalOwed) &&
        Objects.equals(this.balance, financialPartnerSummaryResponse.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partnerId, partnerName, totalPaid, totalOwed, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FinancialPartnerSummaryResponse {\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
    sb.append("    partnerName: ").append(toIndentedString(partnerName)).append("\n");
    sb.append("    totalPaid: ").append(toIndentedString(totalPaid)).append("\n");
    sb.append("    totalOwed: ").append(toIndentedString(totalOwed)).append("\n");
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

