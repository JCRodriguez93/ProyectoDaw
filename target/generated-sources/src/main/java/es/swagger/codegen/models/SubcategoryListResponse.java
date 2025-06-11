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
 * SubcategoryListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class SubcategoryListResponse   {
  @JsonProperty("subcategories")
  @Valid
  private List<Subcategory> subcategories = new ArrayList<Subcategory>();

  public SubcategoryListResponse subcategories(List<Subcategory> subcategories) {
    this.subcategories = subcategories;
    return this;
  }

  public SubcategoryListResponse addSubcategoriesItem(Subcategory subcategoriesItem) {
    this.subcategories.add(subcategoriesItem);
    return this;
  }

  /**
   * Get subcategories
   * @return subcategories
   **/
  @Schema(required = true, description = "")
      @NotNull
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
    SubcategoryListResponse subcategoryListResponse = (SubcategoryListResponse) o;
    return Objects.equals(this.subcategories, subcategoryListResponse.subcategories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subcategories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubcategoryListResponse {\n");
    
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
