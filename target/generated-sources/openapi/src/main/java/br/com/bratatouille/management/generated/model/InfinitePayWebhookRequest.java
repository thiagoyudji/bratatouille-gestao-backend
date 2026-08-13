package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Payload sent by the InfinitePay integrated checkout after an approved payment.
 */

@Schema(name = "InfinitePayWebhookRequest", description = "Payload sent by the InfinitePay integrated checkout after an approved payment.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class InfinitePayWebhookRequest {

  private String invoiceSlug;

  private Long amount;

  private Long paidAmount;

  private @Nullable Integer installments;

  private @Nullable String captureMethod;

  private String transactionNsu;

  private String orderNsu;

  private @Nullable String receiptUrl;

  public InfinitePayWebhookRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public InfinitePayWebhookRequest(String invoiceSlug, Long amount, Long paidAmount, String transactionNsu, String orderNsu) {
    this.invoiceSlug = invoiceSlug;
    this.amount = amount;
    this.paidAmount = paidAmount;
    this.transactionNsu = transactionNsu;
    this.orderNsu = orderNsu;
  }

  public InfinitePayWebhookRequest invoiceSlug(String invoiceSlug) {
    this.invoiceSlug = invoiceSlug;
    return this;
  }

  /**
   * InfinitePay invoice identifier.
   * @return invoiceSlug
   */
  @NotNull 
  @Schema(name = "invoice_slug", description = "InfinitePay invoice identifier.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("invoice_slug")
  public String getInvoiceSlug() {
    return invoiceSlug;
  }

  public void setInvoiceSlug(String invoiceSlug) {
    this.invoiceSlug = invoiceSlug;
  }

  public InfinitePayWebhookRequest amount(Long amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Order amount in cents.
   * minimum: 1
   * @return amount
   */
  @NotNull @Min(1L) 
  @Schema(name = "amount", description = "Order amount in cents.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public InfinitePayWebhookRequest paidAmount(Long paidAmount) {
    this.paidAmount = paidAmount;
    return this;
  }

  /**
   * Amount effectively paid in cents, which may include financing costs.
   * minimum: 1
   * @return paidAmount
   */
  @NotNull @Min(1L) 
  @Schema(name = "paid_amount", description = "Amount effectively paid in cents, which may include financing costs.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("paid_amount")
  public Long getPaidAmount() {
    return paidAmount;
  }

  public void setPaidAmount(Long paidAmount) {
    this.paidAmount = paidAmount;
  }

  public InfinitePayWebhookRequest installments(@Nullable Integer installments) {
    this.installments = installments;
    return this;
  }

  /**
   * Get installments
   * minimum: 1
   * @return installments
   */
  @Min(1) 
  @Schema(name = "installments", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("installments")
  public @Nullable Integer getInstallments() {
    return installments;
  }

  public void setInstallments(@Nullable Integer installments) {
    this.installments = installments;
  }

  public InfinitePayWebhookRequest captureMethod(@Nullable String captureMethod) {
    this.captureMethod = captureMethod;
    return this;
  }

  /**
   * Payment method reported by the provider.
   * @return captureMethod
   */
  
  @Schema(name = "capture_method", description = "Payment method reported by the provider.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("capture_method")
  public @Nullable String getCaptureMethod() {
    return captureMethod;
  }

  public void setCaptureMethod(@Nullable String captureMethod) {
    this.captureMethod = captureMethod;
  }

  public InfinitePayWebhookRequest transactionNsu(String transactionNsu) {
    this.transactionNsu = transactionNsu;
    return this;
  }

  /**
   * Unique provider transaction identifier.
   * @return transactionNsu
   */
  @NotNull 
  @Schema(name = "transaction_nsu", description = "Unique provider transaction identifier.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("transaction_nsu")
  public String getTransactionNsu() {
    return transactionNsu;
  }

  public void setTransactionNsu(String transactionNsu) {
    this.transactionNsu = transactionNsu;
  }

  public InfinitePayWebhookRequest orderNsu(String orderNsu) {
    this.orderNsu = orderNsu;
    return this;
  }

  /**
   * Internal sales order identifier sent when the checkout was created.
   * @return orderNsu
   */
  @NotNull 
  @Schema(name = "order_nsu", description = "Internal sales order identifier sent when the checkout was created.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("order_nsu")
  public String getOrderNsu() {
    return orderNsu;
  }

  public void setOrderNsu(String orderNsu) {
    this.orderNsu = orderNsu;
  }

  public InfinitePayWebhookRequest receiptUrl(@Nullable String receiptUrl) {
    this.receiptUrl = receiptUrl;
    return this;
  }

  /**
   * Provider receipt URL when available.
   * @return receiptUrl
   */
  
  @Schema(name = "receipt_url", description = "Provider receipt URL when available.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("receipt_url")
  public @Nullable String getReceiptUrl() {
    return receiptUrl;
  }

  public void setReceiptUrl(@Nullable String receiptUrl) {
    this.receiptUrl = receiptUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfinitePayWebhookRequest infinitePayWebhookRequest = (InfinitePayWebhookRequest) o;
    return Objects.equals(this.invoiceSlug, infinitePayWebhookRequest.invoiceSlug) &&
        Objects.equals(this.amount, infinitePayWebhookRequest.amount) &&
        Objects.equals(this.paidAmount, infinitePayWebhookRequest.paidAmount) &&
        Objects.equals(this.installments, infinitePayWebhookRequest.installments) &&
        Objects.equals(this.captureMethod, infinitePayWebhookRequest.captureMethod) &&
        Objects.equals(this.transactionNsu, infinitePayWebhookRequest.transactionNsu) &&
        Objects.equals(this.orderNsu, infinitePayWebhookRequest.orderNsu) &&
        Objects.equals(this.receiptUrl, infinitePayWebhookRequest.receiptUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invoiceSlug, amount, paidAmount, installments, captureMethod, transactionNsu, orderNsu, receiptUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfinitePayWebhookRequest {\n");
    sb.append("    invoiceSlug: ").append(toIndentedString(invoiceSlug)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    paidAmount: ").append(toIndentedString(paidAmount)).append("\n");
    sb.append("    installments: ").append(toIndentedString(installments)).append("\n");
    sb.append("    captureMethod: ").append(toIndentedString(captureMethod)).append("\n");
    sb.append("    transactionNsu: ").append(toIndentedString(transactionNsu)).append("\n");
    sb.append("    orderNsu: ").append(toIndentedString(orderNsu)).append("\n");
    sb.append("    receiptUrl: ").append(toIndentedString(receiptUrl)).append("\n");
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

