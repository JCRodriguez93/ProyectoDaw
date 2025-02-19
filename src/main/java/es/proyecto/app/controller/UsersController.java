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



    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
