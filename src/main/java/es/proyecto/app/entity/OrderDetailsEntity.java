package es.proyecto.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Entity
@Table(name = "Order_Details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail")
    private int idDetail;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", nullable = false)
    private OrdersEntity order;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductsEntity product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

}
