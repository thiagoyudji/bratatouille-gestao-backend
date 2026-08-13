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
 * PurchaseSplitResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PurchaseSplitResponse {

  private @Nullable Long id;

  private @Nullable Long partnerId;

  private @Nullable String partnerName;

  private @Nullable BigDecimal percentage;

  private @Nullable BigDecimal owedAmount;

  public PurchaseSplitResponse id(@Nullable Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable Long getId() {
    return id;
  }

  public void setId(@Nullable Long id) {
    this.id = id;
  }

  public PurchaseSplitResponse partnerId(@Nullable Long partnerId) {
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

  public PurchaseSplitResponse partnerName(@Nullable String partnerName) {
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

  public PurchaseSplitResponse percentage(@Nullable BigDecimal percentage) {
    this.percentage = percentage;
    return this;
  }

  /**
   * Get percentage
   * @return percentage
   */
  @Valid 
  @Schema(name = "percentage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("percentage")
  public @Nullable BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(@Nullable BigDecimal percentage) {
    this.percentage = percentage;
  }

  public PurchaseSplitResponse owedAmount(@Nullable BigDecimal owedAmount) {
    this.owedAmount = owedAmount;
    return this;
  }

  /**
   * Get owedAmount
   * @return owedAmount
   */
  @Valid 
  @Schema(name = "owedAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("owedAmount")
  public @Nullable BigDecimal getOwedAmount() {
    return owedAmount;
  }

  public void setOwedAmount(@Nullable BigDecimal owedAmount) {
    this.owedAmount = owedAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PurchaseSplitResponse purchaseSplitResponse = (PurchaseSplitResponse) o;
    return Objects.equals(this.id, purchaseSplitResponse.id) &&
        Objects.equals(this.partnerId, purchaseSplitResponse.partnerId) &&
        Objects.equals(this.partnerName, purchaseSplitResponse.partnerName) &&
        Objects.equals(this.percentage, purchaseSplitResponse.percentage) &&
        Objects.equals(this.owedAmount, purchaseSplitResponse.owedAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, partnerId, partnerName, percentage, owedAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PurchaseSplitResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
    sb.append("    partnerName: ").append(toIndentedString(partnerName)).append("\n");
    sb.append("    percentage: ").append(toIndentedString(percentage)).append("\n");
    sb.append("    owedAmount: ").append(toIndentedString(owedAmount)).append("\n");
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

