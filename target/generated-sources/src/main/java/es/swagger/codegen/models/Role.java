package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Role
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class Role   {
  @JsonProperty("idRole")
  private Integer idRole = null;

  @JsonProperty("roleName")
  private String roleName = null;

  public Role idRole(Integer idRole) {
    this.idRole = idRole;
    return this;
  }

  /**
   * Identificador único del rol
   * @return idRole
   **/
  @Schema(description = "Identificador único del rol")
  
    public Integer getIdRole() {
    return idRole;
  }

  public void setIdRole(Integer idRole) {
    this.idRole = idRole;
  }

  public Role roleName(String roleName) {
    this.roleName = roleName;
    return this;
  }

  /**
   * Nombre del rol
   * @return roleName
   **/
  @Schema(description = "Nombre del rol")
  
    public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role = (Role) o;
    return Objects.equals(this.idRole, role.idRole) &&
        Objects.equals(this.roleName, role.roleName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idRole, roleName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Role {\n");
    
    sb.append("    idRole: ").append(toIndentedString(idRole)).append("\n");
    sb.append("    roleName: ").append(toIndentedString(roleName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
