package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerAddress;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * Pedido criado no e-commerce. No checkout público, o backend força guest quando não há autenticação e deriva PF/PJ do perfil autenticado quando há cliente logado. Guest e PF usam preço PF; PJ usa preço PJ. O pedido nasce como PENDING até a confirmação do pagamento.
 */

@Schema(name = "SalesOrderCreateRequest", description = "Pedido criado no e-commerce. No checkout público, o backend força guest quando não há autenticação e deriva PF/PJ do perfil autenticado quando há cliente logado. Guest e PF usam preço PF; PJ usa preço PJ. O pedido nasce como PENDING até a confirmação do pagamento.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SalesOrderCreateRequest {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate saleDate;

  private @Nullable SalesOrderCustomerType customerType;

  private @Nullable String customerName;

  private @Nullable String customerEmail;

  private @Nullable String customerPhone;

  private @Nullable SalesOrderCustomerAddress deliveryAddress;

  private @Nullable String note;

  @Valid
  private List<@Valid SalesOrderItemRequest> items = new ArrayList<>();

  public SalesOrderCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SalesOrderCreateRequest(LocalDate saleDate, List<@Valid SalesOrderItemRequest> items) {
    this.saleDate = saleDate;
    this.items = items;
  }

  public SalesOrderCreateRequest saleDate(LocalDate saleDate) {
    this.saleDate = saleDate;
    return this;
  }

  /**
   * Get saleDate
   * @return saleDate
   */
  @NotNull @Valid 
  @Schema(name = "saleDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("saleDate")
  public LocalDate getSaleDate() {
    return saleDate;
  }

  public void setSaleDate(LocalDate saleDate) {
    this.saleDate = saleDate;
  }

  public SalesOrderCreateRequest customerType(@Nullable SalesOrderCustomerType customerType) {
    this.customerType = customerType;
    return this;
  }

  /**
   * Get customerType
   * @return customerType
   */
  @Valid 
  @Schema(name = "customerType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerType")
  public @Nullable SalesOrderCustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(@Nullable SalesOrderCustomerType customerType) {
    this.customerType = customerType;
  }

  public SalesOrderCreateRequest customerName(@Nullable String customerName) {
    this.customerName = customerName;
    return this;
  }

  /**
   * Get customerName
   * @return customerName
   */
  
  @Schema(name = "customerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerName")
  public @Nullable String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(@Nullable String customerName) {
    this.customerName = customerName;
  }

  public SalesOrderCreateRequest customerEmail(@Nullable String customerEmail) {
    this.customerEmail = customerEmail;
    return this;
  }

  /**
   * Get customerEmail
   * @return customerEmail
   */
  
  @Schema(name = "customerEmail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerEmail")
  public @Nullable String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(@Nullable String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public SalesOrderCreateRequest customerPhone(@Nullable String customerPhone) {
    this.customerPhone = customerPhone;
    return this;
  }

  /**
   * Get customerPhone
   * @return customerPhone
   */
  
  @Schema(name = "customerPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerPhone")
  public @Nullable String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(@Nullable String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public SalesOrderCreateRequest deliveryAddress(@Nullable SalesOrderCustomerAddress deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    return this;
  }

  /**
   * Get deliveryAddress
   * @return deliveryAddress
   */
  @Valid 
  @Schema(name = "deliveryAddress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deliveryAddress")
  public @Nullable SalesOrderCustomerAddress getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(@Nullable SalesOrderCustomerAddress deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public SalesOrderCreateRequest note(@Nullable String note) {
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

  public SalesOrderCreateRequest items(List<@Valid SalesOrderItemRequest> items) {
    this.items = items;
    return this;
  }

  public SalesOrderCreateRequest addItemsItem(SalesOrderItemRequest itemsItem) {
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
  public List<@Valid SalesOrderItemRequest> getItems() {
    return items;
  }

  public void setItems(List<@Valid SalesOrderItemRequest> items) {
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
    SalesOrderCreateRequest salesOrderCreateRequest = (SalesOrderCreateRequest) o;
    return Objects.equals(this.saleDate, salesOrderCreateRequest.saleDate) &&
        Objects.equals(this.customerType, salesOrderCreateRequest.customerType) &&
        Objects.equals(this.customerName, salesOrderCreateRequest.customerName) &&
        Objects.equals(this.customerEmail, salesOrderCreateRequest.customerEmail) &&
        Objects.equals(this.customerPhone, salesOrderCreateRequest.customerPhone) &&
        Objects.equals(this.deliveryAddress, salesOrderCreateRequest.deliveryAddress) &&
        Objects.equals(this.note, salesOrderCreateRequest.note) &&
        Objects.equals(this.items, salesOrderCreateRequest.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(saleDate, customerType, customerName, customerEmail, customerPhone, deliveryAddress, note, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesOrderCreateRequest {\n");
    sb.append("    saleDate: ").append(toIndentedString(saleDate)).append("\n");
    sb.append("    customerType: ").append(toIndentedString(customerType)).append("\n");
    sb.append("    customerName: ").append(toIndentedString(customerName)).append("\n");
    sb.append("    customerEmail: ").append(toIndentedString(customerEmail)).append("\n");
    sb.append("    customerPhone: ").append(toIndentedString(customerPhone)).append("\n");
    sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
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

