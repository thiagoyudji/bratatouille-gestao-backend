package br.com.bratatouille.management.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PartnerResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-13T16:34:02.160723012-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
public class PartnerResponse {

  private @Nullable Long id;

  private @Nullable String name;

  private @Nullable Boolean active;

  private @Nullable BigDecimal defaultSplitPercentage;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

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

  public PartnerResponse id(@Nullable Long id) {
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

  public PartnerResponse name(@Nullable String name) {
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

  public PartnerResponse active(@Nullable Boolean active) {
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

  public PartnerResponse defaultSplitPercentage(@Nullable BigDecimal defaultSplitPercentage) {
    this.defaultSplitPercentage = defaultSplitPercentage;
    return this;
  }

  /**
   * Get defaultSplitPercentage
   * @return defaultSplitPercentage
   */
  @Valid 
  @Schema(name = "defaultSplitPercentage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("defaultSplitPercentage")
  public @Nullable BigDecimal getDefaultSplitPercentage() {
    return defaultSplitPercentage;
  }

  public void setDefaultSplitPercentage(@Nullable BigDecimal defaultSplitPercentage) {
    this.defaultSplitPercentage = defaultSplitPercentage;
  }

  public PartnerResponse createdAt(@Nullable OffsetDateTime createdAt) {
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

  public PartnerResponse roles(Set<RolesEnum> roles) {
    this.roles = roles;
    return this;
  }

  public PartnerResponse addRolesItem(RolesEnum rolesItem) {
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
    PartnerResponse partnerResponse = (PartnerResponse) o;
    return Objects.equals(this.id, partnerResponse.id) &&
        Objects.equals(this.name, partnerResponse.name) &&
        Objects.equals(this.active, partnerResponse.active) &&
        Objects.equals(this.defaultSplitPercentage, partnerResponse.defaultSplitPercentage) &&
        Objects.equals(this.createdAt, partnerResponse.createdAt) &&
        Objects.equals(this.roles, partnerResponse.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, active, defaultSplitPercentage, createdAt, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartnerResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    defaultSplitPercentage: ").append(toIndentedString(defaultSplitPercentage)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

