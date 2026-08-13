package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.ProductionItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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
 * ProductionResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ProductionResponse {

  private @Nullable Long id;

  private @Nullable Long recipeId;

  private @Nullable String recipeName;

  private @Nullable Long outputItemId;

  private @Nullable String outputItemName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate productionDate;

  private @Nullable BigDecimal producedQuantity;

  private @Nullable BigDecimal totalCost;

  private @Nullable BigDecimal unitCost;

  private @Nullable Long lotId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate lotExpirationDate;

  @Valid
  private List<@Valid ProductionItemResponse> items = new ArrayList<>();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  public ProductionResponse id(@Nullable Long id) {
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

  public ProductionResponse recipeId(@Nullable Long recipeId) {
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

  public ProductionResponse recipeName(@Nullable String recipeName) {
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

  public ProductionResponse outputItemId(@Nullable Long outputItemId) {
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

  public ProductionResponse outputItemName(@Nullable String outputItemName) {
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

  public ProductionResponse productionDate(@Nullable LocalDate productionDate) {
    this.productionDate = productionDate;
    return this;
  }

  /**
   * Get productionDate
   * @return productionDate
   */
  @Valid 
  @Schema(name = "productionDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("productionDate")
  public @Nullable LocalDate getProductionDate() {
    return productionDate;
  }

  public void setProductionDate(@Nullable LocalDate productionDate) {
    this.productionDate = productionDate;
  }

  public ProductionResponse producedQuantity(@Nullable BigDecimal producedQuantity) {
    this.producedQuantity = producedQuantity;
    return this;
  }

  /**
   * Get producedQuantity
   * @return producedQuantity
   */
  @Valid 
  @Schema(name = "producedQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("producedQuantity")
  public @Nullable BigDecimal getProducedQuantity() {
    return producedQuantity;
  }

  public void setProducedQuantity(@Nullable BigDecimal producedQuantity) {
    this.producedQuantity = producedQuantity;
  }

  public ProductionResponse totalCost(@Nullable BigDecimal totalCost) {
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

  public ProductionResponse unitCost(@Nullable BigDecimal unitCost) {
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

  public ProductionResponse lotId(@Nullable Long lotId) {
    this.lotId = lotId;
    return this;
  }

  /**
   * Get lotId
   * @return lotId
   */
  
  @Schema(name = "lotId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lotId")
  public @Nullable Long getLotId() {
    return lotId;
  }

  public void setLotId(@Nullable Long lotId) {
    this.lotId = lotId;
  }

  public ProductionResponse lotExpirationDate(@Nullable LocalDate lotExpirationDate) {
    this.lotExpirationDate = lotExpirationDate;
    return this;
  }

  /**
   * Get lotExpirationDate
   * @return lotExpirationDate
   */
  @Valid 
  @Schema(name = "lotExpirationDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lotExpirationDate")
  public @Nullable LocalDate getLotExpirationDate() {
    return lotExpirationDate;
  }

  public void setLotExpirationDate(@Nullable LocalDate lotExpirationDate) {
    this.lotExpirationDate = lotExpirationDate;
  }

  public ProductionResponse items(List<@Valid ProductionItemResponse> items) {
    this.items = items;
    return this;
  }

  public ProductionResponse addItemsItem(ProductionItemResponse itemsItem) {
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
  public List<@Valid ProductionItemResponse> getItems() {
    return items;
  }

  public void setItems(List<@Valid ProductionItemResponse> items) {
    this.items = items;
  }

  public ProductionResponse createdAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public @Nullable OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductionResponse productionResponse = (ProductionResponse) o;
    return Objects.equals(this.id, productionResponse.id) &&
        Objects.equals(this.recipeId, productionResponse.recipeId) &&
        Objects.equals(this.recipeName, productionResponse.recipeName) &&
        Objects.equals(this.outputItemId, productionResponse.outputItemId) &&
        Objects.equals(this.outputItemName, productionResponse.outputItemName) &&
        Objects.equals(this.productionDate, productionResponse.productionDate) &&
        Objects.equals(this.producedQuantity, productionResponse.producedQuantity) &&
        Objects.equals(this.totalCost, productionResponse.totalCost) &&
        Objects.equals(this.unitCost, productionResponse.unitCost) &&
        Objects.equals(this.lotId, productionResponse.lotId) &&
        Objects.equals(this.lotExpirationDate, productionResponse.lotExpirationDate) &&
        Objects.equals(this.items, productionResponse.items) &&
        Objects.equals(this.createdAt, productionResponse.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, recipeId, recipeName, outputItemId, outputItemName, productionDate, producedQuantity, totalCost, unitCost, lotId, lotExpirationDate, items, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductionResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    recipeId: ").append(toIndentedString(recipeId)).append("\n");
    sb.append("    recipeName: ").append(toIndentedString(recipeName)).append("\n");
    sb.append("    outputItemId: ").append(toIndentedString(outputItemId)).append("\n");
    sb.append("    outputItemName: ").append(toIndentedString(outputItemName)).append("\n");
    sb.append("    productionDate: ").append(toIndentedString(productionDate)).append("\n");
    sb.append("    producedQuantity: ").append(toIndentedString(producedQuantity)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    unitCost: ").append(toIndentedString(unitCost)).append("\n");
    sb.append("    lotId: ").append(toIndentedString(lotId)).append("\n");
    sb.append("    lotExpirationDate: ").append(toIndentedString(lotExpirationDate)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

