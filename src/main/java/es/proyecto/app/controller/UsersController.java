package es.proyecto.app.controller;

import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.UsersApi;
import es.swagger.codegen.models.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * UsersController es la implementación de UsersApi.
 */
@Slf4j
@RestController
public class UsersController implements UsersApi {

    @Autowired
    private UsersService usersService;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    /**
     * Crea un nuevo usuario en el sistema.
     * <p>
     * Valida que el cuerpo de la petición no sea nulo y que el nombre de usuario esté presente.
     * Verifica que el email no esté duplicado en la base de datos.
     * Si las validaciones fallan, lanza excepciones o devuelve un estado HTTP adecuado.
     * Si la creación es exitosa, devuelve un estado HTTP 201 (CREATED).
     *
     * @param body el objeto {@link User} que contiene los datos del usuario a crear
     * @return un {@link ResponseEntity} con el código HTTP correspondiente a la operación:
     *         - 201 (CREATED) si se crea el usuario correctamente,
     *         - 409 (CONFLICT) si el email ya está en uso
     * @throws UsersException si el cuerpo es nulo o el nombre de usuario es inválido
     */
    @Override
    public ResponseEntity<UserCreatedResponse> createUser(User body) {
        if (body == null) {
            logger.error("Null body provided");
            throw UsersException.NULL_BODY_EXCEPTION;
        }

        if (body.getUserName() == null || body.getUserName().isEmpty()) {
            logger.error("Invalid user name when creating user");
            throw UsersException.MISSING_USER_NAME_EXCEPTION;
        }

        if (usersService.existsByEmail(body.getEmail())) {
            logger.error("Duplicate email in database");
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Retorna 409 Conflict
        }

        usersService.createUser(body);
        logger.info("User created successfully: {}", body.getIdUser());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Elimina un usuario identificado por su ID.
     * <p>
     * Valida que el ID proporcionado sea válido.
     * Busca el usuario por el ID; si no existe, lanza una excepción.
     * Intenta eliminar el usuario usando el servicio correspondiente.
     * Si la eliminación es exitosa, devuelve el código HTTP 204 (NO_CONTENT).
     * En caso de error, lanza una excepción personalizada.
     *
     * @param idUser el identificador del usuario a eliminar
     * @return un {@link ResponseEntity} con el código HTTP 204 (NO_CONTENT) si la eliminación fue exitosa
     * @throws UsersException si el ID es inválido, el usuario no existe o ocurre un error durante la eliminación
     */
    @Override
    public ResponseEntity<DeleteResponse> deleteUser(Integer idUser) {
        if (!isValidId(String.valueOf(idUser))) {
            logger.error("Delete failure. Invalid user ID: {}", idUser);
            throw UsersException.INVALID_USER_ID_EXCEPTION;
        }

        User deletedUser = usersService.getUserById(idUser);

        if (deletedUser == null) {
            logger.error("User with id {} not found", idUser);
            throw UsersException.NO_USER_FOUND_EXCEPTION;
        }

        try {
            usersService.deleteUser(idUser);
            logger.info("User with id {} deleted successfully", idUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting user with id {}. Message: {}", idUser, e.getMessage());
            throw new UsersException("Error deleting user with id " + idUser);
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * <p>
     * Valida que el ID proporcionado sea válido.
     * Si el ID no es válido, devuelve un estado HTTP 400 (BAD_REQUEST).
     * Busca el usuario por ID mediante el servicio de usuarios.
     * Si no se encuentra el usuario, devuelve un estado HTTP 404 (NOT_FOUND).
     * Si se encuentra, devuelve el usuario con estado HTTP 200 (OK).
     * En caso de error durante la consulta, devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @param idUser el identificador del usuario a buscar
     * @return un {@link ResponseEntity} que contiene el usuario si se encuentra, o un estado HTTP apropiado si no
     */
    @Override
    public ResponseEntity<User> getUserById(Integer idUser) {
        try {
            if (!isValidId(String.valueOf(idUser))) {
                logger.error("Invalid user ID format: {}", idUser);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User empleado = usersService.getUserById(idUser);

            if (empleado == null) {
                logger.info("User with id {} not found", idUser);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("User with id {} retrieved successfully", idUser);
                return new ResponseEntity<>(empleado, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving user with id {}. Message: {}", idUser, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene la lista de todos los usuarios.
     * <p>
     * Llama al servicio para obtener todos los usuarios.
     * Si la lista está vacía, lanza una excepción personalizada.
     * En caso de éxito, devuelve un {@link UserResponse} con la lista de usuarios y un estado HTTP 200 (OK).
     * Si ocurre un error, devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @return un {@link ResponseEntity} que contiene un {@link UserResponse} con la lista de usuarios o un estado de error
     */
    @Override
    public ResponseEntity<UserResponse> getUsers() {
        try {
            List<User> userList = usersService.getAllUsers();
            if (userList.isEmpty()) {
                logger.error("No users found");
                throw UsersException.NO_USER_FOUND_EXCEPTION;
            }
            UserResponse response = new UserResponse();
            response.setUsers(userList);
            logger.info("Successfully fetched all users");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UsersException e) {
            logger.error("Error fetching all users: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un usuario existente con los datos proporcionados.
     * <p>
     * Verifica que el cuerpo de la petición no sea nulo.
     * Comprueba si el usuario con el ID dado existe; si no, lanza una excepción.
     * Establece el ID del usuario en el cuerpo para asegurar que coincida con el parámetro.
     * Llama al servicio para actualizar el usuario.
     * En caso de error en el formato del ID, lanza una excepción específica.
     * Captura otras excepciones y lanza una excepción general de error al actualizar.
     *
     * @param idUser el identificador del usuario a actualizar
     * @param body   el objeto {@link User} con los datos actualizados
     * @return un {@link ResponseEntity} con el estado HTTP de la operación
     * @throws UsersException si el cuerpo es nulo, el usuario no existe, el ID es inválido o ocurre un error durante la actualización
     */
    @Override
    public ResponseEntity<Void> updateUser(Integer idUser, User body) {
        try {
            if (body == null) {
                logger.error("Null body provided. Cannot update user");
                throw UsersException.NULL_BODY_EXCEPTION;
            }

            User existingUser = usersService.getUserById(idUser);
            if (existingUser == null) {
                logger.error("No user found with ID {}", idUser);
                throw UsersException.NO_USER_FOUND_EXCEPTION;
            }

            body.setIdUser(idUser);  // Ensure body has the correct ID
            HttpStatus status = usersService.updateUser(idUser, body);
            logger.info("User with id {} updated successfully", idUser);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format: {}", idUser);
            throw UsersException.INVALID_USER_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating user with id {}. Message: {}", idUser, e.getMessage());
            throw UsersException.ERROR_UPDATING_USER_EXCEPTION;
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
