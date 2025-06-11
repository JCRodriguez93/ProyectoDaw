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
 * SubcategoriesResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class SubcategoriesResponse   {
  @JsonProperty("subcategories")
  @Valid
  private List<Subcategory> subcategories = null;

  public SubcategoriesResponse subcategories(List<Subcategory> subcategories) {
    this.subcategories = subcategories;
    return this;
  }

  public SubcategoriesResponse addSubcategoriesItem(Subcategory subcategoriesItem) {
    if (this.subcategories == null) {
      this.subcategories = new ArrayList<Subcategory>();
    }
    this.subcategories.add(subcategoriesItem);
    return this;
  }

  /**
   * Lista de subcategorías disponibles
   * @return subcategories
   **/
  @Schema(description = "Lista de subcategorías disponibles")
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
    SubcategoriesResponse subcategoriesResponse = (SubcategoriesResponse) o;
    return Objects.equals(this.subcategories, subcategoriesResponse.subcategories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subcategories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubcategoriesResponse {\n");
    
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
