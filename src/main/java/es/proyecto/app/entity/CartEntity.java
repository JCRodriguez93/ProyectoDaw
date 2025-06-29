package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Entidad que representa el carrito de compra de un usuario.
 * <p>
 * Cada instancia representa un producto específico agregado por un usuario
 * junto con la cantidad deseada.
 * <p>
 * Esta entidad está mapeada a la tabla {@code Cart} en la base de datos.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Cart")
public class CartEntity {

    /**
     * Identificador único del registro en la tabla {@code Cart}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Integer idCart;

    /**
     * Usuario que posee este carrito.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity user;

    /**
     * Producto agregado al carrito.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductsEntity product;

    /**
     * Cantidad del producto en el carrito.
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
