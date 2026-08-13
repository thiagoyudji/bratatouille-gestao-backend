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
 * CashFlowEntryResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class CashFlowEntryResponse {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate date;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    PURCHASE("PURCHASE"),
    
    OPERATIONAL_COST("OPERATIONAL_COST");

    private final String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable TypeEnum type;

  private @Nullable Long sourceId;

  private @Nullable String description;

  private @Nullable Long partnerId;

  private @Nullable String partnerName;

  private @Nullable BigDecimal amount;

  /**
   * Gets or Sets direction
   */
  public enum DirectionEnum {
    OUT("OUT"),
    
    IN("IN");

    private final String value;

    DirectionEnum(String value) {
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
    public static DirectionEnum fromValue(String value) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable DirectionEnum direction;

  private @Nullable BigDecimal balanceAfter;

  public CashFlowEntryResponse date(@Nullable LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   */
  @Valid 
  @Schema(name = "date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("date")
  public @Nullable LocalDate getDate() {
    return date;
  }

  public void setDate(@Nullable LocalDate date) {
    this.date = date;
  }

  public CashFlowEntryResponse type(@Nullable TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public @Nullable TypeEnum getType() {
    return type;
  }

  public void setType(@Nullable TypeEnum type) {
    this.type = type;
  }

  public CashFlowEntryResponse sourceId(@Nullable Long sourceId) {
    this.sourceId = sourceId;
    return this;
  }

  /**
   * Get sourceId
   * @return sourceId
   */
  
  @Schema(name = "sourceId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceId")
  public @Nullable Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(@Nullable Long sourceId) {
    this.sourceId = sourceId;
  }

  public CashFlowEntryResponse description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public CashFlowEntryResponse partnerId(@Nullable Long partnerId) {
    this.partnerId = partnerId;
    return this;
  }

  /**
   * Get partnerId
   * @return partnerId
   */
  
  @Schema(name = "partnerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partnerId")
  public @Nullable Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(@Nullable Long partnerId) {
    this.partnerId = partnerId;
  }

  public CashFlowEntryResponse partnerName(@Nullable String partnerName) {
    this.partnerName = partnerName;
    return this;
  }

  /**
   * Get partnerName
   * @return partnerName
   */
  
  @Schema(name = "partnerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partnerName")
  public @Nullable String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(@Nullable String partnerName) {
    this.partnerName = partnerName;
  }

  public CashFlowEntryResponse amount(@Nullable BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   */
  @Valid 
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amount")
  public @Nullable BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(@Nullable BigDecimal amount) {
    this.amount = amount;
  }

  public CashFlowEntryResponse direction(@Nullable DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * Get direction
   * @return direction
   */
  
  @Schema(name = "direction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("direction")
  public @Nullable DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(@Nullable DirectionEnum direction) {
    this.direction = direction;
  }

  public CashFlowEntryResponse balanceAfter(@Nullable BigDecimal balanceAfter) {
    this.balanceAfter = balanceAfter;
    return this;
  }

  /**
   * Get balanceAfter
   * @return balanceAfter
   */
  @Valid 
  @Schema(name = "balanceAfter", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("balanceAfter")
  public @Nullable BigDecimal getBalanceAfter() {
    return balanceAfter;
  }

  public void setBalanceAfter(@Nullable BigDecimal balanceAfter) {
    this.balanceAfter = balanceAfter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CashFlowEntryResponse cashFlowEntryResponse = (CashFlowEntryResponse) o;
    return Objects.equals(this.date, cashFlowEntryResponse.date) &&
        Objects.equals(this.type, cashFlowEntryResponse.type) &&
        Objects.equals(this.sourceId, cashFlowEntryResponse.sourceId) &&
        Objects.equals(this.description, cashFlowEntryResponse.description) &&
        Objects.equals(this.partnerId, cashFlowEntryResponse.partnerId) &&
        Objects.equals(this.partnerName, cashFlowEntryResponse.partnerName) &&
        Objects.equals(this.amount, cashFlowEntryResponse.amount) &&
        Objects.equals(this.direction, cashFlowEntryResponse.direction) &&
        Objects.equals(this.balanceAfter, cashFlowEntryResponse.balanceAfter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, type, sourceId, description, partnerId, partnerName, amount, direction, balanceAfter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CashFlowEntryResponse {\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    sourceId: ").append(toIndentedString(sourceId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
    sb.append("    partnerName: ").append(toIndentedString(partnerName)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    balanceAfter: ").append(toIndentedString(balanceAfter)).append("\n");
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

