package es.proyecto.app.repository;

import es.proyecto.app.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de la entidad {@link OrderProductEntity}.
 * <p>
 * Proporciona métodos para acceder y manipular los productos asociados a pedidos,
 * incluyendo consultas personalizadas para obtener productos por ID de pedido
 * y combinaciones de ID de pedido y producto.
 * </p>
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Integer> {

    /**
     * Obtiene una lista de productos asociados a un pedido específico.
     *
     * @param orderId el ID del pedido para el cual se quieren obtener los productos.
     * @return lista de entidades {@link OrderProductEntity} que pertenecen al pedido indicado.
     */
    @Query("SELECT op FROM OrderProductEntity op WHERE op.idOrder.idOrder = :orderId")
    List<OrderProductEntity> findByOrderId(@Param("orderId") int orderId);

    /**
     * Busca un producto específico dentro de un pedido dado.
     *
     * @param orderId   el ID del pedido.
     * @param productId el ID del producto.
     * @return un {@link Optional} que contiene la entidad {@link OrderProductEntity} si existe,
     *         o vacío si no se encuentra ninguna coincidencia.
     */
    @Query("SELECT op FROM OrderProductEntity op WHERE op.idOrder.idOrder = :orderId AND op.idProduct.idProduct = :productId")
    Optional<OrderProductEntity> findByOrderIdAndProductId(@Param("orderId") int orderId, @Param("productId") int productId);
}
