package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.CustomerAddressRequest;
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
 * Perfil persistido do customer, separado da autenticação. PF e PJ compartilham a mesma estrutura; guest não gera perfil salvo.
 */

@Schema(name = "CustomerProfileRequest", description = "Perfil persistido do customer, separado da autenticação. PF e PJ compartilham a mesma estrutura; guest não gera perfil salvo.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class CustomerProfileRequest {

  private CustomerType customerType;

  private String fullName;

  private String email;

  private String phone;

  @Valid
  private List<@Valid CustomerAddressRequest> addresses = new ArrayList<>();

  public CustomerProfileRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CustomerProfileRequest(CustomerType customerType, String fullName, String email, String phone) {
    this.customerType = customerType;
    this.fullName = fullName;
    this.email = email;
    this.phone = phone;
  }

  public CustomerProfileRequest customerType(CustomerType customerType) {
    this.customerType = customerType;
    return this;
  }

  /**
   * Get customerType
   * @return customerType
   */
  @NotNull @Valid 
  @Schema(name = "customerType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("customerType")
  public CustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(CustomerType customerType) {
    this.customerType = customerType;
  }

  public CustomerProfileRequest fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  @NotNull 
  @Schema(name = "fullName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fullName")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public CustomerProfileRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @NotNull 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public CustomerProfileRequest phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
   */
  @NotNull 
  @Schema(name = "phone", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public CustomerProfileRequest addresses(List<@Valid CustomerAddressRequest> addresses) {
    this.addresses = addresses;
    return this;
  }

  public CustomerProfileRequest addAddressesItem(CustomerAddressRequest addressesItem) {
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
  public List<@Valid CustomerAddressRequest> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<@Valid CustomerAddressRequest> addresses) {
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
    CustomerProfileRequest customerProfileRequest = (CustomerProfileRequest) o;
    return Objects.equals(this.customerType, customerProfileRequest.customerType) &&
        Objects.equals(this.fullName, customerProfileRequest.fullName) &&
        Objects.equals(this.email, customerProfileRequest.email) &&
        Objects.equals(this.phone, customerProfileRequest.phone) &&
        Objects.equals(this.addresses, customerProfileRequest.addresses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerType, fullName, email, phone, addresses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerProfileRequest {\n");
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

