package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.OperationalCostSplitResponse;
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
 * OperationalCostResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class OperationalCostResponse {

  private @Nullable Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate costDate;

  /**
   * Gets or Sets category
   */
  public enum CategoryEnum {
    FIXED("FIXED"),
    
    VARIABLE("VARIABLE"),
    
    FINANCIAL("FINANCIAL");

    private final String value;

    CategoryEnum(String value) {
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
    public static CategoryEnum fromValue(String value) {
      for (CategoryEnum b : CategoryEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable CategoryEnum category;

  private @Nullable Long paidByPartnerId;

  private @Nullable String paidByPartnerName;

  private @Nullable BigDecimal amount;

  private @Nullable String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @Valid
  private List<@Valid OperationalCostSplitResponse> splits = new ArrayList<>();

  public OperationalCostResponse id(@Nullable Long id) {
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

  public OperationalCostResponse costDate(@Nullable LocalDate costDate) {
    this.costDate = costDate;
    return this;
  }

  /**
   * Get costDate
   * @return costDate
   */
  @Valid 
  @Schema(name = "costDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("costDate")
  public @Nullable LocalDate getCostDate() {
    return costDate;
  }

  public void setCostDate(@Nullable LocalDate costDate) {
    this.costDate = costDate;
  }

  public OperationalCostResponse category(@Nullable CategoryEnum category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
   */
  
  @Schema(name = "category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("category")
  public @Nullable CategoryEnum getCategory() {
    return category;
  }

  public void setCategory(@Nullable CategoryEnum category) {
    this.category = category;
  }

  public OperationalCostResponse paidByPartnerId(@Nullable Long paidByPartnerId) {
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

  public OperationalCostResponse paidByPartnerName(@Nullable String paidByPartnerName) {
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

  public OperationalCostResponse amount(@Nullable BigDecimal amount) {
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

  public OperationalCostResponse description(@Nullable String description) {
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

  public OperationalCostResponse createdAt(@Nullable OffsetDateTime createdAt) {
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

  public OperationalCostResponse splits(List<@Valid OperationalCostSplitResponse> splits) {
    this.splits = splits;
    return this;
  }

  public OperationalCostResponse addSplitsItem(OperationalCostSplitResponse splitsItem) {
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
  public List<@Valid OperationalCostSplitResponse> getSplits() {
    return splits;
  }

  public void setSplits(List<@Valid OperationalCostSplitResponse> splits) {
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
    OperationalCostResponse operationalCostResponse = (OperationalCostResponse) o;
    return Objects.equals(this.id, operationalCostResponse.id) &&
        Objects.equals(this.costDate, operationalCostResponse.costDate) &&
        Objects.equals(this.category, operationalCostResponse.category) &&
        Objects.equals(this.paidByPartnerId, operationalCostResponse.paidByPartnerId) &&
        Objects.equals(this.paidByPartnerName, operationalCostResponse.paidByPartnerName) &&
        Objects.equals(this.amount, operationalCostResponse.amount) &&
        Objects.equals(this.description, operationalCostResponse.description) &&
        Objects.equals(this.createdAt, operationalCostResponse.createdAt) &&
        Objects.equals(this.splits, operationalCostResponse.splits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, costDate, category, paidByPartnerId, paidByPartnerName, amount, description, createdAt, splits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationalCostResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    costDate: ").append(toIndentedString(costDate)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    paidByPartnerId: ").append(toIndentedString(paidByPartnerId)).append("\n");
    sb.append("    paidByPartnerName: ").append(toIndentedString(paidByPartnerName)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

