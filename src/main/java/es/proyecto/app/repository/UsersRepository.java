package es.proyecto.app.repository;

import es.proyecto.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    // Consulta personalizada para encontrar un usuario por su nombre de usuario
    @Query("SELECT u FROM UsersEntity u WHERE u.userName = ?1")
    Optional<UsersEntity> findByUserName(String userName);

    // Consulta personalizada para encontrar un usuario por su correo electrónico
    @Query("SELECT u FROM UsersEntity u WHERE u.email = ?1")
    Optional<UsersEntity> findByEmail(String email);

    // Consulta personalizada para encontrar un usuario por su nombre de usuario y correo
    @Query("SELECT u FROM UsersEntity u WHERE u.userName = ?1 AND u.email = ?2")
    Optional<UsersEntity> findByUserNameAndEmail(String userName, String email);
}
