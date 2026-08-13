package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import br.com.bratatouille.management.generated.model.CustomerProfileRequest;
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
 * Cadastro público do e-commerce. Sempre cria customer PF e exige perfil com pelo menos um endereço.
 */

@Schema(name = "RegisterCustomerRequest", description = "Cadastro público do e-commerce. Sempre cria customer PF e exige perfil com pelo menos um endereço.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class RegisterCustomerRequest {

  private String username;

  private String password;

  private CustomerProfileRequest profile;

  public RegisterCustomerRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RegisterCustomerRequest(String username, String password, CustomerProfileRequest profile) {
    this.username = username;
    this.password = password;
    this.profile = profile;
  }

  public RegisterCustomerRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   */
  @NotNull @Size(min = 3, max = 80) 
  @Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public RegisterCustomerRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  @NotNull @Size(min = 8, max = 72) 
  @Schema(name = "password", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RegisterCustomerRequest profile(CustomerProfileRequest profile) {
    this.profile = profile;
    return this;
  }

  /**
   * Get profile
   * @return profile
   */
  @NotNull @Valid 
  @Schema(name = "profile", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("profile")
  public CustomerProfileRequest getProfile() {
    return profile;
  }

  public void setProfile(CustomerProfileRequest profile) {
    this.profile = profile;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterCustomerRequest registerCustomerRequest = (RegisterCustomerRequest) o;
    return Objects.equals(this.username, registerCustomerRequest.username) &&
        Objects.equals(this.password, registerCustomerRequest.password) &&
        Objects.equals(this.profile, registerCustomerRequest.profile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, profile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterCustomerRequest {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    profile: ").append(toIndentedString(profile)).append("\n");
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

