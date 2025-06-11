package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class User   {
  @JsonProperty("idUser")
  private Integer idUser = null;

  @JsonProperty("userName")
  private String userName = null;

  @JsonProperty("userSurname")
  private String userSurname = null;

  @JsonProperty("userBirth")
  private java.time.LocalDateTime userBirth = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("roleId")
  private Integer roleId = null;

  public User idUser(Integer idUser) {
    this.idUser = idUser;
    return this;
  }

  /**
   * Identificador único del usuario
   * @return idUser
   **/
  @Schema(required = true, description = "Identificador único del usuario")
      @NotNull

    public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public User userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Nombre del usuario
   * @return userName
   **/
  @Schema(required = true, description = "Nombre del usuario")
      @NotNull

  @Size(max=50)   public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public User userSurname(String userSurname) {
    this.userSurname = userSurname;
    return this;
  }

  /**
   * Apellidos del usuario
   * @return userSurname
   **/
  @Schema(required = true, description = "Apellidos del usuario")
      @NotNull

  @Size(max=100)   public String getUserSurname() {
    return userSurname;
  }

  public void setUserSurname(String userSurname) {
    this.userSurname = userSurname;
  }

  public User userBirth(java.time.LocalDateTime userBirth) {
    this.userBirth = userBirth;
    return this;
  }

  /**
   * Fecha de nacimiento del usuario
   * @return userBirth
   **/
  @Schema(required = true, description = "Fecha de nacimiento del usuario")
      @NotNull

    @Valid
    public java.time.LocalDateTime getUserBirth() {
    return userBirth;
  }

  public void setUserBirth(java.time.LocalDateTime userBirth) {
    this.userBirth = userBirth;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Correo electrónico único del usuario
   * @return email
   **/
  @Schema(required = true, description = "Correo electrónico único del usuario")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Contraseña del usuario en formato hash
   * @return password
   **/
  @Schema(required = true, description = "Contraseña del usuario en formato hash")
      @NotNull

  @Size(max=255)   public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User roleId(Integer roleId) {
    this.roleId = roleId;
    return this;
  }

  /**
   * ID del rol del usuario
   * @return roleId
   **/
  @Schema(required = true, description = "ID del rol del usuario")
      @NotNull

    public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.idUser, user.idUser) &&
        Objects.equals(this.userName, user.userName) &&
        Objects.equals(this.userSurname, user.userSurname) &&
        Objects.equals(this.userBirth, user.userBirth) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.roleId, user.roleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idUser, userName, userSurname, userBirth, email, password, roleId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    idUser: ").append(toIndentedString(idUser)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    userSurname: ").append(toIndentedString(userSurname)).append("\n");
    sb.append("    userBirth: ").append(toIndentedString(userBirth)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    roleId: ").append(toIndentedString(roleId)).append("\n");
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
