package es.proyecto.app.repository;

import es.proyecto.app.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar entidades de carrito de compra (CartEntity).
 * <p>
 * Proporciona métodos para acceder a carritos asociados a usuarios específicos,
 * incluyendo búsquedas por usuario y por combinación de usuario y producto.
 * </p>
 */
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    /**
     * Busca todos los carritos asociados a un usuario dado.
     *
     * @param userId id del usuario.
     * @return lista de entidades CartEntity asociadas al usuario.
     */
    List<CartEntity> findByUser_IdUser(Integer userId);

    /**
     * Busca un carrito que coincida con el usuario y producto dados.
     *
     * @param userId    id del usuario.
     * @param productId id del producto.
     * @return Optional con la entidad CartEntity si existe.
     */
    Optional<CartEntity> findByUser_IdUserAndProduct_IdProduct(Integer userId, Integer productId);
}
