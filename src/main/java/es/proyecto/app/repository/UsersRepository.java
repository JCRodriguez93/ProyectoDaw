package es.proyecto.app.repository;

import es.proyecto.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    @Query("SELECT u FROM UsersEntity u WHERE u.email = :email")
    Optional<UsersEntity> findByEmail(String email);

    @Query("SELECT u FROM UsersEntity u WHERE u.email = :email AND u.password = :password")
    Optional<UsersEntity> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM UsersEntity u WHERE u.roleId.idRole = :idRole")
    List<UsersEntity> findByRoleId(@Param("idRole") Integer idRole);




}
