package es.proyecto.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Order_Products")
public class OrderProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_product")
    private Integer idOrderProduct;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", nullable = false)
    private OrdersEntity idOrder;  // Relación con OrderEntity

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductsEntity idProduct;  // Relación con ProductEntity

    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    public Integer getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(Integer idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public OrdersEntity getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(OrdersEntity idOrder) {
        this.idOrder = idOrder;
    }

    public ProductsEntity getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(ProductsEntity idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}