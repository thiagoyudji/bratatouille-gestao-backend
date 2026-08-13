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
 * DashboardOverviewResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class DashboardOverviewResponse {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate endDate;

  private @Nullable Long totalOrders;

  private @Nullable BigDecimal totalRevenue;

  private @Nullable BigDecimal totalCost;

  private @Nullable BigDecimal grossProfit;

  private @Nullable BigDecimal grossMarginPercentage;

  private @Nullable BigDecimal totalOperationalCost;

  private @Nullable BigDecimal fixedOperationalCost;

  private @Nullable BigDecimal variableOperationalCost;

  private @Nullable BigDecimal financialOperationalCost;

  private @Nullable BigDecimal netProfit;

  private @Nullable BigDecimal netMarginPercentage;

  private @Nullable Long criticalStockAlerts;

  private @Nullable Long lowStockAlerts;

  private @Nullable Long nearZeroStockAlerts;

  private @Nullable Long openPartnerBalances;

  private @Nullable Boolean hasCostIncomplete;

  private @Nullable Long costIncompleteItems;

  private @Nullable Boolean marginReliable;

  private @Nullable String marginWarning;

  public DashboardOverviewResponse startDate(@Nullable LocalDate startDate) {
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

  public DashboardOverviewResponse endDate(@Nullable LocalDate endDate) {
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

  public DashboardOverviewResponse totalOrders(@Nullable Long totalOrders) {
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

  public DashboardOverviewResponse totalRevenue(@Nullable BigDecimal totalRevenue) {
    this.totalRevenue = totalRevenue;
    return this;
  }

  /**
   * Get totalRevenue
   * @return totalRevenue
   */
  @Valid 
  @Schema(name = "totalRevenue", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalRevenue")
  public @Nullable BigDecimal getTotalRevenue() {
    return totalRevenue;
  }

  public void setTotalRevenue(@Nullable BigDecimal totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  public DashboardOverviewResponse totalCost(@Nullable BigDecimal totalCost) {
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

  public DashboardOverviewResponse grossProfit(@Nullable BigDecimal grossProfit) {
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

  public DashboardOverviewResponse grossMarginPercentage(@Nullable BigDecimal grossMarginPercentage) {
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

  public DashboardOverviewResponse totalOperationalCost(@Nullable BigDecimal totalOperationalCost) {
    this.totalOperationalCost = totalOperationalCost;
    return this;
  }

  /**
   * Get totalOperationalCost
   * @return totalOperationalCost
   */
  @Valid 
  @Schema(name = "totalOperationalCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalOperationalCost")
  public @Nullable BigDecimal getTotalOperationalCost() {
    return totalOperationalCost;
  }

  public void setTotalOperationalCost(@Nullable BigDecimal totalOperationalCost) {
    this.totalOperationalCost = totalOperationalCost;
  }

  public DashboardOverviewResponse fixedOperationalCost(@Nullable BigDecimal fixedOperationalCost) {
    this.fixedOperationalCost = fixedOperationalCost;
    return this;
  }

  /**
   * Get fixedOperationalCost
   * @return fixedOperationalCost
   */
  @Valid 
  @Schema(name = "fixedOperationalCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fixedOperationalCost")
  public @Nullable BigDecimal getFixedOperationalCost() {
    return fixedOperationalCost;
  }

  public void setFixedOperationalCost(@Nullable BigDecimal fixedOperationalCost) {
    this.fixedOperationalCost = fixedOperationalCost;
  }

  public DashboardOverviewResponse variableOperationalCost(@Nullable BigDecimal variableOperationalCost) {
    this.variableOperationalCost = variableOperationalCost;
    return this;
  }

  /**
   * Get variableOperationalCost
   * @return variableOperationalCost
   */
  @Valid 
  @Schema(name = "variableOperationalCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("variableOperationalCost")
  public @Nullable BigDecimal getVariableOperationalCost() {
    return variableOperationalCost;
  }

  public void setVariableOperationalCost(@Nullable BigDecimal variableOperationalCost) {
    this.variableOperationalCost = variableOperationalCost;
  }

  public DashboardOverviewResponse financialOperationalCost(@Nullable BigDecimal financialOperationalCost) {
    this.financialOperationalCost = financialOperationalCost;
    return this;
  }

  /**
   * Get financialOperationalCost
   * @return financialOperationalCost
   */
  @Valid 
  @Schema(name = "financialOperationalCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("financialOperationalCost")
  public @Nullable BigDecimal getFinancialOperationalCost() {
    return financialOperationalCost;
  }

  public void setFinancialOperationalCost(@Nullable BigDecimal financialOperationalCost) {
    this.financialOperationalCost = financialOperationalCost;
  }

  public DashboardOverviewResponse netProfit(@Nullable BigDecimal netProfit) {
    this.netProfit = netProfit;
    return this;
  }

  /**
   * Get netProfit
   * @return netProfit
   */
  @Valid 
  @Schema(name = "netProfit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("netProfit")
  public @Nullable BigDecimal getNetProfit() {
    return netProfit;
  }

  public void setNetProfit(@Nullable BigDecimal netProfit) {
    this.netProfit = netProfit;
  }

  public DashboardOverviewResponse netMarginPercentage(@Nullable BigDecimal netMarginPercentage) {
    this.netMarginPercentage = netMarginPercentage;
    return this;
  }

  /**
   * Get netMarginPercentage
   * @return netMarginPercentage
   */
  @Valid 
  @Schema(name = "netMarginPercentage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("netMarginPercentage")
  public @Nullable BigDecimal getNetMarginPercentage() {
    return netMarginPercentage;
  }

  public void setNetMarginPercentage(@Nullable BigDecimal netMarginPercentage) {
    this.netMarginPercentage = netMarginPercentage;
  }

  public DashboardOverviewResponse criticalStockAlerts(@Nullable Long criticalStockAlerts) {
    this.criticalStockAlerts = criticalStockAlerts;
    return this;
  }

  /**
   * Get criticalStockAlerts
   * @return criticalStockAlerts
   */
  
  @Schema(name = "criticalStockAlerts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("criticalStockAlerts")
  public @Nullable Long getCriticalStockAlerts() {
    return criticalStockAlerts;
  }

  public void setCriticalStockAlerts(@Nullable Long criticalStockAlerts) {
    this.criticalStockAlerts = criticalStockAlerts;
  }

  public DashboardOverviewResponse lowStockAlerts(@Nullable Long lowStockAlerts) {
    this.lowStockAlerts = lowStockAlerts;
    return this;
  }

  /**
   * Get lowStockAlerts
   * @return lowStockAlerts
   */
  
  @Schema(name = "lowStockAlerts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lowStockAlerts")
  public @Nullable Long getLowStockAlerts() {
    return lowStockAlerts;
  }

  public void setLowStockAlerts(@Nullable Long lowStockAlerts) {
    this.lowStockAlerts = lowStockAlerts;
  }

  public DashboardOverviewResponse nearZeroStockAlerts(@Nullable Long nearZeroStockAlerts) {
    this.nearZeroStockAlerts = nearZeroStockAlerts;
    return this;
  }

  /**
   * Get nearZeroStockAlerts
   * @return nearZeroStockAlerts
   */
  
  @Schema(name = "nearZeroStockAlerts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nearZeroStockAlerts")
  public @Nullable Long getNearZeroStockAlerts() {
    return nearZeroStockAlerts;
  }

  public void setNearZeroStockAlerts(@Nullable Long nearZeroStockAlerts) {
    this.nearZeroStockAlerts = nearZeroStockAlerts;
  }

  public DashboardOverviewResponse openPartnerBalances(@Nullable Long openPartnerBalances) {
    this.openPartnerBalances = openPartnerBalances;
    return this;
  }

  /**
   * Get openPartnerBalances
   * @return openPartnerBalances
   */
  
  @Schema(name = "openPartnerBalances", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("openPartnerBalances")
  public @Nullable Long getOpenPartnerBalances() {
    return openPartnerBalances;
  }

  public void setOpenPartnerBalances(@Nullable Long openPartnerBalances) {
    this.openPartnerBalances = openPartnerBalances;
  }

  public DashboardOverviewResponse hasCostIncomplete(@Nullable Boolean hasCostIncomplete) {
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

  public DashboardOverviewResponse costIncompleteItems(@Nullable Long costIncompleteItems) {
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

  public DashboardOverviewResponse marginReliable(@Nullable Boolean marginReliable) {
    this.marginReliable = marginReliable;
    return this;
  }

  /**
   * Get marginReliable
   * @return marginReliable
   */
  
  @Schema(name = "marginReliable", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("marginReliable")
  public @Nullable Boolean getMarginReliable() {
    return marginReliable;
  }

  public void setMarginReliable(@Nullable Boolean marginReliable) {
    this.marginReliable = marginReliable;
  }

  public DashboardOverviewResponse marginWarning(@Nullable String marginWarning) {
    this.marginWarning = marginWarning;
    return this;
  }

  /**
   * Get marginWarning
   * @return marginWarning
   */
  
  @Schema(name = "marginWarning", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("marginWarning")
  public @Nullable String getMarginWarning() {
    return marginWarning;
  }

  public void setMarginWarning(@Nullable String marginWarning) {
    this.marginWarning = marginWarning;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardOverviewResponse dashboardOverviewResponse = (DashboardOverviewResponse) o;
    return Objects.equals(this.startDate, dashboardOverviewResponse.startDate) &&
        Objects.equals(this.endDate, dashboardOverviewResponse.endDate) &&
        Objects.equals(this.totalOrders, dashboardOverviewResponse.totalOrders) &&
        Objects.equals(this.totalRevenue, dashboardOverviewResponse.totalRevenue) &&
        Objects.equals(this.totalCost, dashboardOverviewResponse.totalCost) &&
        Objects.equals(this.grossProfit, dashboardOverviewResponse.grossProfit) &&
        Objects.equals(this.grossMarginPercentage, dashboardOverviewResponse.grossMarginPercentage) &&
        Objects.equals(this.totalOperationalCost, dashboardOverviewResponse.totalOperationalCost) &&
        Objects.equals(this.fixedOperationalCost, dashboardOverviewResponse.fixedOperationalCost) &&
        Objects.equals(this.variableOperationalCost, dashboardOverviewResponse.variableOperationalCost) &&
        Objects.equals(this.financialOperationalCost, dashboardOverviewResponse.financialOperationalCost) &&
        Objects.equals(this.netProfit, dashboardOverviewResponse.netProfit) &&
        Objects.equals(this.netMarginPercentage, dashboardOverviewResponse.netMarginPercentage) &&
        Objects.equals(this.criticalStockAlerts, dashboardOverviewResponse.criticalStockAlerts) &&
        Objects.equals(this.lowStockAlerts, dashboardOverviewResponse.lowStockAlerts) &&
        Objects.equals(this.nearZeroStockAlerts, dashboardOverviewResponse.nearZeroStockAlerts) &&
        Objects.equals(this.openPartnerBalances, dashboardOverviewResponse.openPartnerBalances) &&
        Objects.equals(this.hasCostIncomplete, dashboardOverviewResponse.hasCostIncomplete) &&
        Objects.equals(this.costIncompleteItems, dashboardOverviewResponse.costIncompleteItems) &&
        Objects.equals(this.marginReliable, dashboardOverviewResponse.marginReliable) &&
        Objects.equals(this.marginWarning, dashboardOverviewResponse.marginWarning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, totalOrders, totalRevenue, totalCost, grossProfit, grossMarginPercentage, totalOperationalCost, fixedOperationalCost, variableOperationalCost, financialOperationalCost, netProfit, netMarginPercentage, criticalStockAlerts, lowStockAlerts, nearZeroStockAlerts, openPartnerBalances, hasCostIncomplete, costIncompleteItems, marginReliable, marginWarning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardOverviewResponse {\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    totalOrders: ").append(toIndentedString(totalOrders)).append("\n");
    sb.append("    totalRevenue: ").append(toIndentedString(totalRevenue)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    grossProfit: ").append(toIndentedString(grossProfit)).append("\n");
    sb.append("    grossMarginPercentage: ").append(toIndentedString(grossMarginPercentage)).append("\n");
    sb.append("    totalOperationalCost: ").append(toIndentedString(totalOperationalCost)).append("\n");
    sb.append("    fixedOperationalCost: ").append(toIndentedString(fixedOperationalCost)).append("\n");
    sb.append("    variableOperationalCost: ").append(toIndentedString(variableOperationalCost)).append("\n");
    sb.append("    financialOperationalCost: ").append(toIndentedString(financialOperationalCost)).append("\n");
    sb.append("    netProfit: ").append(toIndentedString(netProfit)).append("\n");
    sb.append("    netMarginPercentage: ").append(toIndentedString(netMarginPercentage)).append("\n");
    sb.append("    criticalStockAlerts: ").append(toIndentedString(criticalStockAlerts)).append("\n");
    sb.append("    lowStockAlerts: ").append(toIndentedString(lowStockAlerts)).append("\n");
    sb.append("    nearZeroStockAlerts: ").append(toIndentedString(nearZeroStockAlerts)).append("\n");
    sb.append("    openPartnerBalances: ").append(toIndentedString(openPartnerBalances)).append("\n");
    sb.append("    hasCostIncomplete: ").append(toIndentedString(hasCostIncomplete)).append("\n");
    sb.append("    costIncompleteItems: ").append(toIndentedString(costIncompleteItems)).append("\n");
    sb.append("    marginReliable: ").append(toIndentedString(marginReliable)).append("\n");
    sb.append("    marginWarning: ").append(toIndentedString(marginWarning)).append("\n");
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

