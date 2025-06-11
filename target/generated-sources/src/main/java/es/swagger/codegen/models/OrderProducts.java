package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrderProducts
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class OrderProducts   {
  @JsonProperty("idOrderProduct")
  private Integer idOrderProduct = null;

  @JsonProperty("idOrder")
  private Integer idOrder = null;

  @JsonProperty("idProduct")
  private Integer idProduct = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  public OrderProducts idOrderProduct(Integer idOrderProduct) {
    this.idOrderProduct = idOrderProduct;
    return this;
  }

  /**
   * Identificador único del producto en el pedido
   * @return idOrderProduct
   **/
  @Schema(required = true, description = "Identificador único del producto en el pedido")
      @NotNull

    public Integer getIdOrderProduct() {
    return idOrderProduct;
  }

  public void setIdOrderProduct(Integer idOrderProduct) {
    this.idOrderProduct = idOrderProduct;
  }

  public OrderProducts idOrder(Integer idOrder) {
    this.idOrder = idOrder;
    return this;
  }

  /**
   * Identificador único del pedido
   * @return idOrder
   **/
  @Schema(required = true, description = "Identificador único del pedido")
      @NotNull

    public Integer getIdOrder() {
    return idOrder;
  }

  public void setIdOrder(Integer idOrder) {
    this.idOrder = idOrder;
  }

  public OrderProducts idProduct(Integer idProduct) {
    this.idProduct = idProduct;
    return this;
  }

  /**
   * Identificador único del producto asociado al pedido
   * @return idProduct
   **/
  @Schema(required = true, description = "Identificador único del producto asociado al pedido")
      @NotNull

    public Integer getIdProduct() {
    return idProduct;
  }

  public void setIdProduct(Integer idProduct) {
    this.idProduct = idProduct;
  }

  public OrderProducts quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Cantidad del producto en el pedido (debe ser mayor a 0)
   * minimum: 1
   * @return quantity
   **/
  @Schema(required = true, description = "Cantidad del producto en el pedido (debe ser mayor a 0)")
      @NotNull

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
    OrderProducts orderProducts = (OrderProducts) o;
    return Objects.equals(this.idOrderProduct, orderProducts.idOrderProduct) &&
        Objects.equals(this.idOrder, orderProducts.idOrder) &&
        Objects.equals(this.idProduct, orderProducts.idProduct) &&
        Objects.equals(this.quantity, orderProducts.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idOrderProduct, idOrder, idProduct, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderProducts {\n");
    
    sb.append("    idOrderProduct: ").append(toIndentedString(idOrderProduct)).append("\n");
    sb.append("    idOrder: ").append(toIndentedString(idOrder)).append("\n");
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
