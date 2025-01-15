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
@Table(name = "Order_Products")
public class OrderProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_product")
    private Integer idOrderProduct;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", nullable = false)
    private OrdersEntity idOrder;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductsEntity idProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
