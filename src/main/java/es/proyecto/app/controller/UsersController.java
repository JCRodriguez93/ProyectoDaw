package es.proyecto.app.controller;


import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.UsersApi;
import es.swagger.codegen.models.*;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    public ResponseEntity<UserCreatedResponse> createUser(User body) {
        if (body == null) {
       //     log.error("Null body provided");
            throw new UsersException("Null body provided");
        }

        if (body.getUserName() == null || body.getUserName().isEmpty()) {
            log.error("Invalid user name");
            throw  UsersException.MISSING_USER_NAME_EXCEPTION;
        }

        usersService.createUser(body);

       // log.info("user created successfully: {}", body.getIdUser());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<DeleteResponse> deleteUser(Integer idUser) {
        if (!isValidId(String.valueOf(idUser))) {
            log.error("Invalid user ID format: {}", idUser);
            throw UsersException.INVALID_USER_ID_EXCEPTION;
        }

        User deletedUser = usersService.getUserById(idUser);

        if (deletedUser == null) {
            log.error("user with id {} not found", idUser);
            throw UsersException.NO_USER_FOUND_EXCEPTION;
        }

        try {
            usersService.deleteUser(idUser);
            log.info("user with id {} deleted successfully", idUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting user with id {}: {}", idUser, e.getMessage());
            throw new UsersException("Error deleting user with id " + idUser);
        }
    }

    @Override
    public ResponseEntity<User> getUserById(Integer idUser) {
        try {
            if (!isValidId(String.valueOf(idUser))) {
                log.error("Invalid user ID format: {}", idUser);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User empleado = usersService.getUserById(idUser);

            if (empleado == null) {
                log.info("user with id {} not found", idUser);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("user with id {} retrieved successfully", idUser);
                return new ResponseEntity<>(empleado, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving user with id {}: {}", idUser, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<UserResponse> getUsers() {
        try {
            List<User> userList = usersService.getAllUsers();
            if (userList.isEmpty()) {
                throw UsersException.NO_USER_FOUND_EXCEPTION;
            }
            UserResponse response = new UserResponse();
            response.setUsers(userList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UsersException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> updateUser(Integer idUser, User body) {

        try {
            if (body == null) {
                log.error("Null body provided");
                throw UsersException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdUser() == null) {
                log.error("user ID is required for updating");
                throw UsersException.MISSING_ID_EXCEPTION;
            }

            User existingEmployee = usersService.getUserById(idUser);
            if (existingEmployee == null) {
                log.error("No user found with ID {}", idUser);
                throw UsersException.NO_USER_FOUND_EXCEPTION;
            }
            HttpStatus status = usersService.updateUser(idUser, body);
            log.info("user with id {} updated successfully", idUser);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format: {}", idUser);
            throw UsersException.INVALID_USER_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating user with id {}: {}", idUser, e.getMessage());
            throw new UsersException("Error updating user");
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
