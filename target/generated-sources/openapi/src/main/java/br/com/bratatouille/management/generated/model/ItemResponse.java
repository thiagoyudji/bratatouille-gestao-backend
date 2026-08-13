package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Item persistido com os preços de venda PF e PJ.
 */

@Schema(name = "ItemResponse", description = "Item persistido com os preços de venda PF e PJ.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class ItemResponse {

  private @Nullable Long id;

  private @Nullable String name;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    INGREDIENT("INGREDIENT"),
    
    PACKAGING("PACKAGING"),
    
    FINISHED_PRODUCT("FINISHED_PRODUCT");

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

  /**
   * Gets or Sets baseUnit
   */
  public enum BaseUnitEnum {
    G("G"),
    
    ML("ML"),
    
    UN("UN");

    private final String value;

    BaseUnitEnum(String value) {
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
    public static BaseUnitEnum fromValue(String value) {
      for (BaseUnitEnum b : BaseUnitEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable BaseUnitEnum baseUnit;

  private @Nullable Boolean active;

  private @Nullable BigDecimal lowStockThreshold;

  private @Nullable BigDecimal criticalStockThreshold;

  private @Nullable BigDecimal pricePf;

  private @Nullable BigDecimal pricePj;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  public ItemResponse id(@Nullable Long id) {
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

  public ItemResponse name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public ItemResponse type(@Nullable TypeEnum type) {
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

  public ItemResponse baseUnit(@Nullable BaseUnitEnum baseUnit) {
    this.baseUnit = baseUnit;
    return this;
  }

  /**
   * Get baseUnit
   * @return baseUnit
   */
  
  @Schema(name = "baseUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("baseUnit")
  public @Nullable BaseUnitEnum getBaseUnit() {
    return baseUnit;
  }

  public void setBaseUnit(@Nullable BaseUnitEnum baseUnit) {
    this.baseUnit = baseUnit;
  }

  public ItemResponse active(@Nullable Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
   */
  
  @Schema(name = "active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("active")
  public @Nullable Boolean getActive() {
    return active;
  }

  public void setActive(@Nullable Boolean active) {
    this.active = active;
  }

  public ItemResponse lowStockThreshold(@Nullable BigDecimal lowStockThreshold) {
    this.lowStockThreshold = lowStockThreshold;
    return this;
  }

  /**
   * Get lowStockThreshold
   * @return lowStockThreshold
   */
  @Valid 
  @Schema(name = "lowStockThreshold", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lowStockThreshold")
  public @Nullable BigDecimal getLowStockThreshold() {
    return lowStockThreshold;
  }

  public void setLowStockThreshold(@Nullable BigDecimal lowStockThreshold) {
    this.lowStockThreshold = lowStockThreshold;
  }

  public ItemResponse criticalStockThreshold(@Nullable BigDecimal criticalStockThreshold) {
    this.criticalStockThreshold = criticalStockThreshold;
    return this;
  }

  /**
   * Get criticalStockThreshold
   * @return criticalStockThreshold
   */
  @Valid 
  @Schema(name = "criticalStockThreshold", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("criticalStockThreshold")
  public @Nullable BigDecimal getCriticalStockThreshold() {
    return criticalStockThreshold;
  }

  public void setCriticalStockThreshold(@Nullable BigDecimal criticalStockThreshold) {
    this.criticalStockThreshold = criticalStockThreshold;
  }

  public ItemResponse pricePf(@Nullable BigDecimal pricePf) {
    this.pricePf = pricePf;
    return this;
  }

  /**
   * Get pricePf
   * @return pricePf
   */
  @Valid 
  @Schema(name = "pricePf", example = "12.9", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pricePf")
  public @Nullable BigDecimal getPricePf() {
    return pricePf;
  }

  public void setPricePf(@Nullable BigDecimal pricePf) {
    this.pricePf = pricePf;
  }

  public ItemResponse pricePj(@Nullable BigDecimal pricePj) {
    this.pricePj = pricePj;
    return this;
  }

  /**
   * Get pricePj
   * @return pricePj
   */
  @Valid 
  @Schema(name = "pricePj", example = "15.9", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pricePj")
  public @Nullable BigDecimal getPricePj() {
    return pricePj;
  }

  public void setPricePj(@Nullable BigDecimal pricePj) {
    this.pricePj = pricePj;
  }

  public ItemResponse createdAt(@Nullable OffsetDateTime createdAt) {
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

  public ItemResponse updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemResponse itemResponse = (ItemResponse) o;
    return Objects.equals(this.id, itemResponse.id) &&
        Objects.equals(this.name, itemResponse.name) &&
        Objects.equals(this.type, itemResponse.type) &&
        Objects.equals(this.baseUnit, itemResponse.baseUnit) &&
        Objects.equals(this.active, itemResponse.active) &&
        Objects.equals(this.lowStockThreshold, itemResponse.lowStockThreshold) &&
        Objects.equals(this.criticalStockThreshold, itemResponse.criticalStockThreshold) &&
        Objects.equals(this.pricePf, itemResponse.pricePf) &&
        Objects.equals(this.pricePj, itemResponse.pricePj) &&
        Objects.equals(this.createdAt, itemResponse.createdAt) &&
        Objects.equals(this.updatedAt, itemResponse.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, type, baseUnit, active, lowStockThreshold, criticalStockThreshold, pricePf, pricePj, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    baseUnit: ").append(toIndentedString(baseUnit)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    lowStockThreshold: ").append(toIndentedString(lowStockThreshold)).append("\n");
    sb.append("    criticalStockThreshold: ").append(toIndentedString(criticalStockThreshold)).append("\n");
    sb.append("    pricePf: ").append(toIndentedString(pricePf)).append("\n");
    sb.append("    pricePj: ").append(toIndentedString(pricePj)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

