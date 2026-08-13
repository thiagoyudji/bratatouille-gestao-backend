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
 * OperationalLossResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class OperationalLossResponse {

  private @Nullable Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate lossDate;

  private @Nullable Long itemId;

  private @Nullable String itemName;

  private @Nullable BigDecimal quantity;

  private @Nullable String reason;

  private @Nullable BigDecimal unitCost;

  private @Nullable BigDecimal totalCost;

  private @Nullable String note;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  public OperationalLossResponse id(@Nullable Long id) {
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

  public OperationalLossResponse lossDate(@Nullable LocalDate lossDate) {
    this.lossDate = lossDate;
    return this;
  }

  /**
   * Get lossDate
   * @return lossDate
   */
  @Valid 
  @Schema(name = "lossDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lossDate")
  public @Nullable LocalDate getLossDate() {
    return lossDate;
  }

  public void setLossDate(@Nullable LocalDate lossDate) {
    this.lossDate = lossDate;
  }

  public OperationalLossResponse itemId(@Nullable Long itemId) {
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

  public OperationalLossResponse itemName(@Nullable String itemName) {
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

  public OperationalLossResponse quantity(@Nullable BigDecimal quantity) {
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

  public OperationalLossResponse reason(@Nullable String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
   */
  
  @Schema(name = "reason", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reason")
  public @Nullable String getReason() {
    return reason;
  }

  public void setReason(@Nullable String reason) {
    this.reason = reason;
  }

  public OperationalLossResponse unitCost(@Nullable BigDecimal unitCost) {
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

  public OperationalLossResponse totalCost(@Nullable BigDecimal totalCost) {
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

  public OperationalLossResponse note(@Nullable String note) {
    this.note = note;
    return this;
  }

  /**
   * Get note
   * @return note
   */
  
  @Schema(name = "note", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("note")
  public @Nullable String getNote() {
    return note;
  }

  public void setNote(@Nullable String note) {
    this.note = note;
  }

  public OperationalLossResponse createdAt(@Nullable OffsetDateTime createdAt) {
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
    OperationalLossResponse operationalLossResponse = (OperationalLossResponse) o;
    return Objects.equals(this.id, operationalLossResponse.id) &&
        Objects.equals(this.lossDate, operationalLossResponse.lossDate) &&
        Objects.equals(this.itemId, operationalLossResponse.itemId) &&
        Objects.equals(this.itemName, operationalLossResponse.itemName) &&
        Objects.equals(this.quantity, operationalLossResponse.quantity) &&
        Objects.equals(this.reason, operationalLossResponse.reason) &&
        Objects.equals(this.unitCost, operationalLossResponse.unitCost) &&
        Objects.equals(this.totalCost, operationalLossResponse.totalCost) &&
        Objects.equals(this.note, operationalLossResponse.note) &&
        Objects.equals(this.createdAt, operationalLossResponse.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lossDate, itemId, itemName, quantity, reason, unitCost, totalCost, note, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationalLossResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lossDate: ").append(toIndentedString(lossDate)).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    unitCost: ").append(toIndentedString(unitCost)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
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

