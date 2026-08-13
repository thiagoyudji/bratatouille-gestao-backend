package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SalesOrderCustomerAddress
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class SalesOrderCustomerAddress {

  private @Nullable String label;

  private @Nullable String zipCode;

  private @Nullable String street;

  private @Nullable String number;

  private @Nullable String neighborhood;

  private @Nullable String state;

  private @Nullable String city;

  private @Nullable String complement;

  private @Nullable Boolean defaultAddress;

  public SalesOrderCustomerAddress label(@Nullable String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
   */
  
  @Schema(name = "label", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("label")
  public @Nullable String getLabel() {
    return label;
  }

  public void setLabel(@Nullable String label) {
    this.label = label;
  }

  public SalesOrderCustomerAddress zipCode(@Nullable String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

  /**
   * Get zipCode
   * @return zipCode
   */
  
  @Schema(name = "zipCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("zipCode")
  public @Nullable String getZipCode() {
    return zipCode;
  }

  public void setZipCode(@Nullable String zipCode) {
    this.zipCode = zipCode;
  }

  public SalesOrderCustomerAddress street(@Nullable String street) {
    this.street = street;
    return this;
  }

  /**
   * Get street
   * @return street
   */
  
  @Schema(name = "street", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("street")
  public @Nullable String getStreet() {
    return street;
  }

  public void setStreet(@Nullable String street) {
    this.street = street;
  }

  public SalesOrderCustomerAddress number(@Nullable String number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * @return number
   */
  
  @Schema(name = "number", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("number")
  public @Nullable String getNumber() {
    return number;
  }

  public void setNumber(@Nullable String number) {
    this.number = number;
  }

  public SalesOrderCustomerAddress neighborhood(@Nullable String neighborhood) {
    this.neighborhood = neighborhood;
    return this;
  }

  /**
   * Get neighborhood
   * @return neighborhood
   */
  
  @Schema(name = "neighborhood", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("neighborhood")
  public @Nullable String getNeighborhood() {
    return neighborhood;
  }

  public void setNeighborhood(@Nullable String neighborhood) {
    this.neighborhood = neighborhood;
  }

  public SalesOrderCustomerAddress state(@Nullable String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   */
  
  @Schema(name = "state", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("state")
  public @Nullable String getState() {
    return state;
  }

  public void setState(@Nullable String state) {
    this.state = state;
  }

  public SalesOrderCustomerAddress city(@Nullable String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  
  @Schema(name = "city", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public @Nullable String getCity() {
    return city;
  }

  public void setCity(@Nullable String city) {
    this.city = city;
  }

  public SalesOrderCustomerAddress complement(@Nullable String complement) {
    this.complement = complement;
    return this;
  }

  /**
   * Get complement
   * @return complement
   */
  
  @Schema(name = "complement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("complement")
  public @Nullable String getComplement() {
    return complement;
  }

  public void setComplement(@Nullable String complement) {
    this.complement = complement;
  }

  public SalesOrderCustomerAddress defaultAddress(@Nullable Boolean defaultAddress) {
    this.defaultAddress = defaultAddress;
    return this;
  }

  /**
   * Get defaultAddress
   * @return defaultAddress
   */
  
  @Schema(name = "defaultAddress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("defaultAddress")
  public @Nullable Boolean getDefaultAddress() {
    return defaultAddress;
  }

  public void setDefaultAddress(@Nullable Boolean defaultAddress) {
    this.defaultAddress = defaultAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalesOrderCustomerAddress salesOrderCustomerAddress = (SalesOrderCustomerAddress) o;
    return Objects.equals(this.label, salesOrderCustomerAddress.label) &&
        Objects.equals(this.zipCode, salesOrderCustomerAddress.zipCode) &&
        Objects.equals(this.street, salesOrderCustomerAddress.street) &&
        Objects.equals(this.number, salesOrderCustomerAddress.number) &&
        Objects.equals(this.neighborhood, salesOrderCustomerAddress.neighborhood) &&
        Objects.equals(this.state, salesOrderCustomerAddress.state) &&
        Objects.equals(this.city, salesOrderCustomerAddress.city) &&
        Objects.equals(this.complement, salesOrderCustomerAddress.complement) &&
        Objects.equals(this.defaultAddress, salesOrderCustomerAddress.defaultAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, zipCode, street, number, neighborhood, state, city, complement, defaultAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesOrderCustomerAddress {\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    neighborhood: ").append(toIndentedString(neighborhood)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    complement: ").append(toIndentedString(complement)).append("\n");
    sb.append("    defaultAddress: ").append(toIndentedString(defaultAddress)).append("\n");
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

