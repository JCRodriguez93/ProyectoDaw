package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import es.swagger.codegen.models.CartProducts;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Cart
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class Cart   {
  @JsonProperty("idCart")
  private Integer idCart = null;

  @JsonProperty("idUser")
  private Integer idUser = null;

  @JsonProperty("products")
  @Valid
  private List<CartProducts> products = new ArrayList<CartProducts>();

  public Cart idCart(Integer idCart) {
    this.idCart = idCart;
    return this;
  }

  /**
   * Identificador único del carrito
   * @return idCart
   **/
  @Schema(required = true, description = "Identificador único del carrito")
      @NotNull

    public Integer getIdCart() {
    return idCart;
  }

  public void setIdCart(Integer idCart) {
    this.idCart = idCart;
  }

  public Cart idUser(Integer idUser) {
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

  public Cart products(List<CartProducts> products) {
    this.products = products;
    return this;
  }

  public Cart addProductsItem(CartProducts productsItem) {
    this.products.add(productsItem);
    return this;
  }

  /**
   * Get products
   * @return products
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<CartProducts> getProducts() {
    return products;
  }

  public void setProducts(List<CartProducts> products) {
    this.products = products;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cart cart = (Cart) o;
    return Objects.equals(this.idCart, cart.idCart) &&
        Objects.equals(this.idUser, cart.idUser) &&
        Objects.equals(this.products, cart.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCart, idUser, products);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cart {\n");
    
    sb.append("    idCart: ").append(toIndentedString(idCart)).append("\n");
    sb.append("    idUser: ").append(toIndentedString(idUser)).append("\n");
    sb.append("    products: ").append(toIndentedString(products)).append("\n");
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
