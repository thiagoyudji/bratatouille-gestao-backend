package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OperationalLossCreateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class OperationalLossCreateRequest {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate lossDate;

  private Long itemId;

  private BigDecimal quantity;

  /**
   * Gets or Sets reason
   */
  public enum ReasonEnum {
    DAMAGE("DAMAGE"),
    
    OPERATIONAL("OPERATIONAL"),
    
    EXPIRED("EXPIRED");

    private final String value;

    ReasonEnum(String value) {
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
    public static ReasonEnum fromValue(String value) {
      for (ReasonEnum b : ReasonEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ReasonEnum reason;

  private @Nullable String note;

  public OperationalLossCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OperationalLossCreateRequest(LocalDate lossDate, Long itemId, BigDecimal quantity, ReasonEnum reason) {
    this.lossDate = lossDate;
    this.itemId = itemId;
    this.quantity = quantity;
    this.reason = reason;
  }

  public OperationalLossCreateRequest lossDate(LocalDate lossDate) {
    this.lossDate = lossDate;
    return this;
  }

  /**
   * Get lossDate
   * @return lossDate
   */
  @NotNull @Valid 
  @Schema(name = "lossDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lossDate")
  public LocalDate getLossDate() {
    return lossDate;
  }

  public void setLossDate(LocalDate lossDate) {
    this.lossDate = lossDate;
  }

  public OperationalLossCreateRequest itemId(Long itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
   */
  @NotNull 
  @Schema(name = "itemId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("itemId")
  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public OperationalLossCreateRequest quantity(BigDecimal quantity) {
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

  public OperationalLossCreateRequest reason(ReasonEnum reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
   */
  @NotNull 
  @Schema(name = "reason", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reason")
  public ReasonEnum getReason() {
    return reason;
  }

  public void setReason(ReasonEnum reason) {
    this.reason = reason;
  }

  public OperationalLossCreateRequest note(@Nullable String note) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationalLossCreateRequest operationalLossCreateRequest = (OperationalLossCreateRequest) o;
    return Objects.equals(this.lossDate, operationalLossCreateRequest.lossDate) &&
        Objects.equals(this.itemId, operationalLossCreateRequest.itemId) &&
        Objects.equals(this.quantity, operationalLossCreateRequest.quantity) &&
        Objects.equals(this.reason, operationalLossCreateRequest.reason) &&
        Objects.equals(this.note, operationalLossCreateRequest.note);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lossDate, itemId, quantity, reason, note);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationalLossCreateRequest {\n");
    sb.append("    lossDate: ").append(toIndentedString(lossDate)).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
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

