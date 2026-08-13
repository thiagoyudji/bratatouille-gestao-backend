package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.RecipeItemRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * RecipeCreateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class RecipeCreateRequest {

  private String name;

  private Long outputItemId;

  @Valid
  private List<@Valid RecipeItemRequest> items = new ArrayList<>();

  public RecipeCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RecipeCreateRequest(String name, Long outputItemId, List<@Valid RecipeItemRequest> items) {
    this.name = name;
    this.outputItemId = outputItemId;
    this.items = items;
  }

  public RecipeCreateRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RecipeCreateRequest outputItemId(Long outputItemId) {
    this.outputItemId = outputItemId;
    return this;
  }

  /**
   * Get outputItemId
   * @return outputItemId
   */
  @NotNull 
  @Schema(name = "outputItemId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("outputItemId")
  public Long getOutputItemId() {
    return outputItemId;
  }

  public void setOutputItemId(Long outputItemId) {
    this.outputItemId = outputItemId;
  }

  public RecipeCreateRequest items(List<@Valid RecipeItemRequest> items) {
    this.items = items;
    return this;
  }

  public RecipeCreateRequest addItemsItem(RecipeItemRequest itemsItem) {
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
  @NotNull @Valid 
  @Schema(name = "items", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<@Valid RecipeItemRequest> getItems() {
    return items;
  }

  public void setItems(List<@Valid RecipeItemRequest> items) {
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
    RecipeCreateRequest recipeCreateRequest = (RecipeCreateRequest) o;
    return Objects.equals(this.name, recipeCreateRequest.name) &&
        Objects.equals(this.outputItemId, recipeCreateRequest.outputItemId) &&
        Objects.equals(this.items, recipeCreateRequest.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, outputItemId, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecipeCreateRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    outputItemId: ").append(toIndentedString(outputItemId)).append("\n");
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

