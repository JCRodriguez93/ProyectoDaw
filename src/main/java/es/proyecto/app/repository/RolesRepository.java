package es.proyecto.app.repository;

import es.proyecto.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {

    // Consulta personalizada para encontrar un rol por su nombre
    @Query("SELECT r FROM RolesEntity r WHERE r.roleName = ?1")
    Optional<RolesEntity> findByRoleName(String roleName);
}
