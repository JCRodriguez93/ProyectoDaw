package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import es.swagger.codegen.models.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Orders
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class Orders   {
  @JsonProperty("idOrder")
  private Integer idOrder = null;

  @JsonProperty("idUser")
  private Integer idUser = null;

  @JsonProperty("totalQuantity")
  private Integer totalQuantity = null;

  @JsonProperty("totalPrice")
  private Double totalPrice = null;

  @JsonProperty("date")
  private java.time.LocalDateTime date = null;

  @JsonProperty("orderStatus")
  private OrderStatus orderStatus = null;

  public Orders idOrder(Integer idOrder) {
    this.idOrder = idOrder;
    return this;
  }

  /**
   * Identificador único del pedido
   * @return idOrder
   **/
  @Schema(description = "Identificador único del pedido")
  
    public Integer getIdOrder() {
    return idOrder;
  }

  public void setIdOrder(Integer idOrder) {
    this.idOrder = idOrder;
  }

  public Orders idUser(Integer idUser) {
    this.idUser = idUser;
    return this;
  }

  /**
   * Identificador único del usuario que realizó el pedido
   * @return idUser
   **/
  @Schema(required = true, description = "Identificador único del usuario que realizó el pedido")
      @NotNull

    public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public Orders totalQuantity(Integer totalQuantity) {
    this.totalQuantity = totalQuantity;
    return this;
  }

  /**
   * Cantidad total de productos en el pedido
   * @return totalQuantity
   **/
  @Schema(required = true, description = "Cantidad total de productos en el pedido")
      @NotNull

    public Integer getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(Integer totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  public Orders totalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  /**
   * Precio total del pedido
   * @return totalPrice
   **/
  @Schema(required = true, description = "Precio total del pedido")
      @NotNull

    public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Orders date(java.time.LocalDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Fecha
   * @return date
   **/
  @Schema(required = true, description = "Fecha")
      @NotNull

    @Valid
    public java.time.LocalDateTime getDate() {
    return date;
  }

  public void setDate(java.time.LocalDateTime date) {
    this.date = date;
  }

  public Orders orderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
    return this;
  }

  /**
   * Get orderStatus
   * @return orderStatus
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Orders orders = (Orders) o;
    return Objects.equals(this.idOrder, orders.idOrder) &&
        Objects.equals(this.idUser, orders.idUser) &&
        Objects.equals(this.totalQuantity, orders.totalQuantity) &&
        Objects.equals(this.totalPrice, orders.totalPrice) &&
        Objects.equals(this.date, orders.date) &&
        Objects.equals(this.orderStatus, orders.orderStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idOrder, idUser, totalQuantity, totalPrice, date, orderStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Orders {\n");
    
    sb.append("    idOrder: ").append(toIndentedString(idOrder)).append("\n");
    sb.append("    idUser: ").append(toIndentedString(idUser)).append("\n");
    sb.append("    totalQuantity: ").append(toIndentedString(totalQuantity)).append("\n");
    sb.append("    totalPrice: ").append(toIndentedString(totalPrice)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    orderStatus: ").append(toIndentedString(orderStatus)).append("\n");
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
