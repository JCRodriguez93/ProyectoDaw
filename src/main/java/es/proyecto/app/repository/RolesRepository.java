package es.proyecto.app.repository;

import es.proyecto.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestión de la entidad {@link RolesEntity}.
 * <p>
 * Proporciona métodos CRUD estándar para operaciones sobre roles.
 * </p>
 */
@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {


}
