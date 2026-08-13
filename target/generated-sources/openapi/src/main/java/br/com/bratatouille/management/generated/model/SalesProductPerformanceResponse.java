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
 * Product performance grouped by item, using the real amount sold for PF/PJ orders.
 */

@Schema(name = "SalesProductPerformanceResponse", description = "Product performance grouped by item, using the real amount sold for PF/PJ orders.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SalesProductPerformanceResponse {

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable BigDecimal soldQuantity;

  private @Nullable BigDecimal totalAmount;

  private @Nullable BigDecimal totalCost;

  private @Nullable BigDecimal grossProfit;

  private @Nullable BigDecimal grossMarginPercentage;

  public SalesProductPerformanceResponse itemId(@Nullable Long itemId) {
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

  public SalesProductPerformanceResponse itemName(@Nullable String itemName) {
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

  public SalesProductPerformanceResponse soldQuantity(@Nullable BigDecimal soldQuantity) {
    this.soldQuantity = soldQuantity;
    return this;
  }

  /**
   * Get soldQuantity
   * @return soldQuantity
   */
  @Valid 
  @Schema(name = "soldQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("soldQuantity")
  public @Nullable BigDecimal getSoldQuantity() {
    return soldQuantity;
  }

  public void setSoldQuantity(@Nullable BigDecimal soldQuantity) {
    this.soldQuantity = soldQuantity;
  }

  public SalesProductPerformanceResponse totalAmount(@Nullable BigDecimal totalAmount) {
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

  public SalesProductPerformanceResponse totalCost(@Nullable BigDecimal totalCost) {
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

  public SalesProductPerformanceResponse grossProfit(@Nullable BigDecimal grossProfit) {
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

  public SalesProductPerformanceResponse grossMarginPercentage(@Nullable BigDecimal grossMarginPercentage) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalesProductPerformanceResponse salesProductPerformanceResponse = (SalesProductPerformanceResponse) o;
    return Objects.equals(this.itemId, salesProductPerformanceResponse.itemId) &&
        Objects.equals(this.itemName, salesProductPerformanceResponse.itemName) &&
        Objects.equals(this.soldQuantity, salesProductPerformanceResponse.soldQuantity) &&
        Objects.equals(this.totalAmount, salesProductPerformanceResponse.totalAmount) &&
        Objects.equals(this.totalCost, salesProductPerformanceResponse.totalCost) &&
        Objects.equals(this.grossProfit, salesProductPerformanceResponse.grossProfit) &&
        Objects.equals(this.grossMarginPercentage, salesProductPerformanceResponse.grossMarginPercentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, soldQuantity, totalAmount, totalCost, grossProfit, grossMarginPercentage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesProductPerformanceResponse {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    soldQuantity: ").append(toIndentedString(soldQuantity)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    grossProfit: ").append(toIndentedString(grossProfit)).append("\n");
    sb.append("    grossMarginPercentage: ").append(toIndentedString(grossMarginPercentage)).append("\n");
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

