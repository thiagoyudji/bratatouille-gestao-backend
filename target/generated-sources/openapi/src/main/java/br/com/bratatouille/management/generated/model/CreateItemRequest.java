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
 * Cadastro de item com preço separado para PF e PJ.
 */

@Schema(name = "CreateItemRequest", description = "Cadastro de item com preço separado para PF e PJ.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class CreateItemRequest {

  private String name;

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

  private TypeEnum type;

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

  private BaseUnitEnum baseUnit;

  private @Nullable BigDecimal lowStockThreshold;

  private @Nullable BigDecimal criticalStockThreshold;

  private BigDecimal pricePf;

  private BigDecimal pricePj;

  public CreateItemRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateItemRequest(String name, TypeEnum type, BaseUnitEnum baseUnit, BigDecimal pricePf, BigDecimal pricePj) {
    this.name = name;
    this.type = type;
    this.baseUnit = baseUnit;
    this.pricePf = pricePf;
    this.pricePj = pricePj;
  }

  public CreateItemRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateItemRequest type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public CreateItemRequest baseUnit(BaseUnitEnum baseUnit) {
    this.baseUnit = baseUnit;
    return this;
  }

  /**
   * Get baseUnit
   * @return baseUnit
   */
  @NotNull 
  @Schema(name = "baseUnit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("baseUnit")
  public BaseUnitEnum getBaseUnit() {
    return baseUnit;
  }

  public void setBaseUnit(BaseUnitEnum baseUnit) {
    this.baseUnit = baseUnit;
  }

  public CreateItemRequest lowStockThreshold(@Nullable BigDecimal lowStockThreshold) {
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

  public CreateItemRequest criticalStockThreshold(@Nullable BigDecimal criticalStockThreshold) {
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

  public CreateItemRequest pricePf(BigDecimal pricePf) {
    this.pricePf = pricePf;
    return this;
  }

  /**
   * Get pricePf
   * @return pricePf
   */
  @NotNull @Valid 
  @Schema(name = "pricePf", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pricePf")
  public BigDecimal getPricePf() {
    return pricePf;
  }

  public void setPricePf(BigDecimal pricePf) {
    this.pricePf = pricePf;
  }

  public CreateItemRequest pricePj(BigDecimal pricePj) {
    this.pricePj = pricePj;
    return this;
  }

  /**
   * Get pricePj
   * @return pricePj
   */
  @NotNull @Valid 
  @Schema(name = "pricePj", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pricePj")
  public BigDecimal getPricePj() {
    return pricePj;
  }

  public void setPricePj(BigDecimal pricePj) {
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
    CreateItemRequest createItemRequest = (CreateItemRequest) o;
    return Objects.equals(this.name, createItemRequest.name) &&
        Objects.equals(this.type, createItemRequest.type) &&
        Objects.equals(this.baseUnit, createItemRequest.baseUnit) &&
        Objects.equals(this.lowStockThreshold, createItemRequest.lowStockThreshold) &&
        Objects.equals(this.criticalStockThreshold, createItemRequest.criticalStockThreshold) &&
        Objects.equals(this.pricePf, createItemRequest.pricePf) &&
        Objects.equals(this.pricePj, createItemRequest.pricePj);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, baseUnit, lowStockThreshold, criticalStockThreshold, pricePf, pricePj);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateItemRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    baseUnit: ").append(toIndentedString(baseUnit)).append("\n");
    sb.append("    lowStockThreshold: ").append(toIndentedString(lowStockThreshold)).append("\n");
    sb.append("    criticalStockThreshold: ").append(toIndentedString(criticalStockThreshold)).append("\n");
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

