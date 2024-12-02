package es.proyecto.app.repository;

import es.proyecto.app.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    // Consulta personalizada para encontrar un pedido por su ID de usuario y estado
    @Query("SELECT o FROM OrdersEntity o WHERE o.user.idUser = ?1 AND o.status = ?2")
    List<OrdersEntity> findByUserIdAndStatus(int userId, String status);

    // Consulta personalizada para obtener todos los pedidos de un usuario
    @Query("SELECT o FROM OrdersEntity o WHERE o.user.idUser = ?1")
    List<OrdersEntity> findAllByUserId(int userId);

    // Consulta personalizada para encontrar un pedido por su ID
    Optional<OrdersEntity> findByIdOrder(int idOrder);
}
