package es.proyecto.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

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

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_category")
    private CategoryEntity category;

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