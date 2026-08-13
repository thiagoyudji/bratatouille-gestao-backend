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
 * RecipeItemRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class RecipeItemRequest {

  private Long itemId;

  private BigDecimal quantity;

  private BigDecimal yieldPercentage = new BigDecimal("1");

  public RecipeItemRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RecipeItemRequest(Long itemId, BigDecimal quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

  public RecipeItemRequest itemId(Long itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
   */
  @NotNull 
  @Schema(name = "itemId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("itemId")
  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public RecipeItemRequest quantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
   */
  @NotNull @Valid 
  @Schema(name = "quantity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("quantity")
  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public RecipeItemRequest yieldPercentage(BigDecimal yieldPercentage) {
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
  public BigDecimal getYieldPercentage() {
    return yieldPercentage;
  }

  public void setYieldPercentage(BigDecimal yieldPercentage) {
    this.yieldPercentage = yieldPercentage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecipeItemRequest recipeItemRequest = (RecipeItemRequest) o;
    return Objects.equals(this.itemId, recipeItemRequest.itemId) &&
        Objects.equals(this.quantity, recipeItemRequest.quantity) &&
        Objects.equals(this.yieldPercentage, recipeItemRequest.yieldPercentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, quantity, yieldPercentage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecipeItemRequest {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    yieldPercentage: ").append(toIndentedString(yieldPercentage)).append("\n");
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

