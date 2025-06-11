package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RegisterRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class RegisterRequest   {
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

  public RegisterRequest userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Get userName
   * @return userName
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public RegisterRequest userSurname(String userSurname) {
    this.userSurname = userSurname;
    return this;
  }

  /**
   * Get userSurname
   * @return userSurname
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getUserSurname() {
    return userSurname;
  }

  public void setUserSurname(String userSurname) {
    this.userSurname = userSurname;
  }

  public RegisterRequest userBirth(java.time.LocalDateTime userBirth) {
    this.userBirth = userBirth;
    return this;
  }

  /**
   * Get userBirth
   * @return userBirth
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public java.time.LocalDateTime getUserBirth() {
    return userBirth;
  }

  public void setUserBirth(java.time.LocalDateTime userBirth) {
    this.userBirth = userBirth;
  }

  public RegisterRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public RegisterRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RegisterRequest roleId(Integer roleId) {
    this.roleId = roleId;
    return this;
  }

  /**
   * Get roleId
   * @return roleId
   **/
  @Schema(required = true, description = "")
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
    RegisterRequest registerRequest = (RegisterRequest) o;
    return Objects.equals(this.userName, registerRequest.userName) &&
        Objects.equals(this.userSurname, registerRequest.userSurname) &&
        Objects.equals(this.userBirth, registerRequest.userBirth) &&
        Objects.equals(this.email, registerRequest.email) &&
        Objects.equals(this.password, registerRequest.password) &&
        Objects.equals(this.roleId, registerRequest.roleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, userSurname, userBirth, email, password, roleId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterRequest {\n");
    
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
