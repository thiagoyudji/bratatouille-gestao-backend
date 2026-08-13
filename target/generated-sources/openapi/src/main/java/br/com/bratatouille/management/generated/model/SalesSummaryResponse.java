package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Summary of sales for a period, using the actual sold price stored in each order.
 */

@Schema(name = "SalesSummaryResponse", description = "Summary of sales for a period, using the actual sold price stored in each order.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SalesSummaryResponse {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate endDate;

  private @Nullable Long totalOrders;

  private @Nullable BigDecimal totalAmount;

  private @Nullable BigDecimal totalCost;

  private @Nullable BigDecimal grossProfit;

  private @Nullable BigDecimal averageTicket;

  private @Nullable BigDecimal grossMarginPercentage;

  private @Nullable Long costIncompleteItems;

  private @Nullable Boolean hasCostIncomplete;

  public SalesSummaryResponse startDate(@Nullable LocalDate startDate) {
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

  public SalesSummaryResponse endDate(@Nullable LocalDate endDate) {
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

  public SalesSummaryResponse totalOrders(@Nullable Long totalOrders) {
    this.totalOrders = totalOrders;
    return this;
  }

  /**
   * Get totalOrders
   * @return totalOrders
   */
  
  @Schema(name = "totalOrders", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalOrders")
  public @Nullable Long getTotalOrders() {
    return totalOrders;
  }

  public void setTotalOrders(@Nullable Long totalOrders) {
    this.totalOrders = totalOrders;
  }

  public SalesSummaryResponse totalAmount(@Nullable BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * Get totalAmount
   * @return totalAmount
   */
  @Valid 
  @Schema(name = "totalAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalAmount")
  public @Nullable BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(@Nullable BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public SalesSummaryResponse totalCost(@Nullable BigDecimal totalCost) {
    this.totalCost = totalCost;
    return this;
  }

  /**
   * Get totalCost
   * @return totalCost
   */
  @Valid 
  @Schema(name = "totalCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalCost")
  public @Nullable BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(@Nullable BigDecimal totalCost) {
    this.totalCost = totalCost;
  }

  public SalesSummaryResponse grossProfit(@Nullable BigDecimal grossProfit) {
    this.grossProfit = grossProfit;
    return this;
  }

  /**
   * Get grossProfit
   * @return grossProfit
   */
  @Valid 
  @Schema(name = "grossProfit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("grossProfit")
  public @Nullable BigDecimal getGrossProfit() {
    return grossProfit;
  }

  public void setGrossProfit(@Nullable BigDecimal grossProfit) {
    this.grossProfit = grossProfit;
  }

  public SalesSummaryResponse averageTicket(@Nullable BigDecimal averageTicket) {
    this.averageTicket = averageTicket;
    return this;
  }

  /**
   * Get averageTicket
   * @return averageTicket
   */
  @Valid 
  @Schema(name = "averageTicket", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("averageTicket")
  public @Nullable BigDecimal getAverageTicket() {
    return averageTicket;
  }

  public void setAverageTicket(@Nullable BigDecimal averageTicket) {
    this.averageTicket = averageTicket;
  }

  public SalesSummaryResponse grossMarginPercentage(@Nullable BigDecimal grossMarginPercentage) {
    this.grossMarginPercentage = grossMarginPercentage;
    return this;
  }

  /**
   * Get grossMarginPercentage
   * @return grossMarginPercentage
   */
  @Valid 
  @Schema(name = "grossMarginPercentage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("grossMarginPercentage")
  public @Nullable BigDecimal getGrossMarginPercentage() {
    return grossMarginPercentage;
  }

  public void setGrossMarginPercentage(@Nullable BigDecimal grossMarginPercentage) {
    this.grossMarginPercentage = grossMarginPercentage;
  }

  public SalesSummaryResponse costIncompleteItems(@Nullable Long costIncompleteItems) {
    this.costIncompleteItems = costIncompleteItems;
    return this;
  }

  /**
   * Get costIncompleteItems
   * @return costIncompleteItems
   */
  
  @Schema(name = "costIncompleteItems", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("costIncompleteItems")
  public @Nullable Long getCostIncompleteItems() {
    return costIncompleteItems;
  }

  public void setCostIncompleteItems(@Nullable Long costIncompleteItems) {
    this.costIncompleteItems = costIncompleteItems;
  }

  public SalesSummaryResponse hasCostIncomplete(@Nullable Boolean hasCostIncomplete) {
    this.hasCostIncomplete = hasCostIncomplete;
    return this;
  }

  /**
   * Get hasCostIncomplete
   * @return hasCostIncomplete
   */
  
  @Schema(name = "hasCostIncomplete", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("hasCostIncomplete")
  public @Nullable Boolean getHasCostIncomplete() {
    return hasCostIncomplete;
  }

  public void setHasCostIncomplete(@Nullable Boolean hasCostIncomplete) {
    this.hasCostIncomplete = hasCostIncomplete;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalesSummaryResponse salesSummaryResponse = (SalesSummaryResponse) o;
    return Objects.equals(this.startDate, salesSummaryResponse.startDate) &&
        Objects.equals(this.endDate, salesSummaryResponse.endDate) &&
        Objects.equals(this.totalOrders, salesSummaryResponse.totalOrders) &&
        Objects.equals(this.totalAmount, salesSummaryResponse.totalAmount) &&
        Objects.equals(this.totalCost, salesSummaryResponse.totalCost) &&
        Objects.equals(this.grossProfit, salesSummaryResponse.grossProfit) &&
        Objects.equals(this.averageTicket, salesSummaryResponse.averageTicket) &&
        Objects.equals(this.grossMarginPercentage, salesSummaryResponse.grossMarginPercentage) &&
        Objects.equals(this.costIncompleteItems, salesSummaryResponse.costIncompleteItems) &&
        Objects.equals(this.hasCostIncomplete, salesSummaryResponse.hasCostIncomplete);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, totalOrders, totalAmount, totalCost, grossProfit, averageTicket, grossMarginPercentage, costIncompleteItems, hasCostIncomplete);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesSummaryResponse {\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    totalOrders: ").append(toIndentedString(totalOrders)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    grossProfit: ").append(toIndentedString(grossProfit)).append("\n");
    sb.append("    averageTicket: ").append(toIndentedString(averageTicket)).append("\n");
    sb.append("    grossMarginPercentage: ").append(toIndentedString(grossMarginPercentage)).append("\n");
    sb.append("    costIncompleteItems: ").append(toIndentedString(costIncompleteItems)).append("\n");
    sb.append("    hasCostIncomplete: ").append(toIndentedString(hasCostIncomplete)).append("\n");
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

