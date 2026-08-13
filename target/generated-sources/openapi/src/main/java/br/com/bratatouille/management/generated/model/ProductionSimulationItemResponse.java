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
 * ProductionSimulationItemResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ProductionSimulationItemResponse {

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable BigDecimal requiredQuantity;

  private @Nullable BigDecimal usableQuantity;

  private @Nullable BigDecimal lossQuantity;

  private @Nullable BigDecimal yieldPercentage;

  private @Nullable BigDecimal currentStock;

  private @Nullable BigDecimal missingQuantity;

  private @Nullable BigDecimal unitCost;

  private @Nullable BigDecimal totalCost;

  public ProductionSimulationItemResponse itemId(@Nullable Long itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
   */
  
  @Schema(name = "itemId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemId")
  public @Nullable Long getItemId() {
    return itemId;
  }

  public void setItemId(@Nullable Long itemId) {
    this.itemId = itemId;
  }

  public ProductionSimulationItemResponse itemName(@Nullable String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Get itemName
   * @return itemName
   */
  
  @Schema(name = "itemName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemName")
  public @Nullable String getItemName() {
    return itemName;
  }

  public void setItemName(@Nullable String itemName) {
    this.itemName = itemName;
  }

  public ProductionSimulationItemResponse requiredQuantity(@Nullable BigDecimal requiredQuantity) {
    this.requiredQuantity = requiredQuantity;
    return this;
  }

  /**
   * Get requiredQuantity
   * @return requiredQuantity
   */
  @Valid 
  @Schema(name = "requiredQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requiredQuantity")
  public @Nullable BigDecimal getRequiredQuantity() {
    return requiredQuantity;
  }

  public void setRequiredQuantity(@Nullable BigDecimal requiredQuantity) {
    this.requiredQuantity = requiredQuantity;
  }

  public ProductionSimulationItemResponse usableQuantity(@Nullable BigDecimal usableQuantity) {
    this.usableQuantity = usableQuantity;
    return this;
  }

  /**
   * Get usableQuantity
   * @return usableQuantity
   */
  @Valid 
  @Schema(name = "usableQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("usableQuantity")
  public @Nullable BigDecimal getUsableQuantity() {
    return usableQuantity;
  }

  public void setUsableQuantity(@Nullable BigDecimal usableQuantity) {
    this.usableQuantity = usableQuantity;
  }

  public ProductionSimulationItemResponse lossQuantity(@Nullable BigDecimal lossQuantity) {
    this.lossQuantity = lossQuantity;
    return this;
  }

  /**
   * Get lossQuantity
   * @return lossQuantity
   */
  @Valid 
  @Schema(name = "lossQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lossQuantity")
  public @Nullable BigDecimal getLossQuantity() {
    return lossQuantity;
  }

  public void setLossQuantity(@Nullable BigDecimal lossQuantity) {
    this.lossQuantity = lossQuantity;
  }

  public ProductionSimulationItemResponse yieldPercentage(@Nullable BigDecimal yieldPercentage) {
    this.yieldPercentage = yieldPercentage;
    return this;
  }

  /**
   * Get yieldPercentage
   * @return yieldPercentage
   */
  @Valid 
  @Schema(name = "yieldPercentage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("yieldPercentage")
  public @Nullable BigDecimal getYieldPercentage() {
    return yieldPercentage;
  }

  public void setYieldPercentage(@Nullable BigDecimal yieldPercentage) {
    this.yieldPercentage = yieldPercentage;
  }

  public ProductionSimulationItemResponse currentStock(@Nullable BigDecimal currentStock) {
    this.currentStock = currentStock;
    return this;
  }

  /**
   * Get currentStock
   * @return currentStock
   */
  @Valid 
  @Schema(name = "currentStock", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currentStock")
  public @Nullable BigDecimal getCurrentStock() {
    return currentStock;
  }

  public void setCurrentStock(@Nullable BigDecimal currentStock) {
    this.currentStock = currentStock;
  }

  public ProductionSimulationItemResponse missingQuantity(@Nullable BigDecimal missingQuantity) {
    this.missingQuantity = missingQuantity;
    return this;
  }

  /**
   * Get missingQuantity
   * @return missingQuantity
   */
  @Valid 
  @Schema(name = "missingQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("missingQuantity")
  public @Nullable BigDecimal getMissingQuantity() {
    return missingQuantity;
  }

  public void setMissingQuantity(@Nullable BigDecimal missingQuantity) {
    this.missingQuantity = missingQuantity;
  }

  public ProductionSimulationItemResponse unitCost(@Nullable BigDecimal unitCost) {
    this.unitCost = unitCost;
    return this;
  }

  /**
   * Get unitCost
   * @return unitCost
   */
  @Valid 
  @Schema(name = "unitCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unitCost")
  public @Nullable BigDecimal getUnitCost() {
    return unitCost;
  }

  public void setUnitCost(@Nullable BigDecimal unitCost) {
    this.unitCost = unitCost;
  }

  public ProductionSimulationItemResponse totalCost(@Nullable BigDecimal totalCost) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductionSimulationItemResponse productionSimulationItemResponse = (ProductionSimulationItemResponse) o;
    return Objects.equals(this.itemId, productionSimulationItemResponse.itemId) &&
        Objects.equals(this.itemName, productionSimulationItemResponse.itemName) &&
        Objects.equals(this.requiredQuantity, productionSimulationItemResponse.requiredQuantity) &&
        Objects.equals(this.usableQuantity, productionSimulationItemResponse.usableQuantity) &&
        Objects.equals(this.lossQuantity, productionSimulationItemResponse.lossQuantity) &&
        Objects.equals(this.yieldPercentage, productionSimulationItemResponse.yieldPercentage) &&
        Objects.equals(this.currentStock, productionSimulationItemResponse.currentStock) &&
        Objects.equals(this.missingQuantity, productionSimulationItemResponse.missingQuantity) &&
        Objects.equals(this.unitCost, productionSimulationItemResponse.unitCost) &&
        Objects.equals(this.totalCost, productionSimulationItemResponse.totalCost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, requiredQuantity, usableQuantity, lossQuantity, yieldPercentage, currentStock, missingQuantity, unitCost, totalCost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductionSimulationItemResponse {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    requiredQuantity: ").append(toIndentedString(requiredQuantity)).append("\n");
    sb.append("    usableQuantity: ").append(toIndentedString(usableQuantity)).append("\n");
    sb.append("    lossQuantity: ").append(toIndentedString(lossQuantity)).append("\n");
    sb.append("    yieldPercentage: ").append(toIndentedString(yieldPercentage)).append("\n");
    sb.append("    currentStock: ").append(toIndentedString(currentStock)).append("\n");
    sb.append("    missingQuantity: ").append(toIndentedString(missingQuantity)).append("\n");
    sb.append("    unitCost: ").append(toIndentedString(unitCost)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
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

