package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import es.swagger.codegen.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class UpdateResponse   {
  @JsonProperty("message")
  private String message = null;

  @JsonProperty("updatedUser")
  private User updatedUser = null;

  public UpdateResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   **/
  @Schema(example = "Usuario actualizado correctamente.", required = true, description = "")
      @NotNull

    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public UpdateResponse updatedUser(User updatedUser) {
    this.updatedUser = updatedUser;
    return this;
  }

  /**
   * Get updatedUser
   * @return updatedUser
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public User getUpdatedUser() {
    return updatedUser;
  }

  public void setUpdatedUser(User updatedUser) {
    this.updatedUser = updatedUser;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateResponse updateResponse = (UpdateResponse) o;
    return Objects.equals(this.message, updateResponse.message) &&
        Objects.equals(this.updatedUser, updateResponse.updatedUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, updatedUser);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateResponse {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    updatedUser: ").append(toIndentedString(updatedUser)).append("\n");
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
