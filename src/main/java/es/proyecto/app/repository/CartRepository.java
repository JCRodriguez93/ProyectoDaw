package es.proyecto.app.repository;

import es.proyecto.app.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findByUser_IdUser(Integer userId);
    Optional<CartEntity> findByUser_IdUserAndProduct_IdProduct(Integer userId, Integer productId);
}
