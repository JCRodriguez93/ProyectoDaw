package es.proyecto.app.controller;


import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.error.RolesException;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.RolesService;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.RolesApi;
import es.swagger.codegen.api.UsersApi;
import es.swagger.codegen.models.Role;
import es.swagger.codegen.models.RoleResponse;
import es.swagger.codegen.models.User;
import es.swagger.codegen.models.UserResponse;
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
}
