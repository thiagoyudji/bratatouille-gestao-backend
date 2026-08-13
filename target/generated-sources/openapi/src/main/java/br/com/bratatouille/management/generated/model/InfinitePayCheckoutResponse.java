package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Checkout created for the InfinitePay provider, linked to a pending sales order.
 */

@Schema(name = "InfinitePayCheckoutResponse", description = "Checkout created for the InfinitePay provider, linked to a pending sales order.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class InfinitePayCheckoutResponse {

  private @Nullable String provider;

  private @Nullable Long orderId;

  private @Nullable String checkoutUrl;

  private @Nullable String invoiceSlug;

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

  public InfinitePayCheckoutResponse provider(@Nullable String provider) {
    this.provider = provider;
    return this;
  }

  /**
   * Get provider
   * @return provider
   */
  
  @Schema(name = "provider", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("provider")
  public @Nullable String getProvider() {
    return provider;
  }

  public void setProvider(@Nullable String provider) {
    this.provider = provider;
  }

  public InfinitePayCheckoutResponse orderId(@Nullable Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
   */
  
  @Schema(name = "orderId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderId")
  public @Nullable Long getOrderId() {
    return orderId;
  }

  public void setOrderId(@Nullable Long orderId) {
    this.orderId = orderId;
  }

  public InfinitePayCheckoutResponse checkoutUrl(@Nullable String checkoutUrl) {
    this.checkoutUrl = checkoutUrl;
    return this;
  }

  /**
   * Get checkoutUrl
   * @return checkoutUrl
   */
  
  @Schema(name = "checkoutUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("checkoutUrl")
  public @Nullable String getCheckoutUrl() {
    return checkoutUrl;
  }

  public void setCheckoutUrl(@Nullable String checkoutUrl) {
    this.checkoutUrl = checkoutUrl;
  }

  public InfinitePayCheckoutResponse invoiceSlug(@Nullable String invoiceSlug) {
    this.invoiceSlug = invoiceSlug;
    return this;
  }

  /**
   * Get invoiceSlug
   * @return invoiceSlug
   */
  
  @Schema(name = "invoiceSlug", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("invoiceSlug")
  public @Nullable String getInvoiceSlug() {
    return invoiceSlug;
  }

  public void setInvoiceSlug(@Nullable String invoiceSlug) {
    this.invoiceSlug = invoiceSlug;
  }

  public InfinitePayCheckoutResponse paymentStatus(@Nullable PaymentStatusEnum paymentStatus) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfinitePayCheckoutResponse infinitePayCheckoutResponse = (InfinitePayCheckoutResponse) o;
    return Objects.equals(this.provider, infinitePayCheckoutResponse.provider) &&
        Objects.equals(this.orderId, infinitePayCheckoutResponse.orderId) &&
        Objects.equals(this.checkoutUrl, infinitePayCheckoutResponse.checkoutUrl) &&
        Objects.equals(this.invoiceSlug, infinitePayCheckoutResponse.invoiceSlug) &&
        Objects.equals(this.paymentStatus, infinitePayCheckoutResponse.paymentStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(provider, orderId, checkoutUrl, invoiceSlug, paymentStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfinitePayCheckoutResponse {\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    checkoutUrl: ").append(toIndentedString(checkoutUrl)).append("\n");
    sb.append("    invoiceSlug: ").append(toIndentedString(invoiceSlug)).append("\n");
    sb.append("    paymentStatus: ").append(toIndentedString(paymentStatus)).append("\n");
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

