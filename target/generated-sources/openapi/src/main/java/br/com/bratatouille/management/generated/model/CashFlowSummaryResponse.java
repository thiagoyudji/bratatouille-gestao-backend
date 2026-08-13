package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.CashFlowEntryResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * CashFlowSummaryResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class CashFlowSummaryResponse {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate endDate;

  private @Nullable BigDecimal openingBalance;

  private @Nullable BigDecimal totalIn;

  private @Nullable BigDecimal totalOut;

  private @Nullable BigDecimal closingBalance;

  @Valid
  private List<@Valid CashFlowEntryResponse> entries = new ArrayList<>();

  public CashFlowSummaryResponse startDate(@Nullable LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
   */
  @Valid 
  @Schema(name = "startDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startDate")
  public @Nullable LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(@Nullable LocalDate startDate) {
    this.startDate = startDate;
  }

  public CashFlowSummaryResponse endDate(@Nullable LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
   */
  @Valid 
  @Schema(name = "endDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endDate")
  public @Nullable LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(@Nullable LocalDate endDate) {
    this.endDate = endDate;
  }

  public CashFlowSummaryResponse openingBalance(@Nullable BigDecimal openingBalance) {
    this.openingBalance = openingBalance;
    return this;
  }

  /**
   * Get openingBalance
   * @return openingBalance
   */
  @Valid 
  @Schema(name = "openingBalance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("openingBalance")
  public @Nullable BigDecimal getOpeningBalance() {
    return openingBalance;
  }

  public void setOpeningBalance(@Nullable BigDecimal openingBalance) {
    this.openingBalance = openingBalance;
  }

  public CashFlowSummaryResponse totalIn(@Nullable BigDecimal totalIn) {
    this.totalIn = totalIn;
    return this;
  }

  /**
   * Get totalIn
   * @return totalIn
   */
  @Valid 
  @Schema(name = "totalIn", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalIn")
  public @Nullable BigDecimal getTotalIn() {
    return totalIn;
  }

  public void setTotalIn(@Nullable BigDecimal totalIn) {
    this.totalIn = totalIn;
  }

  public CashFlowSummaryResponse totalOut(@Nullable BigDecimal totalOut) {
    this.totalOut = totalOut;
    return this;
  }

  /**
   * Get totalOut
   * @return totalOut
   */
  @Valid 
  @Schema(name = "totalOut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalOut")
  public @Nullable BigDecimal getTotalOut() {
    return totalOut;
  }

  public void setTotalOut(@Nullable BigDecimal totalOut) {
    this.totalOut = totalOut;
  }

  public CashFlowSummaryResponse closingBalance(@Nullable BigDecimal closingBalance) {
    this.closingBalance = closingBalance;
    return this;
  }

  /**
   * Get closingBalance
   * @return closingBalance
   */
  @Valid 
  @Schema(name = "closingBalance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("closingBalance")
  public @Nullable BigDecimal getClosingBalance() {
    return closingBalance;
  }

  public void setClosingBalance(@Nullable BigDecimal closingBalance) {
    this.closingBalance = closingBalance;
  }

  public CashFlowSummaryResponse entries(List<@Valid CashFlowEntryResponse> entries) {
    this.entries = entries;
    return this;
  }

  public CashFlowSummaryResponse addEntriesItem(CashFlowEntryResponse entriesItem) {
    if (this.entries == null) {
      this.entries = new ArrayList<>();
    }
    this.entries.add(entriesItem);
    return this;
  }

  /**
   * Get entries
   * @return entries
   */
  @Valid 
  @Schema(name = "entries", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("entries")
  public List<@Valid CashFlowEntryResponse> getEntries() {
    return entries;
  }

  public void setEntries(List<@Valid CashFlowEntryResponse> entries) {
    this.entries = entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CashFlowSummaryResponse cashFlowSummaryResponse = (CashFlowSummaryResponse) o;
    return Objects.equals(this.startDate, cashFlowSummaryResponse.startDate) &&
        Objects.equals(this.endDate, cashFlowSummaryResponse.endDate) &&
        Objects.equals(this.openingBalance, cashFlowSummaryResponse.openingBalance) &&
        Objects.equals(this.totalIn, cashFlowSummaryResponse.totalIn) &&
        Objects.equals(this.totalOut, cashFlowSummaryResponse.totalOut) &&
        Objects.equals(this.closingBalance, cashFlowSummaryResponse.closingBalance) &&
        Objects.equals(this.entries, cashFlowSummaryResponse.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, openingBalance, totalIn, totalOut, closingBalance, entries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CashFlowSummaryResponse {\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    openingBalance: ").append(toIndentedString(openingBalance)).append("\n");
    sb.append("    totalIn: ").append(toIndentedString(totalIn)).append("\n");
    sb.append("    totalOut: ").append(toIndentedString(totalOut)).append("\n");
    sb.append("    closingBalance: ").append(toIndentedString(closingBalance)).append("\n");
    sb.append("    entries: ").append(toIndentedString(entries)).append("\n");
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

