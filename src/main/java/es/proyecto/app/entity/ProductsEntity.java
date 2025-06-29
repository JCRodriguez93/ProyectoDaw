package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * Entidad que representa un producto en la base de datos.
 * <p>
 * Mapea la tabla {@code Products}, que almacena información sobre los productos disponibles,
 * incluyendo su subcategoría, nombre, descripción, precio e imagen.
 * <p>
 * Contiene una relación many-to-one con la entidad {@link SubcategoryEntity}.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Products")
public class ProductsEntity {

    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;

    /**
     * Subcategoría a la que pertenece el producto.
     */
    @ManyToOne
    @JoinColumn(name = "id_subcategory", referencedColumnName = "id_subcategory", nullable = false)
    private SubcategoryEntity idSubcategory;  // Relación con SubcategoryEntity

    /**
     * Nombre del producto.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Descripción del producto.
     */
    @Column(name = "description")
    private String description;

    /**
     * Precio del producto.
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;  // Precio en DECIMAL(10,2)

    /**
     * URL de la imagen asociada al producto.
     */
    @Column(name = "image_url")
    private String imageUrl;

}
