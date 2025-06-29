package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
/**
 * Entidad que representa una subcategoría dentro de una categoría en la base de datos.
 * <p>
 * Mapea la tabla {@code Subcategory}, que almacena las subcategorías asociadas a categorías
 * principales, permitiendo organizar los productos o elementos en niveles jerárquicos.
 * <p>
 * Cada subcategoría está vinculada a una única categoría padre.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Subcategory")
public class SubcategoryEntity {

    /**
     * Identificador único de la subcategoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategory")
    private Integer idSubcategory;

    /**
     * Categoría a la que pertenece esta subcategoría.
     */
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category", nullable = false)
    private CategoryEntity category;

    /**
     * Nombre de la subcategoría.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Descripción opcional de la subcategoría.
     */
    @Column(name = "description")
    private String description;



}
