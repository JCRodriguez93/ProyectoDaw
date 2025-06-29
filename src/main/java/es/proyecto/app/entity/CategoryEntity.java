package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

/**
 * Entidad que representa una categoría en el sistema.
 * <p>
 * Cada categoría tiene un identificador único, un nombre y una descripción opcional.
 * Además, mantiene una lista de subcategorías asociadas.
 * <p>
 * Está mapeada a la tabla {@code Category} en la base de datos.
 * <p>
 * La relación con {@link SubcategoryEntity} es de uno a muchos, con
 * carga ansiosa (EAGER) y cascada completa (ALL).
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Category")
public class CategoryEntity {

    /**
     * Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Integer idCategory;

    /**
     * Nombre de la categoría. Debe ser único y no nulo.
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * Descripción opcional de la categoría.
     */
    @Column(name = "description")
    private String description;

    /**
     * Lista de subcategorías asociadas a esta categoría.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER ,  cascade = CascadeType.ALL)
    private List<SubcategoryEntity> subcategories;




}
