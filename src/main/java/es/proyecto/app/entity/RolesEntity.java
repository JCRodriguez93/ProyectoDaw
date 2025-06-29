package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
/**
 * Entidad que representa un rol de usuario en la base de datos.
 * <p>
 * Mapea la tabla {@code Roles}, que almacena los diferentes roles disponibles en el sistema,
 * como por ejemplo "ADMIN", "USER", etc.
 * <p>
 * Cada rol tiene un identificador único y un nombre único.
 *
 * @author Jorge
 */
@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Roles")
public class RolesEntity {

    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;

    /**
     * Nombre único del rol.
     */
    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;


}
