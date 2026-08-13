package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreatePartnerRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class CreatePartnerRequest {

  private String name;

  private BigDecimal defaultSplitPercentage;

  /**
   * Gets or Sets roles
   */
  public enum RolesEnum {
    ADMIN("ADMIN"),
    
    PRODUCER("PRODUCER"),
    
    VIEWER("VIEWER"),
    
    EDITOR("EDITOR");

    private final String value;

    RolesEnum(String value) {
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
    public static RolesEnum fromValue(String value) {
      for (RolesEnum b : RolesEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @Valid
  private Set<RolesEnum> roles = new LinkedHashSet<>();

  public CreatePartnerRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreatePartnerRequest(String name, BigDecimal defaultSplitPercentage) {
    this.name = name;
    this.defaultSplitPercentage = defaultSplitPercentage;
  }

  public CreatePartnerRequest name(String name) {
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

  public CreatePartnerRequest defaultSplitPercentage(BigDecimal defaultSplitPercentage) {
    this.defaultSplitPercentage = defaultSplitPercentage;
    return this;
  }

  /**
   * Get defaultSplitPercentage
   * @return defaultSplitPercentage
   */
  @NotNull @Valid 
  @Schema(name = "defaultSplitPercentage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("defaultSplitPercentage")
  public BigDecimal getDefaultSplitPercentage() {
    return defaultSplitPercentage;
  }

  public void setDefaultSplitPercentage(BigDecimal defaultSplitPercentage) {
    this.defaultSplitPercentage = defaultSplitPercentage;
  }

  public CreatePartnerRequest roles(Set<RolesEnum> roles) {
    this.roles = roles;
    return this;
  }

  public CreatePartnerRequest addRolesItem(RolesEnum rolesItem) {
    if (this.roles == null) {
      this.roles = new LinkedHashSet<>();
    }
    this.roles.add(rolesItem);
    return this;
  }

  /**
   * Get roles
   * @return roles
   */
  
  @Schema(name = "roles", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roles")
  public Set<RolesEnum> getRoles() {
    return roles;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  public void setRoles(Set<RolesEnum> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreatePartnerRequest createPartnerRequest = (CreatePartnerRequest) o;
    return Objects.equals(this.name, createPartnerRequest.name) &&
        Objects.equals(this.defaultSplitPercentage, createPartnerRequest.defaultSplitPercentage) &&
        Objects.equals(this.roles, createPartnerRequest.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, defaultSplitPercentage, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreatePartnerRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    defaultSplitPercentage: ").append(toIndentedString(defaultSplitPercentage)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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

