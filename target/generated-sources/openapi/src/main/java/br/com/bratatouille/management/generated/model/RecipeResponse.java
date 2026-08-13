package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.RecipeItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * RecipeResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class RecipeResponse {

  private @Nullable Long id;

  private @Nullable String name;

  private @Nullable Long outputItemId;

  private @Nullable String outputItemName;

  private @Nullable Boolean active;

  @Valid
  private List<@Valid RecipeItemResponse> items = new ArrayList<>();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  public RecipeResponse id(@Nullable Long id) {
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

  public RecipeResponse name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public RecipeResponse outputItemId(@Nullable Long outputItemId) {
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

  public RecipeResponse outputItemName(@Nullable String outputItemName) {
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

  public RecipeResponse active(@Nullable Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
   */
  
  @Schema(name = "active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("active")
  public @Nullable Boolean getActive() {
    return active;
  }

  public void setActive(@Nullable Boolean active) {
    this.active = active;
  }

  public RecipeResponse items(List<@Valid RecipeItemResponse> items) {
    this.items = items;
    return this;
  }

  public RecipeResponse addItemsItem(RecipeItemResponse itemsItem) {
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
  public List<@Valid RecipeItemResponse> getItems() {
    return items;
  }

  public void setItems(List<@Valid RecipeItemResponse> items) {
    this.items = items;
  }

  public RecipeResponse createdAt(@Nullable OffsetDateTime createdAt) {
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

  public RecipeResponse updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecipeResponse recipeResponse = (RecipeResponse) o;
    return Objects.equals(this.id, recipeResponse.id) &&
        Objects.equals(this.name, recipeResponse.name) &&
        Objects.equals(this.outputItemId, recipeResponse.outputItemId) &&
        Objects.equals(this.outputItemName, recipeResponse.outputItemName) &&
        Objects.equals(this.active, recipeResponse.active) &&
        Objects.equals(this.items, recipeResponse.items) &&
        Objects.equals(this.createdAt, recipeResponse.createdAt) &&
        Objects.equals(this.updatedAt, recipeResponse.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, outputItemId, outputItemName, active, items, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecipeResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    outputItemId: ").append(toIndentedString(outputItemId)).append("\n");
    sb.append("    outputItemName: ").append(toIndentedString(outputItemName)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

