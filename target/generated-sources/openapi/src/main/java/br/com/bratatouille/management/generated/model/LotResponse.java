package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * LotResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class LotResponse {

  private @Nullable Long id;

  private @Nullable Long productionId;

  private @Nullable Long itemId;

  private @Nullable String itemName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate productionDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate expirationDate;

  private @Nullable BigDecimal quantity;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  public LotResponse id(@Nullable Long id) {
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

  public LotResponse productionId(@Nullable Long productionId) {
    this.productionId = productionId;
    return this;
  }

  /**
   * Get productionId
   * @return productionId
   */
  
  @Schema(name = "productionId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("productionId")
  public @Nullable Long getProductionId() {
    return productionId;
  }

  public void setProductionId(@Nullable Long productionId) {
    this.productionId = productionId;
  }

  public LotResponse itemId(@Nullable Long itemId) {
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

  public LotResponse itemName(@Nullable String itemName) {
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

  public LotResponse productionDate(@Nullable LocalDate productionDate) {
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

  public LotResponse expirationDate(@Nullable LocalDate expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  /**
   * Get expirationDate
   * @return expirationDate
   */
  @Valid 
  @Schema(name = "expirationDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expirationDate")
  public @Nullable LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(@Nullable LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  public LotResponse quantity(@Nullable BigDecimal quantity) {
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

  public LotResponse createdAt(@Nullable OffsetDateTime createdAt) {
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
    LotResponse lotResponse = (LotResponse) o;
    return Objects.equals(this.id, lotResponse.id) &&
        Objects.equals(this.productionId, lotResponse.productionId) &&
        Objects.equals(this.itemId, lotResponse.itemId) &&
        Objects.equals(this.itemName, lotResponse.itemName) &&
        Objects.equals(this.productionDate, lotResponse.productionDate) &&
        Objects.equals(this.expirationDate, lotResponse.expirationDate) &&
        Objects.equals(this.quantity, lotResponse.quantity) &&
        Objects.equals(this.createdAt, lotResponse.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productionId, itemId, itemName, productionDate, expirationDate, quantity, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LotResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    productionId: ").append(toIndentedString(productionId)).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    productionDate: ").append(toIndentedString(productionDate)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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

