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
 * SellableStockResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SellableStockResponse {

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable BigDecimal availableQuantity;

  private @Nullable BigDecimal currentStockQuantity;

  private @Nullable BigDecimal pricePf;

  private @Nullable BigDecimal pricePj;

  private @Nullable Boolean infinite;

  private @Nullable Boolean enabled;

  public SellableStockResponse itemId(@Nullable Long itemId) {
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

  public SellableStockResponse itemName(@Nullable String itemName) {
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

  public SellableStockResponse availableQuantity(@Nullable BigDecimal availableQuantity) {
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

  public SellableStockResponse currentStockQuantity(@Nullable BigDecimal currentStockQuantity) {
    this.currentStockQuantity = currentStockQuantity;
    return this;
  }

  /**
   * Get currentStockQuantity
   * @return currentStockQuantity
   */
  @Valid 
  @Schema(name = "currentStockQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currentStockQuantity")
  public @Nullable BigDecimal getCurrentStockQuantity() {
    return currentStockQuantity;
  }

  public void setCurrentStockQuantity(@Nullable BigDecimal currentStockQuantity) {
    this.currentStockQuantity = currentStockQuantity;
  }

  public SellableStockResponse pricePf(@Nullable BigDecimal pricePf) {
    this.pricePf = pricePf;
    return this;
  }

  /**
   * Get pricePf
   * @return pricePf
   */
  @Valid 
  @Schema(name = "pricePf", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pricePf")
  public @Nullable BigDecimal getPricePf() {
    return pricePf;
  }

  public void setPricePf(@Nullable BigDecimal pricePf) {
    this.pricePf = pricePf;
  }

  public SellableStockResponse pricePj(@Nullable BigDecimal pricePj) {
    this.pricePj = pricePj;
    return this;
  }

  /**
   * Get pricePj
   * @return pricePj
   */
  @Valid 
  @Schema(name = "pricePj", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pricePj")
  public @Nullable BigDecimal getPricePj() {
    return pricePj;
  }

  public void setPricePj(@Nullable BigDecimal pricePj) {
    this.pricePj = pricePj;
  }

  public SellableStockResponse infinite(@Nullable Boolean infinite) {
    this.infinite = infinite;
    return this;
  }

  /**
   * Get infinite
   * @return infinite
   */
  
  @Schema(name = "infinite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("infinite")
  public @Nullable Boolean getInfinite() {
    return infinite;
  }

  public void setInfinite(@Nullable Boolean infinite) {
    this.infinite = infinite;
  }

  public SellableStockResponse enabled(@Nullable Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   * @return enabled
   */
  
  @Schema(name = "enabled", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("enabled")
  public @Nullable Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(@Nullable Boolean enabled) {
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
    SellableStockResponse sellableStockResponse = (SellableStockResponse) o;
    return Objects.equals(this.itemId, sellableStockResponse.itemId) &&
        Objects.equals(this.itemName, sellableStockResponse.itemName) &&
        Objects.equals(this.availableQuantity, sellableStockResponse.availableQuantity) &&
        Objects.equals(this.currentStockQuantity, sellableStockResponse.currentStockQuantity) &&
        Objects.equals(this.pricePf, sellableStockResponse.pricePf) &&
        Objects.equals(this.pricePj, sellableStockResponse.pricePj) &&
        Objects.equals(this.infinite, sellableStockResponse.infinite) &&
        Objects.equals(this.enabled, sellableStockResponse.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, availableQuantity, currentStockQuantity, pricePf, pricePj, infinite, enabled);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SellableStockResponse {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    availableQuantity: ").append(toIndentedString(availableQuantity)).append("\n");
    sb.append("    currentStockQuantity: ").append(toIndentedString(currentStockQuantity)).append("\n");
    sb.append("    pricePf: ").append(toIndentedString(pricePf)).append("\n");
    sb.append("    pricePj: ").append(toIndentedString(pricePj)).append("\n");
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

