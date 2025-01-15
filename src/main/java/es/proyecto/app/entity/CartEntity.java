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
@Table(name = "Cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Integer idCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductsEntity product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
