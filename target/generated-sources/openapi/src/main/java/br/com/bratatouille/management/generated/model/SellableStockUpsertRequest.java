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
 * SellableStockUpsertRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SellableStockUpsertRequest {

  private @Nullable BigDecimal availableQuantity;

  private Boolean infinite;

  private Boolean enabled;

  public SellableStockUpsertRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SellableStockUpsertRequest(Boolean infinite, Boolean enabled) {
    this.infinite = infinite;
    this.enabled = enabled;
  }

  public SellableStockUpsertRequest availableQuantity(@Nullable BigDecimal availableQuantity) {
    this.availableQuantity = availableQuantity;
    return this;
  }

  /**
   * Get availableQuantity
   * @return availableQuantity
   */
  @Valid 
  @Schema(name = "availableQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("availableQuantity")
  public @Nullable BigDecimal getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(@Nullable BigDecimal availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public SellableStockUpsertRequest infinite(Boolean infinite) {
    this.infinite = infinite;
    return this;
  }

  /**
   * Get infinite
   * @return infinite
   */
  @NotNull 
  @Schema(name = "infinite", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("infinite")
  public Boolean getInfinite() {
    return infinite;
  }

  public void setInfinite(Boolean infinite) {
    this.infinite = infinite;
  }

  public SellableStockUpsertRequest enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   * @return enabled
   */
  @NotNull 
  @Schema(name = "enabled", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SellableStockUpsertRequest sellableStockUpsertRequest = (SellableStockUpsertRequest) o;
    return Objects.equals(this.availableQuantity, sellableStockUpsertRequest.availableQuantity) &&
        Objects.equals(this.infinite, sellableStockUpsertRequest.infinite) &&
        Objects.equals(this.enabled, sellableStockUpsertRequest.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availableQuantity, infinite, enabled);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SellableStockUpsertRequest {\n");
    sb.append("    availableQuantity: ").append(toIndentedString(availableQuantity)).append("\n");
    sb.append("    infinite: ").append(toIndentedString(infinite)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
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

