package es.proyecto.app.repository;

import es.proyecto.app.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Integer> {

    @Query("SELECT op FROM OrderProductEntity op WHERE op.idOrder.idOrder = :orderId")
    List<OrderProductEntity> findByOrderId(@Param("orderId") int orderId);

    @Query("SELECT op FROM OrderProductEntity op WHERE op.idOrder.idOrder = :orderId AND op.idProduct.idProduct = :productId")
    Optional<OrderProductEntity> findByOrderIdAndProductId(@Param("orderId") int orderId, @Param("productId") int productId);
}
