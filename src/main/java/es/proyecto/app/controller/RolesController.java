package es.proyecto.app.controller;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.error.RolesException;
import es.proyecto.app.service.RolesService;
import es.swagger.codegen.api.RolesApi;
import es.swagger.codegen.models.Role;
import es.swagger.codegen.models.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST para la gestión de roles del sistema.
 * <p>
 * Implementa la interfaz {@link RolesApi} generada por Swagger Codegen,
 * proporcionando los endpoints para obtener y modificar roles.
 * </p>
 * <p>
 * Utiliza {@link RolesService} para las operaciones de negocio y manejo de datos.
 * Maneja excepciones específicas {@link RolesException} para controlar errores comunes.
 * </p>
 *
 * @author Jorge
 */
@Slf4j
@RestController
public class RolesController implements RolesApi {

    @Autowired
    private RolesService rolesService;

    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    /**
     * Obtiene la lista completa de roles disponibles.
     * <p>
     * Llama al servicio de roles para recuperar todos los roles.
     * Si no se encuentran roles, lanza la excepción {@link RolesException#NO_ROLE_FOUND_EXCEPTION}.
     * En caso de éxito, devuelve un {@link ResponseEntity} con la lista de roles y el estado HTTP 200 (OK).
     * Si ocurre un error durante la obtención, captura la excepción y devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @return un {@link ResponseEntity} que contiene un objeto {@link RoleResponse} con la lista de roles y el código HTTP correspondiente
     * @throws RolesException si no se encuentran roles
     */
    @Override
    public ResponseEntity<RoleResponse> getRoles() throws RolesException {
        try {
            List<Role> rolesEntityList = rolesService.getAllRoles();

            if (rolesEntityList.isEmpty()) {
                logger.error("No roles found");
                throw RolesException.NO_ROLE_FOUND_EXCEPTION;
            }

            RoleResponse response = new RoleResponse();
            response.setRoles(rolesEntityList);
            logger.info("Successfully fetched all roles");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RolesException e) {
            logger.error("Error fetching all roles: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un rol existente identificado por su ID.
     * <p>
     * Valida el formato del ID proporcionado. Si el ID no es válido, lanza {@link RolesException#INVALID_ID_EXCEPTION}.
     * Busca el rol por ID; si no se encuentra, lanza {@link RolesException#NO_ROLE_FOUND_EXCEPTION}.
     * Actualiza el nombre del rol con la información recibida en el cuerpo de la petición.
     * En caso de error en el formato del ID o en la actualización, lanza {@link RolesException#ERROR_UPDATING_ROLE_EXCEPTION}.
     *
     * @param idRole el identificador del rol a actualizar, como cadena
     * @param body un objeto {@link Role} con los datos para actualizar el rol
     * @return un {@link ResponseEntity} con estado 200 (OK) si la actualización fue exitosa
     * @throws RolesException si el ID no es válido, el rol no se encuentra, o ocurre un error durante la actualización
     */
    @Override
    public ResponseEntity<Role> putRoleId(String idRole, Role body) throws RolesException {
        try {
            if (!isValidId(idRole)) {
                logger.error("Invalid role ID format: {}", idRole);
                throw RolesException.INVALID_ID_EXCEPTION;
            }
            RolesEntity rolesEntity = rolesService.getRoleById(Integer.parseInt(idRole));

            if (rolesEntity == null) {
                logger.error("Role with id {} not found", idRole);
                throw RolesException.NO_ROLE_FOUND_EXCEPTION;
            }

            rolesEntity.setIdRole(Integer.parseInt(idRole));
            rolesEntity.setRoleName(body.getRoleName());
            rolesService.updateRole(rolesEntity);
            logger.info("Role with id {} updated successfully", idRole);

            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            logger.error("Invalid ID format: {}", idRole);
            throw  RolesException.ERROR_UPDATING_ROLE_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating role with id {}. Message: {}", idRole, e.getMessage());
            throw  RolesException.ERROR_UPDATING_ROLE_EXCEPTION;
        }
    }

    /**
     * Verifica si una cadena dada representa un identificador numérico válido.
     * <p>
     * Este método intenta convertir la cadena a un {@code Integer}. Si la conversión tiene éxito,
     * se considera un ID válido. Si lanza una excepción {@code NumberFormatException},
     * se considera inválido.
     *
     * @param id la cadena a validar como identificador numérico.
     * @return {@code true} si la cadena puede convertirse a {@code Integer},
     *         {@code false} en caso contrario.
     */
    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
