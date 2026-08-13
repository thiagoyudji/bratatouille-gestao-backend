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
 * AdjustStockRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class AdjustStockRequest {

  private BigDecimal quantity;

  private @Nullable BigDecimal pricePf;

  private @Nullable BigDecimal pricePj;

  public AdjustStockRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AdjustStockRequest(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public AdjustStockRequest quantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
   */
  @NotNull @Valid 
  @Schema(name = "quantity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("quantity")
  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public AdjustStockRequest pricePf(@Nullable BigDecimal pricePf) {
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

  public AdjustStockRequest pricePj(@Nullable BigDecimal pricePj) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdjustStockRequest adjustStockRequest = (AdjustStockRequest) o;
    return Objects.equals(this.quantity, adjustStockRequest.quantity) &&
        Objects.equals(this.pricePf, adjustStockRequest.pricePf) &&
        Objects.equals(this.pricePj, adjustStockRequest.pricePj);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity, pricePf, pricePj);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdjustStockRequest {\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    pricePf: ").append(toIndentedString(pricePf)).append("\n");
    sb.append("    pricePj: ").append(toIndentedString(pricePj)).append("\n");
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

