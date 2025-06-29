package es.proyecto.app.repository;

import es.proyecto.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para la gestión de la entidad {@link UsersEntity}.
 * <p>
 * Proporciona métodos CRUD estándar y consultas personalizadas para usuarios,
 * incluyendo verificación de existencia por correo electrónico y búsqueda por correo.
 * </p>
 */
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    /**
     * Comprueba si existe un usuario con el correo electrónico especificado.
     *
     * @param email Correo electrónico a verificar.
     * @return {@code true} si existe un usuario con ese email; {@code false} en caso contrario.
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM UsersEntity u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario a buscar.
     * @return La entidad {@link UsersEntity} que corresponde al correo dado, o {@code null} si no existe.
     */
    UsersEntity findByEmail(String email);
}
