package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CartProducts
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class CartProducts   {
  @JsonProperty("idProduct")
  private Integer idProduct = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  public CartProducts idProduct(Integer idProduct) {
    this.idProduct = idProduct;
    return this;
  }

  /**
   * Identificador único del producto
   * @return idProduct
   **/
  @Schema(description = "Identificador único del producto")
  
    public Integer getIdProduct() {
    return idProduct;
  }

  public void setIdProduct(Integer idProduct) {
    this.idProduct = idProduct;
  }

  public CartProducts quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Cantidad de producto en el carrito
   * @return quantity
   **/
  @Schema(description = "Cantidad de producto en el carrito")
  
    public Integer getQuantity() {
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
    CartProducts cartProducts = (CartProducts) o;
    return Objects.equals(this.idProduct, cartProducts.idProduct) &&
        Objects.equals(this.quantity, cartProducts.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProduct, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CartProducts {\n");
    
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
