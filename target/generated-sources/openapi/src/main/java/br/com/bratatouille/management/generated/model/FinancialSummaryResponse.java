package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.FinancialPartnerSummaryResponse;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * FinancialSummaryResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class FinancialSummaryResponse {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate endDate;

  private @Nullable BigDecimal totalPurchases;

  private @Nullable BigDecimal totalOperationalCosts;

  private @Nullable BigDecimal totalSpent;

  @Valid
  private List<@Valid FinancialPartnerSummaryResponse> partners = new ArrayList<>();

  @Valid
  private List<@Valid PixSettlementResponse> pixSettlement = new ArrayList<>();

  public FinancialSummaryResponse startDate(@Nullable LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
   */
  @Valid 
  @Schema(name = "startDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startDate")
  public @Nullable LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(@Nullable LocalDate startDate) {
    this.startDate = startDate;
  }

  public FinancialSummaryResponse endDate(@Nullable LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
   */
  @Valid 
  @Schema(name = "endDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endDate")
  public @Nullable LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(@Nullable LocalDate endDate) {
    this.endDate = endDate;
  }

  public FinancialSummaryResponse totalPurchases(@Nullable BigDecimal totalPurchases) {
    this.totalPurchases = totalPurchases;
    return this;
  }

  /**
   * Get totalPurchases
   * @return totalPurchases
   */
  @Valid 
  @Schema(name = "totalPurchases", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPurchases")
  public @Nullable BigDecimal getTotalPurchases() {
    return totalPurchases;
  }

  public void setTotalPurchases(@Nullable BigDecimal totalPurchases) {
    this.totalPurchases = totalPurchases;
  }

  public FinancialSummaryResponse totalOperationalCosts(@Nullable BigDecimal totalOperationalCosts) {
    this.totalOperationalCosts = totalOperationalCosts;
    return this;
  }

  /**
   * Get totalOperationalCosts
   * @return totalOperationalCosts
   */
  @Valid 
  @Schema(name = "totalOperationalCosts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalOperationalCosts")
  public @Nullable BigDecimal getTotalOperationalCosts() {
    return totalOperationalCosts;
  }

  public void setTotalOperationalCosts(@Nullable BigDecimal totalOperationalCosts) {
    this.totalOperationalCosts = totalOperationalCosts;
  }

  public FinancialSummaryResponse totalSpent(@Nullable BigDecimal totalSpent) {
    this.totalSpent = totalSpent;
    return this;
  }

  /**
   * Get totalSpent
   * @return totalSpent
   */
  @Valid 
  @Schema(name = "totalSpent", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalSpent")
  public @Nullable BigDecimal getTotalSpent() {
    return totalSpent;
  }

  public void setTotalSpent(@Nullable BigDecimal totalSpent) {
    this.totalSpent = totalSpent;
  }

  public FinancialSummaryResponse partners(List<@Valid FinancialPartnerSummaryResponse> partners) {
    this.partners = partners;
    return this;
  }

  public FinancialSummaryResponse addPartnersItem(FinancialPartnerSummaryResponse partnersItem) {
    if (this.partners == null) {
      this.partners = new ArrayList<>();
    }
    this.partners.add(partnersItem);
    return this;
  }

  /**
   * Get partners
   * @return partners
   */
  @Valid 
  @Schema(name = "partners", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partners")
  public List<@Valid FinancialPartnerSummaryResponse> getPartners() {
    return partners;
  }

  public void setPartners(List<@Valid FinancialPartnerSummaryResponse> partners) {
    this.partners = partners;
  }

  public FinancialSummaryResponse pixSettlement(List<@Valid PixSettlementResponse> pixSettlement) {
    this.pixSettlement = pixSettlement;
    return this;
  }

  public FinancialSummaryResponse addPixSettlementItem(PixSettlementResponse pixSettlementItem) {
    if (this.pixSettlement == null) {
      this.pixSettlement = new ArrayList<>();
    }
    this.pixSettlement.add(pixSettlementItem);
    return this;
  }

  /**
   * Get pixSettlement
   * @return pixSettlement
   */
  @Valid 
  @Schema(name = "pixSettlement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pixSettlement")
  public List<@Valid PixSettlementResponse> getPixSettlement() {
    return pixSettlement;
  }

  public void setPixSettlement(List<@Valid PixSettlementResponse> pixSettlement) {
    this.pixSettlement = pixSettlement;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FinancialSummaryResponse financialSummaryResponse = (FinancialSummaryResponse) o;
    return Objects.equals(this.startDate, financialSummaryResponse.startDate) &&
        Objects.equals(this.endDate, financialSummaryResponse.endDate) &&
        Objects.equals(this.totalPurchases, financialSummaryResponse.totalPurchases) &&
        Objects.equals(this.totalOperationalCosts, financialSummaryResponse.totalOperationalCosts) &&
        Objects.equals(this.totalSpent, financialSummaryResponse.totalSpent) &&
        Objects.equals(this.partners, financialSummaryResponse.partners) &&
        Objects.equals(this.pixSettlement, financialSummaryResponse.pixSettlement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, totalPurchases, totalOperationalCosts, totalSpent, partners, pixSettlement);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FinancialSummaryResponse {\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    totalPurchases: ").append(toIndentedString(totalPurchases)).append("\n");
    sb.append("    totalOperationalCosts: ").append(toIndentedString(totalOperationalCosts)).append("\n");
    sb.append("    totalSpent: ").append(toIndentedString(totalSpent)).append("\n");
    sb.append("    partners: ").append(toIndentedString(partners)).append("\n");
    sb.append("    pixSettlement: ").append(toIndentedString(pixSettlement)).append("\n");
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

