package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
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
 * PurchaseCreateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PurchaseCreateRequest {

  private Long paidByPartnerId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate purchaseDate;

  private String supplier;

  private @Nullable String note;

  @Valid
  private List<@Valid PurchaseItemRequest> items = new ArrayList<>();

  @Valid
  private List<@Valid PurchaseSplitRequest> splits = new ArrayList<>();

  public PurchaseCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PurchaseCreateRequest(Long paidByPartnerId, LocalDate purchaseDate, String supplier, List<@Valid PurchaseItemRequest> items) {
    this.paidByPartnerId = paidByPartnerId;
    this.purchaseDate = purchaseDate;
    this.supplier = supplier;
    this.items = items;
  }

  public PurchaseCreateRequest paidByPartnerId(Long paidByPartnerId) {
    this.paidByPartnerId = paidByPartnerId;
    return this;
  }

  /**
   * Get paidByPartnerId
   * @return paidByPartnerId
   */
  @NotNull 
  @Schema(name = "paidByPartnerId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("paidByPartnerId")
  public Long getPaidByPartnerId() {
    return paidByPartnerId;
  }

  public void setPaidByPartnerId(Long paidByPartnerId) {
    this.paidByPartnerId = paidByPartnerId;
  }

  public PurchaseCreateRequest purchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
    return this;
  }

  /**
   * Get purchaseDate
   * @return purchaseDate
   */
  @NotNull @Valid 
  @Schema(name = "purchaseDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("purchaseDate")
  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public PurchaseCreateRequest supplier(String supplier) {
    this.supplier = supplier;
    return this;
  }

  /**
   * Get supplier
   * @return supplier
   */
  @NotNull 
  @Schema(name = "supplier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("supplier")
  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public PurchaseCreateRequest note(@Nullable String note) {
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

  public PurchaseCreateRequest items(List<@Valid PurchaseItemRequest> items) {
    this.items = items;
    return this;
  }

  public PurchaseCreateRequest addItemsItem(PurchaseItemRequest itemsItem) {
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
  public List<@Valid PurchaseItemRequest> getItems() {
    return items;
  }

  public void setItems(List<@Valid PurchaseItemRequest> items) {
    this.items = items;
  }

  public PurchaseCreateRequest splits(List<@Valid PurchaseSplitRequest> splits) {
    this.splits = splits;
    return this;
  }

  public PurchaseCreateRequest addSplitsItem(PurchaseSplitRequest splitsItem) {
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
  public List<@Valid PurchaseSplitRequest> getSplits() {
    return splits;
  }

  public void setSplits(List<@Valid PurchaseSplitRequest> splits) {
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
    PurchaseCreateRequest purchaseCreateRequest = (PurchaseCreateRequest) o;
    return Objects.equals(this.paidByPartnerId, purchaseCreateRequest.paidByPartnerId) &&
        Objects.equals(this.purchaseDate, purchaseCreateRequest.purchaseDate) &&
        Objects.equals(this.supplier, purchaseCreateRequest.supplier) &&
        Objects.equals(this.note, purchaseCreateRequest.note) &&
        Objects.equals(this.items, purchaseCreateRequest.items) &&
        Objects.equals(this.splits, purchaseCreateRequest.splits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paidByPartnerId, purchaseDate, supplier, note, items, splits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PurchaseCreateRequest {\n");
    sb.append("    paidByPartnerId: ").append(toIndentedString(paidByPartnerId)).append("\n");
    sb.append("    purchaseDate: ").append(toIndentedString(purchaseDate)).append("\n");
    sb.append("    supplier: ").append(toIndentedString(supplier)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
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

