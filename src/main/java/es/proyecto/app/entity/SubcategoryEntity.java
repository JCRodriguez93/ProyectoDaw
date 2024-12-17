package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Subcategory")
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


}
