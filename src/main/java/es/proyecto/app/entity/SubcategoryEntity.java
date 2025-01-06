package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private Integer idSubcategory;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category", nullable = false)
    private CategoryEntity category;  // Relación con CategoryEntity


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;


}
