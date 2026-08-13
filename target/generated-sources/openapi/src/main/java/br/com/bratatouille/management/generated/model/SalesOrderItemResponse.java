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
 * Snapshot do item vendido com os dois preços para auditoria e exibição no front.
 */

@Schema(name = "SalesOrderItemResponse", description = "Snapshot do item vendido com os dois preços para auditoria e exibição no front.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SalesOrderItemResponse {

  private @Nullable Long id;

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable Boolean costIncomplete;

  private @Nullable BigDecimal quantity;

  private @Nullable BigDecimal unitPrice;

  private @Nullable BigDecimal unitPricePf;

  private @Nullable BigDecimal unitPricePj;

  private @Nullable BigDecimal totalPrice;

  private @Nullable BigDecimal unitCost;

  private @Nullable BigDecimal totalCost;

  private @Nullable BigDecimal grossProfit;

  public SalesOrderItemResponse id(@Nullable Long id) {
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

  public SalesOrderItemResponse itemId(@Nullable Long itemId) {
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

  public SalesOrderItemResponse itemName(@Nullable String itemName) {
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

  public SalesOrderItemResponse costIncomplete(@Nullable Boolean costIncomplete) {
    this.costIncomplete = costIncomplete;
    return this;
  }

  /**
   * Get costIncomplete
   * @return costIncomplete
   */
  
  @Schema(name = "costIncomplete", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("costIncomplete")
  public @Nullable Boolean getCostIncomplete() {
    return costIncomplete;
  }

  public void setCostIncomplete(@Nullable Boolean costIncomplete) {
    this.costIncomplete = costIncomplete;
  }

  public SalesOrderItemResponse quantity(@Nullable BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
   */
  @Valid 
  @Schema(name = "quantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantity")
  public @Nullable BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(@Nullable BigDecimal quantity) {
    this.quantity = quantity;
  }

  public SalesOrderItemResponse unitPrice(@Nullable BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  /**
   * Get unitPrice
   * @return unitPrice
   */
  @Valid 
  @Schema(name = "unitPrice", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unitPrice")
  public @Nullable BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(@Nullable BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public SalesOrderItemResponse unitPricePf(@Nullable BigDecimal unitPricePf) {
    this.unitPricePf = unitPricePf;
    return this;
  }

  /**
   * Get unitPricePf
   * @return unitPricePf
   */
  @Valid 
  @Schema(name = "unitPricePf", example = "12.9", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unitPricePf")
  public @Nullable BigDecimal getUnitPricePf() {
    return unitPricePf;
  }

  public void setUnitPricePf(@Nullable BigDecimal unitPricePf) {
    this.unitPricePf = unitPricePf;
  }

  public SalesOrderItemResponse unitPricePj(@Nullable BigDecimal unitPricePj) {
    this.unitPricePj = unitPricePj;
    return this;
  }

  /**
   * Get unitPricePj
   * @return unitPricePj
   */
  @Valid 
  @Schema(name = "unitPricePj", example = "15.9", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unitPricePj")
  public @Nullable BigDecimal getUnitPricePj() {
    return unitPricePj;
  }

  public void setUnitPricePj(@Nullable BigDecimal unitPricePj) {
    this.unitPricePj = unitPricePj;
  }

  public SalesOrderItemResponse totalPrice(@Nullable BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  /**
   * Get totalPrice
   * @return totalPrice
   */
  @Valid 
  @Schema(name = "totalPrice", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPrice")
  public @Nullable BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(@Nullable BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public SalesOrderItemResponse unitCost(@Nullable BigDecimal unitCost) {
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

  public SalesOrderItemResponse totalCost(@Nullable BigDecimal totalCost) {
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

  public SalesOrderItemResponse grossProfit(@Nullable BigDecimal grossProfit) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalesOrderItemResponse salesOrderItemResponse = (SalesOrderItemResponse) o;
    return Objects.equals(this.id, salesOrderItemResponse.id) &&
        Objects.equals(this.itemId, salesOrderItemResponse.itemId) &&
        Objects.equals(this.itemName, salesOrderItemResponse.itemName) &&
        Objects.equals(this.costIncomplete, salesOrderItemResponse.costIncomplete) &&
        Objects.equals(this.quantity, salesOrderItemResponse.quantity) &&
        Objects.equals(this.unitPrice, salesOrderItemResponse.unitPrice) &&
        Objects.equals(this.unitPricePf, salesOrderItemResponse.unitPricePf) &&
        Objects.equals(this.unitPricePj, salesOrderItemResponse.unitPricePj) &&
        Objects.equals(this.totalPrice, salesOrderItemResponse.totalPrice) &&
        Objects.equals(this.unitCost, salesOrderItemResponse.unitCost) &&
        Objects.equals(this.totalCost, salesOrderItemResponse.totalCost) &&
        Objects.equals(this.grossProfit, salesOrderItemResponse.grossProfit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemId, itemName, costIncomplete, quantity, unitPrice, unitPricePf, unitPricePj, totalPrice, unitCost, totalCost, grossProfit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesOrderItemResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    costIncomplete: ").append(toIndentedString(costIncomplete)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("    unitPricePf: ").append(toIndentedString(unitPricePf)).append("\n");
    sb.append("    unitPricePj: ").append(toIndentedString(unitPricePj)).append("\n");
    sb.append("    totalPrice: ").append(toIndentedString(totalPrice)).append("\n");
    sb.append("    unitCost: ").append(toIndentedString(unitCost)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    grossProfit: ").append(toIndentedString(grossProfit)).append("\n");
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

