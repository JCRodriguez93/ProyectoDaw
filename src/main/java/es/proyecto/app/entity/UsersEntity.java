package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;

/**
 * Entidad que representa a un usuario en la base de datos.
 * <p>
 * Mapea la tabla {@code Users}, que almacena la información personal y de acceso
 * de los usuarios registrados en el sistema.
 * <p>
 * Cada usuario está asociado a un rol que determina sus permisos y nivel de acceso.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class UsersEntity {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    /**
     * Nombre del usuario.
     */
    @Column(name = "user_name", nullable = false)
    private String userName;

    /**
     * Apellidos del usuario.
     */
    @Column(name = "user_surname", nullable = false)
    private String userSurname;

    /**
     * Fecha de nacimiento del usuario.
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    /**
     * Correo electrónico único del usuario.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * Contraseña cifrada del usuario.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Rol asignado al usuario.
     * Relación Many-to-One con la entidad RolesEntity.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id_role", nullable = false)
    private RolesEntity roleId;



}
