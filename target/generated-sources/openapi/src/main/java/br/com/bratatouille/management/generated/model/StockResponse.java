package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StockResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class StockResponse {

  private @Nullable Long id;

  private @Nullable Long itemId;

  private @Nullable String itemName;

  /**
   * Gets or Sets itemType
   */
  public enum ItemTypeEnum {
    INGREDIENT("INGREDIENT"),
    
    PACKAGING("PACKAGING"),
    
    FINISHED_PRODUCT("FINISHED_PRODUCT");

    private final String value;

    ItemTypeEnum(String value) {
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
    public static ItemTypeEnum fromValue(String value) {
      for (ItemTypeEnum b : ItemTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ItemTypeEnum itemType;

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

  private @Nullable BigDecimal quantity;

  private @Nullable BigDecimal pricePf;

  private @Nullable BigDecimal pricePj;

  public StockResponse id(@Nullable Long id) {
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

  public StockResponse itemId(@Nullable Long itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
   */
  
  @Schema(name = "itemId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemId")
  public @Nullable Long getItemId() {
    return itemId;
  }

  public void setItemId(@Nullable Long itemId) {
    this.itemId = itemId;
  }

  public StockResponse itemName(@Nullable String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Get itemName
   * @return itemName
   */
  
  @Schema(name = "itemName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemName")
  public @Nullable String getItemName() {
    return itemName;
  }

  public void setItemName(@Nullable String itemName) {
    this.itemName = itemName;
  }

  public StockResponse itemType(@Nullable ItemTypeEnum itemType) {
    this.itemType = itemType;
    return this;
  }

  /**
   * Get itemType
   * @return itemType
   */
  
  @Schema(name = "itemType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemType")
  public @Nullable ItemTypeEnum getItemType() {
    return itemType;
  }

  public void setItemType(@Nullable ItemTypeEnum itemType) {
    this.itemType = itemType;
  }

  public StockResponse baseUnit(@Nullable BaseUnitEnum baseUnit) {
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

  public StockResponse quantity(@Nullable BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
   */
  @Valid 
  @Schema(name = "quantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantity")
  public @Nullable BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(@Nullable BigDecimal quantity) {
    this.quantity = quantity;
  }

  public StockResponse pricePf(@Nullable BigDecimal pricePf) {
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

  public StockResponse pricePj(@Nullable BigDecimal pricePj) {
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
    StockResponse stockResponse = (StockResponse) o;
    return Objects.equals(this.id, stockResponse.id) &&
        Objects.equals(this.itemId, stockResponse.itemId) &&
        Objects.equals(this.itemName, stockResponse.itemName) &&
        Objects.equals(this.itemType, stockResponse.itemType) &&
        Objects.equals(this.baseUnit, stockResponse.baseUnit) &&
        Objects.equals(this.quantity, stockResponse.quantity) &&
        Objects.equals(this.pricePf, stockResponse.pricePf) &&
        Objects.equals(this.pricePj, stockResponse.pricePj);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemId, itemName, itemType, baseUnit, quantity, pricePf, pricePj);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
    sb.append("    baseUnit: ").append(toIndentedString(baseUnit)).append("\n");
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

