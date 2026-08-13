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
 * StockAlertResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class StockAlertResponse {

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

  private @Nullable BigDecimal currentQuantity;

  private @Nullable BigDecimal lowStockThreshold;

  private @Nullable BigDecimal criticalStockThreshold;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    LOW("LOW"),
    
    CRITICAL("CRITICAL"),
    
    NEAR_ZERO("NEAR_ZERO");

    private final String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable StatusEnum status;

  public StockAlertResponse itemId(@Nullable Long itemId) {
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

  public StockAlertResponse itemName(@Nullable String itemName) {
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

  public StockAlertResponse itemType(@Nullable ItemTypeEnum itemType) {
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

  public StockAlertResponse baseUnit(@Nullable BaseUnitEnum baseUnit) {
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

  public StockAlertResponse currentQuantity(@Nullable BigDecimal currentQuantity) {
    this.currentQuantity = currentQuantity;
    return this;
  }

  /**
   * Get currentQuantity
   * @return currentQuantity
   */
  @Valid 
  @Schema(name = "currentQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currentQuantity")
  public @Nullable BigDecimal getCurrentQuantity() {
    return currentQuantity;
  }

  public void setCurrentQuantity(@Nullable BigDecimal currentQuantity) {
    this.currentQuantity = currentQuantity;
  }

  public StockAlertResponse lowStockThreshold(@Nullable BigDecimal lowStockThreshold) {
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

  public StockAlertResponse criticalStockThreshold(@Nullable BigDecimal criticalStockThreshold) {
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

  public StockAlertResponse status(@Nullable StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable StatusEnum getStatus() {
    return status;
  }

  public void setStatus(@Nullable StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockAlertResponse stockAlertResponse = (StockAlertResponse) o;
    return Objects.equals(this.itemId, stockAlertResponse.itemId) &&
        Objects.equals(this.itemName, stockAlertResponse.itemName) &&
        Objects.equals(this.itemType, stockAlertResponse.itemType) &&
        Objects.equals(this.baseUnit, stockAlertResponse.baseUnit) &&
        Objects.equals(this.currentQuantity, stockAlertResponse.currentQuantity) &&
        Objects.equals(this.lowStockThreshold, stockAlertResponse.lowStockThreshold) &&
        Objects.equals(this.criticalStockThreshold, stockAlertResponse.criticalStockThreshold) &&
        Objects.equals(this.status, stockAlertResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, itemType, baseUnit, currentQuantity, lowStockThreshold, criticalStockThreshold, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockAlertResponse {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
    sb.append("    baseUnit: ").append(toIndentedString(baseUnit)).append("\n");
    sb.append("    currentQuantity: ").append(toIndentedString(currentQuantity)).append("\n");
    sb.append("    lowStockThreshold: ").append(toIndentedString(lowStockThreshold)).append("\n");
    sb.append("    criticalStockThreshold: ").append(toIndentedString(criticalStockThreshold)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

