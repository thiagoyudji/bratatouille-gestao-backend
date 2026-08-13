package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.ProductionSimulationItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductionSimulationResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ProductionSimulationResponse {

  private @Nullable Long recipeId;

  private @Nullable String recipeName;

  private @Nullable Long outputItemId;

  private @Nullable String outputItemName;

  private @Nullable BigDecimal quantity;

  private @Nullable BigDecimal estimatedTotalCost;

  @Valid
  private List<@Valid ProductionSimulationItemResponse> items = new ArrayList<>();

  public ProductionSimulationResponse recipeId(@Nullable Long recipeId) {
    this.recipeId = recipeId;
    return this;
  }

  /**
   * Get recipeId
   * @return recipeId
   */
  
  @Schema(name = "recipeId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recipeId")
  public @Nullable Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(@Nullable Long recipeId) {
    this.recipeId = recipeId;
  }

  public ProductionSimulationResponse recipeName(@Nullable String recipeName) {
    this.recipeName = recipeName;
    return this;
  }

  /**
   * Get recipeName
   * @return recipeName
   */
  
  @Schema(name = "recipeName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recipeName")
  public @Nullable String getRecipeName() {
    return recipeName;
  }

  public void setRecipeName(@Nullable String recipeName) {
    this.recipeName = recipeName;
  }

  public ProductionSimulationResponse outputItemId(@Nullable Long outputItemId) {
    this.outputItemId = outputItemId;
    return this;
  }

  /**
   * Get outputItemId
   * @return outputItemId
   */
  
  @Schema(name = "outputItemId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("outputItemId")
  public @Nullable Long getOutputItemId() {
    return outputItemId;
  }

  public void setOutputItemId(@Nullable Long outputItemId) {
    this.outputItemId = outputItemId;
  }

  public ProductionSimulationResponse outputItemName(@Nullable String outputItemName) {
    this.outputItemName = outputItemName;
    return this;
  }

  /**
   * Get outputItemName
   * @return outputItemName
   */
  
  @Schema(name = "outputItemName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("outputItemName")
  public @Nullable String getOutputItemName() {
    return outputItemName;
  }

  public void setOutputItemName(@Nullable String outputItemName) {
    this.outputItemName = outputItemName;
  }

  public ProductionSimulationResponse quantity(@Nullable BigDecimal quantity) {
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

  public ProductionSimulationResponse estimatedTotalCost(@Nullable BigDecimal estimatedTotalCost) {
    this.estimatedTotalCost = estimatedTotalCost;
    return this;
  }

  /**
   * Get estimatedTotalCost
   * @return estimatedTotalCost
   */
  @Valid 
  @Schema(name = "estimatedTotalCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("estimatedTotalCost")
  public @Nullable BigDecimal getEstimatedTotalCost() {
    return estimatedTotalCost;
  }

  public void setEstimatedTotalCost(@Nullable BigDecimal estimatedTotalCost) {
    this.estimatedTotalCost = estimatedTotalCost;
  }

  public ProductionSimulationResponse items(List<@Valid ProductionSimulationItemResponse> items) {
    this.items = items;
    return this;
  }

  public ProductionSimulationResponse addItemsItem(ProductionSimulationItemResponse itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   */
  @Valid 
  @Schema(name = "items", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("items")
  public List<@Valid ProductionSimulationItemResponse> getItems() {
    return items;
  }

  public void setItems(List<@Valid ProductionSimulationItemResponse> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductionSimulationResponse productionSimulationResponse = (ProductionSimulationResponse) o;
    return Objects.equals(this.recipeId, productionSimulationResponse.recipeId) &&
        Objects.equals(this.recipeName, productionSimulationResponse.recipeName) &&
        Objects.equals(this.outputItemId, productionSimulationResponse.outputItemId) &&
        Objects.equals(this.outputItemName, productionSimulationResponse.outputItemName) &&
        Objects.equals(this.quantity, productionSimulationResponse.quantity) &&
        Objects.equals(this.estimatedTotalCost, productionSimulationResponse.estimatedTotalCost) &&
        Objects.equals(this.items, productionSimulationResponse.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipeId, recipeName, outputItemId, outputItemName, quantity, estimatedTotalCost, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductionSimulationResponse {\n");
    sb.append("    recipeId: ").append(toIndentedString(recipeId)).append("\n");
    sb.append("    recipeName: ").append(toIndentedString(recipeName)).append("\n");
    sb.append("    outputItemId: ").append(toIndentedString(outputItemId)).append("\n");
    sb.append("    outputItemName: ").append(toIndentedString(outputItemName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    estimatedTotalCost: ").append(toIndentedString(estimatedTotalCost)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

