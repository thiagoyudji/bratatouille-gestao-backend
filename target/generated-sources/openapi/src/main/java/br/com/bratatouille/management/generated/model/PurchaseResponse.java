package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.PurchaseItemResponse;
import br.com.bratatouille.management.generated.model.PurchaseSplitResponse;
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
 * PurchaseResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PurchaseResponse {

  private @Nullable Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate purchaseDate;

  private @Nullable String supplier;

  private @Nullable String note;

  private @Nullable Long paidByPartnerId;

  private @Nullable String paidByPartnerName;

  private @Nullable BigDecimal totalAmount;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @Valid
  private List<@Valid PurchaseItemResponse> items = new ArrayList<>();

  @Valid
  private List<@Valid PurchaseSplitResponse> splits = new ArrayList<>();

  public PurchaseResponse id(@Nullable Long id) {
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

  public PurchaseResponse purchaseDate(@Nullable LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
    return this;
  }

  /**
   * Get purchaseDate
   * @return purchaseDate
   */
  @Valid 
  @Schema(name = "purchaseDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("purchaseDate")
  public @Nullable LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(@Nullable LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public PurchaseResponse supplier(@Nullable String supplier) {
    this.supplier = supplier;
    return this;
  }

  /**
   * Get supplier
   * @return supplier
   */
  
  @Schema(name = "supplier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supplier")
  public @Nullable String getSupplier() {
    return supplier;
  }

  public void setSupplier(@Nullable String supplier) {
    this.supplier = supplier;
  }

  public PurchaseResponse note(@Nullable String note) {
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

  public PurchaseResponse paidByPartnerId(@Nullable Long paidByPartnerId) {
    this.paidByPartnerId = paidByPartnerId;
    return this;
  }

  /**
   * Get paidByPartnerId
   * @return paidByPartnerId
   */
  
  @Schema(name = "paidByPartnerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paidByPartnerId")
  public @Nullable Long getPaidByPartnerId() {
    return paidByPartnerId;
  }

  public void setPaidByPartnerId(@Nullable Long paidByPartnerId) {
    this.paidByPartnerId = paidByPartnerId;
  }

  public PurchaseResponse paidByPartnerName(@Nullable String paidByPartnerName) {
    this.paidByPartnerName = paidByPartnerName;
    return this;
  }

  /**
   * Get paidByPartnerName
   * @return paidByPartnerName
   */
  
  @Schema(name = "paidByPartnerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paidByPartnerName")
  public @Nullable String getPaidByPartnerName() {
    return paidByPartnerName;
  }

  public void setPaidByPartnerName(@Nullable String paidByPartnerName) {
    this.paidByPartnerName = paidByPartnerName;
  }

  public PurchaseResponse totalAmount(@Nullable BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * Get totalAmount
   * @return totalAmount
   */
  @Valid 
  @Schema(name = "totalAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalAmount")
  public @Nullable BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(@Nullable BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public PurchaseResponse createdAt(@Nullable OffsetDateTime createdAt) {
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

  public PurchaseResponse items(List<@Valid PurchaseItemResponse> items) {
    this.items = items;
    return this;
  }

  public PurchaseResponse addItemsItem(PurchaseItemResponse itemsItem) {
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
  public List<@Valid PurchaseItemResponse> getItems() {
    return items;
  }

  public void setItems(List<@Valid PurchaseItemResponse> items) {
    this.items = items;
  }

  public PurchaseResponse splits(List<@Valid PurchaseSplitResponse> splits) {
    this.splits = splits;
    return this;
  }

  public PurchaseResponse addSplitsItem(PurchaseSplitResponse splitsItem) {
    if (this.splits == null) {
      this.splits = new ArrayList<>();
    }
    this.splits.add(splitsItem);
    return this;
  }

  /**
   * Get splits
   * @return splits
   */
  @Valid 
  @Schema(name = "splits", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("splits")
  public List<@Valid PurchaseSplitResponse> getSplits() {
    return splits;
  }

  public void setSplits(List<@Valid PurchaseSplitResponse> splits) {
    this.splits = splits;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PurchaseResponse purchaseResponse = (PurchaseResponse) o;
    return Objects.equals(this.id, purchaseResponse.id) &&
        Objects.equals(this.purchaseDate, purchaseResponse.purchaseDate) &&
        Objects.equals(this.supplier, purchaseResponse.supplier) &&
        Objects.equals(this.note, purchaseResponse.note) &&
        Objects.equals(this.paidByPartnerId, purchaseResponse.paidByPartnerId) &&
        Objects.equals(this.paidByPartnerName, purchaseResponse.paidByPartnerName) &&
        Objects.equals(this.totalAmount, purchaseResponse.totalAmount) &&
        Objects.equals(this.createdAt, purchaseResponse.createdAt) &&
        Objects.equals(this.items, purchaseResponse.items) &&
        Objects.equals(this.splits, purchaseResponse.splits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, purchaseDate, supplier, note, paidByPartnerId, paidByPartnerName, totalAmount, createdAt, items, splits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PurchaseResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    purchaseDate: ").append(toIndentedString(purchaseDate)).append("\n");
    sb.append("    supplier: ").append(toIndentedString(supplier)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    paidByPartnerId: ").append(toIndentedString(paidByPartnerId)).append("\n");
    sb.append("    paidByPartnerName: ").append(toIndentedString(paidByPartnerName)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    splits: ").append(toIndentedString(splits)).append("\n");
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

