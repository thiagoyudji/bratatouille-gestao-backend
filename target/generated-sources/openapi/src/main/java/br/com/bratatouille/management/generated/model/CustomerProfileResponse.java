package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.CustomerAddressResponse;
import br.com.bratatouille.management.generated.model.CustomerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Perfil do customer disponível para o front.
 */

@Schema(name = "CustomerProfileResponse", description = "Perfil do customer disponível para o front.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class CustomerProfileResponse {

  private @Nullable CustomerType customerType;

  private @Nullable String fullName;

  private @Nullable String email;

  private @Nullable String phone;

  @Valid
  private List<@Valid CustomerAddressResponse> addresses = new ArrayList<>();

  public CustomerProfileResponse customerType(@Nullable CustomerType customerType) {
    this.customerType = customerType;
    return this;
  }

  /**
   * Get customerType
   * @return customerType
   */
  @Valid 
  @Schema(name = "customerType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerType")
  public @Nullable CustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(@Nullable CustomerType customerType) {
    this.customerType = customerType;
  }

  public CustomerProfileResponse fullName(@Nullable String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  
  @Schema(name = "fullName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public @Nullable String getFullName() {
    return fullName;
  }

  public void setFullName(@Nullable String fullName) {
    this.fullName = fullName;
  }

  public CustomerProfileResponse email(@Nullable String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable String getEmail() {
    return email;
  }

  public void setEmail(@Nullable String email) {
    this.email = email;
  }

  public CustomerProfileResponse phone(@Nullable String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
   */
  
  @Schema(name = "phone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phone")
  public @Nullable String getPhone() {
    return phone;
  }

  public void setPhone(@Nullable String phone) {
    this.phone = phone;
  }

  public CustomerProfileResponse addresses(List<@Valid CustomerAddressResponse> addresses) {
    this.addresses = addresses;
    return this;
  }

  public CustomerProfileResponse addAddressesItem(CustomerAddressResponse addressesItem) {
    if (this.addresses == null) {
      this.addresses = new ArrayList<>();
    }
    this.addresses.add(addressesItem);
    return this;
  }

  /**
   * Get addresses
   * @return addresses
   */
  @Valid 
  @Schema(name = "addresses", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("addresses")
  public List<@Valid CustomerAddressResponse> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<@Valid CustomerAddressResponse> addresses) {
    this.addresses = addresses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerProfileResponse customerProfileResponse = (CustomerProfileResponse) o;
    return Objects.equals(this.customerType, customerProfileResponse.customerType) &&
        Objects.equals(this.fullName, customerProfileResponse.fullName) &&
        Objects.equals(this.email, customerProfileResponse.email) &&
        Objects.equals(this.phone, customerProfileResponse.phone) &&
        Objects.equals(this.addresses, customerProfileResponse.addresses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerType, fullName, email, phone, addresses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerProfileResponse {\n");
    sb.append("    customerType: ").append(toIndentedString(customerType)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
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

