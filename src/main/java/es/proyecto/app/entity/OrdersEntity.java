package es.proyecto.app.entity;

import es.swagger.codegen.models.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un pedido realizado por un usuario.
 * <p>
 * Mapea la tabla {@code Orders} que almacena la información básica de cada pedido,
 * incluyendo la relación con el usuario que lo realiza, cantidad total, precio total,
 * fecha y estado del pedido.
 * <p>
 * Contiene una relación many-to-one con la entidad {@link UsersEntity}.
 * El estado del pedido se gestiona mediante el enumerado {@link OrderStatus}.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Orders")
public class OrdersEntity {


    /**
     * Identificador único del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Integer idOrder;

    /**
     * Usuario que realiza el pedido.
     */
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity user;  // Relación con UserEntity

    /**
     * Cantidad total de productos en el pedido.
     */
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    /**
     * Precio total del pedido.
     */
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    /**
     * Fecha y hora en que se realizó el pedido.
     */
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    /**
     * Estado actual del pedido.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;  // Enum con los estados del pedido



}
