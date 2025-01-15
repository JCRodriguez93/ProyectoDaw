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
 * RolesController es la implementación de RolesApi.
 */
@Slf4j
@RestController
public class RolesController implements RolesApi {

    @Autowired
    private RolesService rolesService;

    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Override
    public ResponseEntity<RoleResponse> getRoles() throws RolesException {
        try {
            List<Role> rolesEntityList = rolesService.getAllRoles();

            if (rolesEntityList.isEmpty()) {
                logger.error("No roles found in getRoles");
                throw RolesException.NO_ROLE_FOUND_EXCEPTION;
            }

            RoleResponse response = new RoleResponse();
            response.setRoles(rolesEntityList);
            logger.info("Successfully fetched all roles in getRoles");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RolesException e) {
            logger.error("Error fetching all roles in getRoles: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Role> putRoleId(String idRole, Role body) throws RolesException {
        try {
            if (!isValidId(idRole)) {
                logger.error("Invalid role ID format in putRoleId: {}", idRole);
                throw RolesException.INVALID_ID_EXCEPTION;
            }
            RolesEntity rolesEntity = rolesService.getRoleById(Integer.parseInt(idRole));

            if (rolesEntity == null) {
                logger.error("Role with id {} not found in putRoleId", idRole);
                throw RolesException.NO_ROLE_FOUND_EXCEPTION;
            }

            rolesEntity.setIdRole(Integer.parseInt(idRole));
            rolesEntity.setRoleName(body.getRoleName());
            rolesService.updateRole(rolesEntity);
            logger.info("Role with id {} updated successfully in putRoleId", idRole);

            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            logger.error("Invalid ID format in putRoleId: {}", idRole);
            throw  RolesException.ERROR_UPDATING_ROLE_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating role with id {} in putRoleId: {}", idRole, e.getMessage());
            throw  RolesException.ERROR_UPDATING_ROLE_EXCEPTION;
        }
    }

    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            logger.error("Invalid ID format in isValidId: {}", id);
            return false;
        }
    }
}
