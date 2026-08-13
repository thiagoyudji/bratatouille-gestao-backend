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
 * RecipeItemResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class RecipeItemResponse {

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable BigDecimal quantity;

  private @Nullable BigDecimal yieldPercentage;

  private @Nullable String unit;

  public RecipeItemResponse itemId(@Nullable Long itemId) {
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

  public RecipeItemResponse itemName(@Nullable String itemName) {
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

  public RecipeItemResponse quantity(@Nullable BigDecimal quantity) {
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

  public RecipeItemResponse yieldPercentage(@Nullable BigDecimal yieldPercentage) {
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

  public RecipeItemResponse unit(@Nullable String unit) {
    this.unit = unit;
    return this;
  }

  /**
   * Get unit
   * @return unit
   */
  
  @Schema(name = "unit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unit")
  public @Nullable String getUnit() {
    return unit;
  }

  public void setUnit(@Nullable String unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecipeItemResponse recipeItemResponse = (RecipeItemResponse) o;
    return Objects.equals(this.itemId, recipeItemResponse.itemId) &&
        Objects.equals(this.itemName, recipeItemResponse.itemName) &&
        Objects.equals(this.quantity, recipeItemResponse.quantity) &&
        Objects.equals(this.yieldPercentage, recipeItemResponse.yieldPercentage) &&
        Objects.equals(this.unit, recipeItemResponse.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, quantity, yieldPercentage, unit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecipeItemResponse {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    yieldPercentage: ").append(toIndentedString(yieldPercentage)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
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

