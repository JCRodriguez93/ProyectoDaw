package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Entity
@Table(name = "Subcategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategory")
    private int idSubcategory;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category", nullable = false)
    private CategoryEntity category;  // Relación con CategoryEntity

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // Métodos getter y setter
    public int getIdSubcategory() {
        return idSubcategory;
    }

    public void setIdSubcategory(int idSubcategory) {
        this.idSubcategory = idSubcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
