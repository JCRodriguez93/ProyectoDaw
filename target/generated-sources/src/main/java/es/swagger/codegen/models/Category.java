package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import es.swagger.codegen.models.Subcategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Category
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T21:40:46.220561900+02:00[Europe/Madrid]")


public class Category   {
  @JsonProperty("id_category")
  private Integer idCategory = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("subcategories")
  @Valid
  private List<Subcategory> subcategories = null;

  public Category idCategory(Integer idCategory) {
    this.idCategory = idCategory;
    return this;
  }

  /**
   * Identificador único de la categoría
   * @return idCategory
   **/
  @Schema(description = "Identificador único de la categoría")
  
    public Integer getIdCategory() {
    return idCategory;
  }

  public void setIdCategory(Integer idCategory) {
    this.idCategory = idCategory;
  }

  public Category name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Nombre de la categoría
   * @return name
   **/
  @Schema(description = "Nombre de la categoría")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Descripción de la categoría
   * @return description
   **/
  @Schema(description = "Descripción de la categoría")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Category subcategories(List<Subcategory> subcategories) {
    this.subcategories = subcategories;
    return this;
  }

  public Category addSubcategoriesItem(Subcategory subcategoriesItem) {
    if (this.subcategories == null) {
      this.subcategories = new ArrayList<Subcategory>();
    }
    this.subcategories.add(subcategoriesItem);
    return this;
  }

  /**
   * Lista de subcategorías asociadas a esta categoría
   * @return subcategories
   **/
  @Schema(description = "Lista de subcategorías asociadas a esta categoría")
      @Valid
    public List<Subcategory> getSubcategories() {
    return subcategories;
  }

  public void setSubcategories(List<Subcategory> subcategories) {
    this.subcategories = subcategories;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(this.idCategory, category.idCategory) &&
        Objects.equals(this.name, category.name) &&
        Objects.equals(this.description, category.description) &&
        Objects.equals(this.subcategories, category.subcategories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCategory, name, description, subcategories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Category {\n");
    
    sb.append("    idCategory: ").append(toIndentedString(idCategory)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    subcategories: ").append(toIndentedString(subcategories)).append("\n");
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
