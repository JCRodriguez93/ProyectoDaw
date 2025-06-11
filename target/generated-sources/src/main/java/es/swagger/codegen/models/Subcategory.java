package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Subcategory
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class Subcategory   {
  @JsonProperty("id_subcategory")
  private Integer idSubcategory = null;

  @JsonProperty("id_category")
  private Integer idCategory = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  public Subcategory idSubcategory(Integer idSubcategory) {
    this.idSubcategory = idSubcategory;
    return this;
  }

  /**
   * Identificador único de la subcategoría
   * @return idSubcategory
   **/
  @Schema(description = "Identificador único de la subcategoría")
  
    public Integer getIdSubcategory() {
    return idSubcategory;
  }

  public void setIdSubcategory(Integer idSubcategory) {
    this.idSubcategory = idSubcategory;
  }

  public Subcategory idCategory(Integer idCategory) {
    this.idCategory = idCategory;
    return this;
  }

  /**
   * Identificador de la categoría a la que pertenece esta subcategoría
   * @return idCategory
   **/
  @Schema(description = "Identificador de la categoría a la que pertenece esta subcategoría")
  
    public Integer getIdCategory() {
    return idCategory;
  }

  public void setIdCategory(Integer idCategory) {
    this.idCategory = idCategory;
  }

  public Subcategory name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Nombre de la subcategoría
   * @return name
   **/
  @Schema(description = "Nombre de la subcategoría")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Subcategory description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Descripción de la subcategoría
   * @return description
   **/
  @Schema(description = "Descripción de la subcategoría")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Subcategory subcategory = (Subcategory) o;
    return Objects.equals(this.idSubcategory, subcategory.idSubcategory) &&
        Objects.equals(this.idCategory, subcategory.idCategory) &&
        Objects.equals(this.name, subcategory.name) &&
        Objects.equals(this.description, subcategory.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idSubcategory, idCategory, name, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Subcategory {\n");
    
    sb.append("    idSubcategory: ").append(toIndentedString(idSubcategory)).append("\n");
    sb.append("    idCategory: ").append(toIndentedString(idCategory)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
