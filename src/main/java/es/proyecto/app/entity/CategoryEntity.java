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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private int idCategory;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "idSubcategory", cascade = CascadeType.ALL, orphanRemoval = true) // Cambiar 'mappedBy' a 'category'
    private Set<SubcategoryEntity> subcategories;

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
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

    public Set<SubcategoryEntity> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<SubcategoryEntity> subcategories) {
        this.subcategories = subcategories;
    }
}

