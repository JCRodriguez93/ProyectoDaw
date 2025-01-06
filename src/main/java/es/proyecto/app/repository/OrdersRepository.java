package es.proyecto.app.repository;

import es.proyecto.app.entity.OrdersEntity;
import es.swagger.codegen.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    @Query("SELECT o FROM OrdersEntity o WHERE o.user.idUser = :userId AND o.orderStatus = :orderStatus")
    Optional<OrdersEntity> findByUser_IdUserAndOrderStatus(@Param("userId") int userId, @Param("orderStatus") OrderStatus orderStatus);
}
