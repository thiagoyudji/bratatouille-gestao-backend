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
 * ProductionItemResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ProductionItemResponse {

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable BigDecimal consumedQuantity;

  private @Nullable BigDecimal usableQuantity;

  private @Nullable BigDecimal lossQuantity;

  private @Nullable BigDecimal yieldPercentage;

  private @Nullable BigDecimal unitCost;

  private @Nullable BigDecimal totalCost;

  public ProductionItemResponse itemId(@Nullable Long itemId) {
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

  public ProductionItemResponse itemName(@Nullable String itemName) {
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

  public ProductionItemResponse consumedQuantity(@Nullable BigDecimal consumedQuantity) {
    this.consumedQuantity = consumedQuantity;
    return this;
  }

  /**
   * Get consumedQuantity
   * @return consumedQuantity
   */
  @Valid 
  @Schema(name = "consumedQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("consumedQuantity")
  public @Nullable BigDecimal getConsumedQuantity() {
    return consumedQuantity;
  }

  public void setConsumedQuantity(@Nullable BigDecimal consumedQuantity) {
    this.consumedQuantity = consumedQuantity;
  }

  public ProductionItemResponse usableQuantity(@Nullable BigDecimal usableQuantity) {
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

  public ProductionItemResponse lossQuantity(@Nullable BigDecimal lossQuantity) {
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

  public ProductionItemResponse yieldPercentage(@Nullable BigDecimal yieldPercentage) {
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

  public ProductionItemResponse unitCost(@Nullable BigDecimal unitCost) {
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

  public ProductionItemResponse totalCost(@Nullable BigDecimal totalCost) {
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
    ProductionItemResponse productionItemResponse = (ProductionItemResponse) o;
    return Objects.equals(this.itemId, productionItemResponse.itemId) &&
        Objects.equals(this.itemName, productionItemResponse.itemName) &&
        Objects.equals(this.consumedQuantity, productionItemResponse.consumedQuantity) &&
        Objects.equals(this.usableQuantity, productionItemResponse.usableQuantity) &&
        Objects.equals(this.lossQuantity, productionItemResponse.lossQuantity) &&
        Objects.equals(this.yieldPercentage, productionItemResponse.yieldPercentage) &&
        Objects.equals(this.unitCost, productionItemResponse.unitCost) &&
        Objects.equals(this.totalCost, productionItemResponse.totalCost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, consumedQuantity, usableQuantity, lossQuantity, yieldPercentage, unitCost, totalCost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductionItemResponse {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    consumedQuantity: ").append(toIndentedString(consumedQuantity)).append("\n");
    sb.append("    usableQuantity: ").append(toIndentedString(usableQuantity)).append("\n");
    sb.append("    lossQuantity: ").append(toIndentedString(lossQuantity)).append("\n");
    sb.append("    yieldPercentage: ").append(toIndentedString(yieldPercentage)).append("\n");
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

