package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CartProductResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class CartProductResponse   {
  @JsonProperty("product_id")
  private Integer productId = null;

  @JsonProperty("product_name")
  private String productName = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("price")
  private String price = null;

  @JsonProperty("total_price")
  private String totalPrice = null;

  public CartProductResponse productId(Integer productId) {
    this.productId = productId;
    return this;
  }

  /**
   * ID del producto.
   * @return productId
   **/
  @Schema(description = "ID del producto.")
  
    public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public CartProductResponse productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Nombre del producto.
   * @return productName
   **/
  @Schema(description = "Nombre del producto.")
  
    public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public CartProductResponse quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Cantidad de ese producto en el carrito.
   * @return quantity
   **/
  @Schema(description = "Cantidad de ese producto en el carrito.")
  
    public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public CartProductResponse price(String price) {
    this.price = price;
    return this;
  }

  /**
   * Precio unitario del producto.
   * @return price
   **/
  @Schema(description = "Precio unitario del producto.")
  
    public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public CartProductResponse totalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  /**
   * Precio total basado en la cantidad.
   * @return totalPrice
   **/
  @Schema(description = "Precio total basado en la cantidad.")
  
    public String getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CartProductResponse cartProductResponse = (CartProductResponse) o;
    return Objects.equals(this.productId, cartProductResponse.productId) &&
        Objects.equals(this.productName, cartProductResponse.productName) &&
        Objects.equals(this.quantity, cartProductResponse.quantity) &&
        Objects.equals(this.price, cartProductResponse.price) &&
        Objects.equals(this.totalPrice, cartProductResponse.totalPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, productName, quantity, price, totalPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CartProductResponse {\n");
    
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    totalPrice: ").append(toIndentedString(totalPrice)).append("\n");
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
