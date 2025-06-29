package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Entidad que representa la relación entre un pedido y los productos incluidos en él.
 * <p>
 * Mapea la tabla {@code Order_Products} que contiene los productos asociados a cada pedido,
 * junto con la cantidad de cada producto.
 * <p>
 * Establece relaciones many-to-one con las entidades {@link OrdersEntity} y {@link ProductsEntity}.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Order_Products")
public class OrderProductEntity {

    /**
     * Identificador único de la relación pedido-producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_product")
    private Integer idOrderProduct;


    /**
     * Pedido asociado.
     */
    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", nullable = false)
    private OrdersEntity idOrder;

    /**
     * Producto asociado.
     */
    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductsEntity idProduct;

    /**
     * Cantidad del producto en el pedido.
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
