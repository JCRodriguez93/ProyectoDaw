package es.proyecto.app.repository;

import es.proyecto.app.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Integer> {

    // Consulta personalizada para obtener los detalles de un pedido por su ID
    @Query("SELECT od FROM OrderDetailsEntity od WHERE od.order.idOrder = ?1")
    List<OrderDetailsEntity> findByOrderId(int orderId);

    // Consulta personalizada para obtener los detalles de un pedido por su ID de producto
    @Query("SELECT od FROM OrderDetailsEntity od WHERE od.product.idProduct = ?1")
    List<OrderDetailsEntity> findByProductId(int productId);
}
