package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Products
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class Products   {
  @JsonProperty("idProduct")
  private Integer idProduct = null;

  @JsonProperty("idSubcategory")
  private Integer idSubcategory = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("price")
  private Double price = null;

  @JsonProperty("imageUrl")
  private String imageUrl = null;

  public Products idProduct(Integer idProduct) {
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

  public Products idSubcategory(Integer idSubcategory) {
    this.idSubcategory = idSubcategory;
    return this;
  }

  /**
   * Identificador único de la subcategoría asociada
   * @return idSubcategory
   **/
  @Schema(description = "Identificador único de la subcategoría asociada")
  
    public Integer getIdSubcategory() {
    return idSubcategory;
  }

  public void setIdSubcategory(Integer idSubcategory) {
    this.idSubcategory = idSubcategory;
  }

  public Products name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Nombre del producto
   * @return name
   **/
  @Schema(required = true, description = "Nombre del producto")
      @NotNull

  @Size(max=100)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Products description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Descripción detallada del producto
   * @return description
   **/
  @Schema(description = "Descripción detallada del producto")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Products price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Precio del producto con exactamente dos decimales
   * @return price
   **/
  @Schema(required = true, description = "Precio del producto con exactamente dos decimales")
      @NotNull

    public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Products imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * URL o ruta de la imagen del producto
   * @return imageUrl
   **/
  @Schema(description = "URL o ruta de la imagen del producto")
  
    public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Products products = (Products) o;
    return Objects.equals(this.idProduct, products.idProduct) &&
        Objects.equals(this.idSubcategory, products.idSubcategory) &&
        Objects.equals(this.name, products.name) &&
        Objects.equals(this.description, products.description) &&
        Objects.equals(this.price, products.price) &&
        Objects.equals(this.imageUrl, products.imageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProduct, idSubcategory, name, description, price, imageUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Products {\n");
    
    sb.append("    idProduct: ").append(toIndentedString(idProduct)).append("\n");
    sb.append("    idSubcategory: ").append(toIndentedString(idSubcategory)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
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
