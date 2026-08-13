package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerAddress;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * Pedido persistido com snapshot de cliente, entrega e pagamento.
 */

@Schema(name = "SalesOrderResponse", description = "Pedido persistido com snapshot de cliente, entrega e pagamento.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SalesOrderResponse {

  private @Nullable Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate saleDate;

  private @Nullable SalesOrderCustomerType customerType;

  private @Nullable String customerName;

  private @Nullable String customerEmail;

  private @Nullable String customerPhone;

  private @Nullable SalesOrderCustomerAddress deliveryAddress;

  private @Nullable String note;

  /**
   * Gets or Sets paymentStatus
   */
  public enum PaymentStatusEnum {
    PENDING("PENDING"),
    
    APPROVED("APPROVED"),
    
    DECLINED("DECLINED"),
    
    CANCELED("CANCELED");

    private final String value;

    PaymentStatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PaymentStatusEnum fromValue(String value) {
      for (PaymentStatusEnum b : PaymentStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable PaymentStatusEnum paymentStatus;

  private @Nullable String paymentProvider;

  private @Nullable String paymentProviderTransactionId;

  private @Nullable String paymentProviderStatus;

  private @Nullable String paymentReceiptUrl;

  private @Nullable String paymentCheckoutUrl;

  private @Nullable String paymentInvoiceSlug;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime paidAt;

  private @Nullable BigDecimal totalAmount;

  private @Nullable BigDecimal totalCost;

  private @Nullable BigDecimal grossProfit;

  @Valid
  private List<@Valid SalesOrderItemResponse> items = new ArrayList<>();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  public SalesOrderResponse id(@Nullable Long id) {
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

  public SalesOrderResponse saleDate(@Nullable LocalDate saleDate) {
    this.saleDate = saleDate;
    return this;
  }

  /**
   * Get saleDate
   * @return saleDate
   */
  @Valid 
  @Schema(name = "saleDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("saleDate")
  public @Nullable LocalDate getSaleDate() {
    return saleDate;
  }

  public void setSaleDate(@Nullable LocalDate saleDate) {
    this.saleDate = saleDate;
  }

  public SalesOrderResponse customerType(@Nullable SalesOrderCustomerType customerType) {
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

  public SalesOrderResponse customerName(@Nullable String customerName) {
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

  public SalesOrderResponse customerEmail(@Nullable String customerEmail) {
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

  public SalesOrderResponse customerPhone(@Nullable String customerPhone) {
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

  public SalesOrderResponse deliveryAddress(@Nullable SalesOrderCustomerAddress deliveryAddress) {
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

  public SalesOrderResponse note(@Nullable String note) {
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

  public SalesOrderResponse paymentStatus(@Nullable PaymentStatusEnum paymentStatus) {
    this.paymentStatus = paymentStatus;
    return this;
  }

  /**
   * Get paymentStatus
   * @return paymentStatus
   */
  
  @Schema(name = "paymentStatus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentStatus")
  public @Nullable PaymentStatusEnum getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(@Nullable PaymentStatusEnum paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public SalesOrderResponse paymentProvider(@Nullable String paymentProvider) {
    this.paymentProvider = paymentProvider;
    return this;
  }

  /**
   * Get paymentProvider
   * @return paymentProvider
   */
  
  @Schema(name = "paymentProvider", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentProvider")
  public @Nullable String getPaymentProvider() {
    return paymentProvider;
  }

  public void setPaymentProvider(@Nullable String paymentProvider) {
    this.paymentProvider = paymentProvider;
  }

  public SalesOrderResponse paymentProviderTransactionId(@Nullable String paymentProviderTransactionId) {
    this.paymentProviderTransactionId = paymentProviderTransactionId;
    return this;
  }

  /**
   * Get paymentProviderTransactionId
   * @return paymentProviderTransactionId
   */
  
  @Schema(name = "paymentProviderTransactionId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentProviderTransactionId")
  public @Nullable String getPaymentProviderTransactionId() {
    return paymentProviderTransactionId;
  }

  public void setPaymentProviderTransactionId(@Nullable String paymentProviderTransactionId) {
    this.paymentProviderTransactionId = paymentProviderTransactionId;
  }

  public SalesOrderResponse paymentProviderStatus(@Nullable String paymentProviderStatus) {
    this.paymentProviderStatus = paymentProviderStatus;
    return this;
  }

  /**
   * Raw provider payment status captured from the webhook.
   * @return paymentProviderStatus
   */
  
  @Schema(name = "paymentProviderStatus", description = "Raw provider payment status captured from the webhook.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentProviderStatus")
  public @Nullable String getPaymentProviderStatus() {
    return paymentProviderStatus;
  }

  public void setPaymentProviderStatus(@Nullable String paymentProviderStatus) {
    this.paymentProviderStatus = paymentProviderStatus;
  }

  public SalesOrderResponse paymentReceiptUrl(@Nullable String paymentReceiptUrl) {
    this.paymentReceiptUrl = paymentReceiptUrl;
    return this;
  }

  /**
   * Provider receipt URL when available.
   * @return paymentReceiptUrl
   */
  
  @Schema(name = "paymentReceiptUrl", description = "Provider receipt URL when available.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentReceiptUrl")
  public @Nullable String getPaymentReceiptUrl() {
    return paymentReceiptUrl;
  }

  public void setPaymentReceiptUrl(@Nullable String paymentReceiptUrl) {
    this.paymentReceiptUrl = paymentReceiptUrl;
  }

  public SalesOrderResponse paymentCheckoutUrl(@Nullable String paymentCheckoutUrl) {
    this.paymentCheckoutUrl = paymentCheckoutUrl;
    return this;
  }

  /**
   * Provider checkout URL used to create the payment link.
   * @return paymentCheckoutUrl
   */
  
  @Schema(name = "paymentCheckoutUrl", description = "Provider checkout URL used to create the payment link.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentCheckoutUrl")
  public @Nullable String getPaymentCheckoutUrl() {
    return paymentCheckoutUrl;
  }

  public void setPaymentCheckoutUrl(@Nullable String paymentCheckoutUrl) {
    this.paymentCheckoutUrl = paymentCheckoutUrl;
  }

  public SalesOrderResponse paymentInvoiceSlug(@Nullable String paymentInvoiceSlug) {
    this.paymentInvoiceSlug = paymentInvoiceSlug;
    return this;
  }

  /**
   * Provider invoice slug returned by InfinitePay for reconciliation.
   * @return paymentInvoiceSlug
   */
  
  @Schema(name = "paymentInvoiceSlug", description = "Provider invoice slug returned by InfinitePay for reconciliation.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentInvoiceSlug")
  public @Nullable String getPaymentInvoiceSlug() {
    return paymentInvoiceSlug;
  }

  public void setPaymentInvoiceSlug(@Nullable String paymentInvoiceSlug) {
    this.paymentInvoiceSlug = paymentInvoiceSlug;
  }

  public SalesOrderResponse paidAt(@Nullable OffsetDateTime paidAt) {
    this.paidAt = paidAt;
    return this;
  }

  /**
   * Get paidAt
   * @return paidAt
   */
  @Valid 
  @Schema(name = "paidAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paidAt")
  public @Nullable OffsetDateTime getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(@Nullable OffsetDateTime paidAt) {
    this.paidAt = paidAt;
  }

  public SalesOrderResponse totalAmount(@Nullable BigDecimal totalAmount) {
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

  public SalesOrderResponse totalCost(@Nullable BigDecimal totalCost) {
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

  public SalesOrderResponse grossProfit(@Nullable BigDecimal grossProfit) {
    this.grossProfit = grossProfit;
    return this;
  }

  /**
   * Get grossProfit
   * @return grossProfit
   */
  @Valid 
  @Schema(name = "grossProfit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("grossProfit")
  public @Nullable BigDecimal getGrossProfit() {
    return grossProfit;
  }

  public void setGrossProfit(@Nullable BigDecimal grossProfit) {
    this.grossProfit = grossProfit;
  }

  public SalesOrderResponse items(List<@Valid SalesOrderItemResponse> items) {
    this.items = items;
    return this;
  }

  public SalesOrderResponse addItemsItem(SalesOrderItemResponse itemsItem) {
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
  public List<@Valid SalesOrderItemResponse> getItems() {
    return items;
  }

  public void setItems(List<@Valid SalesOrderItemResponse> items) {
    this.items = items;
  }

  public SalesOrderResponse createdAt(@Nullable OffsetDateTime createdAt) {
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
    SalesOrderResponse salesOrderResponse = (SalesOrderResponse) o;
    return Objects.equals(this.id, salesOrderResponse.id) &&
        Objects.equals(this.saleDate, salesOrderResponse.saleDate) &&
        Objects.equals(this.customerType, salesOrderResponse.customerType) &&
        Objects.equals(this.customerName, salesOrderResponse.customerName) &&
        Objects.equals(this.customerEmail, salesOrderResponse.customerEmail) &&
        Objects.equals(this.customerPhone, salesOrderResponse.customerPhone) &&
        Objects.equals(this.deliveryAddress, salesOrderResponse.deliveryAddress) &&
        Objects.equals(this.note, salesOrderResponse.note) &&
        Objects.equals(this.paymentStatus, salesOrderResponse.paymentStatus) &&
        Objects.equals(this.paymentProvider, salesOrderResponse.paymentProvider) &&
        Objects.equals(this.paymentProviderTransactionId, salesOrderResponse.paymentProviderTransactionId) &&
        Objects.equals(this.paymentProviderStatus, salesOrderResponse.paymentProviderStatus) &&
        Objects.equals(this.paymentReceiptUrl, salesOrderResponse.paymentReceiptUrl) &&
        Objects.equals(this.paymentCheckoutUrl, salesOrderResponse.paymentCheckoutUrl) &&
        Objects.equals(this.paymentInvoiceSlug, salesOrderResponse.paymentInvoiceSlug) &&
        Objects.equals(this.paidAt, salesOrderResponse.paidAt) &&
        Objects.equals(this.totalAmount, salesOrderResponse.totalAmount) &&
        Objects.equals(this.totalCost, salesOrderResponse.totalCost) &&
        Objects.equals(this.grossProfit, salesOrderResponse.grossProfit) &&
        Objects.equals(this.items, salesOrderResponse.items) &&
        Objects.equals(this.createdAt, salesOrderResponse.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, saleDate, customerType, customerName, customerEmail, customerPhone, deliveryAddress, note, paymentStatus, paymentProvider, paymentProviderTransactionId, paymentProviderStatus, paymentReceiptUrl, paymentCheckoutUrl, paymentInvoiceSlug, paidAt, totalAmount, totalCost, grossProfit, items, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesOrderResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    saleDate: ").append(toIndentedString(saleDate)).append("\n");
    sb.append("    customerType: ").append(toIndentedString(customerType)).append("\n");
    sb.append("    customerName: ").append(toIndentedString(customerName)).append("\n");
    sb.append("    customerEmail: ").append(toIndentedString(customerEmail)).append("\n");
    sb.append("    customerPhone: ").append(toIndentedString(customerPhone)).append("\n");
    sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    paymentStatus: ").append(toIndentedString(paymentStatus)).append("\n");
    sb.append("    paymentProvider: ").append(toIndentedString(paymentProvider)).append("\n");
    sb.append("    paymentProviderTransactionId: ").append(toIndentedString(paymentProviderTransactionId)).append("\n");
    sb.append("    paymentProviderStatus: ").append(toIndentedString(paymentProviderStatus)).append("\n");
    sb.append("    paymentReceiptUrl: ").append(toIndentedString(paymentReceiptUrl)).append("\n");
    sb.append("    paymentCheckoutUrl: ").append(toIndentedString(paymentCheckoutUrl)).append("\n");
    sb.append("    paymentInvoiceSlug: ").append(toIndentedString(paymentInvoiceSlug)).append("\n");
    sb.append("    paidAt: ").append(toIndentedString(paidAt)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    grossProfit: ").append(toIndentedString(grossProfit)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

