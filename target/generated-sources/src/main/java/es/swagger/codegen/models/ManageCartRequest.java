package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ManageCartRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class ManageCartRequest   {
  @JsonProperty("product_id")
  private Integer productId = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  /**
   * Acción a realizar en el carrito.
   */
  public enum ActionEnum {
    ADD("add"),
    
    MODIFY("modify"),
    
    REMOVE("remove");

    private String value;

    ActionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ActionEnum fromValue(String text) {
      for (ActionEnum b : ActionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("action")
  private ActionEnum action = null;

  public ManageCartRequest productId(Integer productId) {
    this.productId = productId;
    return this;
  }

  /**
   * ID del producto que se gestionará en el carrito.
   * @return productId
   **/
  @Schema(required = true, description = "ID del producto que se gestionará en el carrito.")
      @NotNull

    public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public ManageCartRequest quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Cantidad del producto. Obligatorio para `add` y `modify`.
   * @return quantity
   **/
  @Schema(example = "1", description = "Cantidad del producto. Obligatorio para `add` y `modify`.")
  
    public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public ManageCartRequest action(ActionEnum action) {
    this.action = action;
    return this;
  }

  /**
   * Acción a realizar en el carrito.
   * @return action
   **/
  @Schema(example = "add", required = true, description = "Acción a realizar en el carrito.")
      @NotNull

    public ActionEnum getAction() {
    return action;
  }

  public void setAction(ActionEnum action) {
    this.action = action;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ManageCartRequest manageCartRequest = (ManageCartRequest) o;
    return Objects.equals(this.productId, manageCartRequest.productId) &&
        Objects.equals(this.quantity, manageCartRequest.quantity) &&
        Objects.equals(this.action, manageCartRequest.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, quantity, action);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManageCartRequest {\n");
    
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
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
