package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddProductRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class AddProductRequest   {
  @JsonProperty("id_user")
  private Integer idUser = null;

  @JsonProperty("id_product")
  private Integer idProduct = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  public AddProductRequest idUser(Integer idUser) {
    this.idUser = idUser;
    return this;
  }

  /**
   * ID del usuario
   * @return idUser
   **/
  @Schema(description = "ID del usuario")
  
    public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public AddProductRequest idProduct(Integer idProduct) {
    this.idProduct = idProduct;
    return this;
  }

  /**
   * ID del producto
   * @return idProduct
   **/
  @Schema(description = "ID del producto")
  
    public Integer getIdProduct() {
    return idProduct;
  }

  public void setIdProduct(Integer idProduct) {
    this.idProduct = idProduct;
  }

  public AddProductRequest quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Cantidad del producto
   * minimum: 1
   * @return quantity
   **/
  @Schema(description = "Cantidad del producto")
  
  @Min(1)  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddProductRequest addProductRequest = (AddProductRequest) o;
    return Objects.equals(this.idUser, addProductRequest.idUser) &&
        Objects.equals(this.idProduct, addProductRequest.idProduct) &&
        Objects.equals(this.quantity, addProductRequest.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idUser, idProduct, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddProductRequest {\n");
    
    sb.append("    idUser: ").append(toIndentedString(idUser)).append("\n");
    sb.append("    idProduct: ").append(toIndentedString(idProduct)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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
