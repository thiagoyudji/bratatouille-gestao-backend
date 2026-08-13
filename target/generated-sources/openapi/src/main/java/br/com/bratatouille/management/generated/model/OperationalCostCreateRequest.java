package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.OperationalCostSplitRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
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
 * OperationalCostCreateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class OperationalCostCreateRequest {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate costDate;

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

  private CategoryEnum category;

  private Long paidByPartnerId;

  private BigDecimal amount;

  private @Nullable String description;

  @Valid
  private List<@Valid OperationalCostSplitRequest> splits = new ArrayList<>();

  public OperationalCostCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OperationalCostCreateRequest(LocalDate costDate, CategoryEnum category, Long paidByPartnerId, BigDecimal amount, List<@Valid OperationalCostSplitRequest> splits) {
    this.costDate = costDate;
    this.category = category;
    this.paidByPartnerId = paidByPartnerId;
    this.amount = amount;
    this.splits = splits;
  }

  public OperationalCostCreateRequest costDate(LocalDate costDate) {
    this.costDate = costDate;
    return this;
  }

  /**
   * Get costDate
   * @return costDate
   */
  @NotNull @Valid 
  @Schema(name = "costDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("costDate")
  public LocalDate getCostDate() {
    return costDate;
  }

  public void setCostDate(LocalDate costDate) {
    this.costDate = costDate;
  }

  public OperationalCostCreateRequest category(CategoryEnum category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
   */
  @NotNull 
  @Schema(name = "category", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("category")
  public CategoryEnum getCategory() {
    return category;
  }

  public void setCategory(CategoryEnum category) {
    this.category = category;
  }

  public OperationalCostCreateRequest paidByPartnerId(Long paidByPartnerId) {
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

  public OperationalCostCreateRequest amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public OperationalCostCreateRequest description(@Nullable String description) {
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

  public OperationalCostCreateRequest splits(List<@Valid OperationalCostSplitRequest> splits) {
    this.splits = splits;
    return this;
  }

  public OperationalCostCreateRequest addSplitsItem(OperationalCostSplitRequest splitsItem) {
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
  @NotNull @Valid 
  @Schema(name = "splits", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("splits")
  public List<@Valid OperationalCostSplitRequest> getSplits() {
    return splits;
  }

  public void setSplits(List<@Valid OperationalCostSplitRequest> splits) {
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
    OperationalCostCreateRequest operationalCostCreateRequest = (OperationalCostCreateRequest) o;
    return Objects.equals(this.costDate, operationalCostCreateRequest.costDate) &&
        Objects.equals(this.category, operationalCostCreateRequest.category) &&
        Objects.equals(this.paidByPartnerId, operationalCostCreateRequest.paidByPartnerId) &&
        Objects.equals(this.amount, operationalCostCreateRequest.amount) &&
        Objects.equals(this.description, operationalCostCreateRequest.description) &&
        Objects.equals(this.splits, operationalCostCreateRequest.splits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(costDate, category, paidByPartnerId, amount, description, splits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationalCostCreateRequest {\n");
    sb.append("    costDate: ").append(toIndentedString(costDate)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    paidByPartnerId: ").append(toIndentedString(paidByPartnerId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

