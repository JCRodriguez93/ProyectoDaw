package es.proyecto.app.repository;

import es.proyecto.app.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de la entidad {@link OrdersEntity}.
 * <p>
 * Proporciona métodos CRUD estándar para operaciones sobre pedidos.
 * </p>
 */
@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    }
