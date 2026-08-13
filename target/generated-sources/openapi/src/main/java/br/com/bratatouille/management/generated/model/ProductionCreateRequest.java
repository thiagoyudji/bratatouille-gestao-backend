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
 * ProductionCreateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ProductionCreateRequest {

  private Long recipeId;

  private BigDecimal producedQuantity;

  public ProductionCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductionCreateRequest(Long recipeId, BigDecimal producedQuantity) {
    this.recipeId = recipeId;
    this.producedQuantity = producedQuantity;
  }

  public ProductionCreateRequest recipeId(Long recipeId) {
    this.recipeId = recipeId;
    return this;
  }

  /**
   * Get recipeId
   * @return recipeId
   */
  @NotNull 
  @Schema(name = "recipeId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("recipeId")
  public Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Long recipeId) {
    this.recipeId = recipeId;
  }

  public ProductionCreateRequest producedQuantity(BigDecimal producedQuantity) {
    this.producedQuantity = producedQuantity;
    return this;
  }

  /**
   * Get producedQuantity
   * @return producedQuantity
   */
  @NotNull @Valid 
  @Schema(name = "producedQuantity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("producedQuantity")
  public BigDecimal getProducedQuantity() {
    return producedQuantity;
  }

  public void setProducedQuantity(BigDecimal producedQuantity) {
    this.producedQuantity = producedQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductionCreateRequest productionCreateRequest = (ProductionCreateRequest) o;
    return Objects.equals(this.recipeId, productionCreateRequest.recipeId) &&
        Objects.equals(this.producedQuantity, productionCreateRequest.producedQuantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipeId, producedQuantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductionCreateRequest {\n");
    sb.append("    recipeId: ").append(toIndentedString(recipeId)).append("\n");
    sb.append("    producedQuantity: ").append(toIndentedString(producedQuantity)).append("\n");
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

