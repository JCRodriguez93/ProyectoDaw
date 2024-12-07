package es.proyecto.app.controller;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.error.RolesException;
import es.proyecto.app.service.RolesService;
import es.swagger.codegen.api.RolesApi;
import es.swagger.codegen.models.RoleListResponse;
import es.swagger.codegen.models.RoleResponse;
import es.swagger.codegen.models.RolesBody;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public ResponseEntity<List<RoleListResponse>> getRoles() {
        return null;
    }

    @Override
    public ResponseEntity<RoleResponse> updateRole(RolesBody body) {
        return null;
    }



//    @Autowired
//    private RolesService rolesService;
//
//    @Autowired
//    private RolesMapper rolesMapper;
//
//    /**
//     * Obtiene la lista completa de roles.
//     */
//    @Override
//    public ResponseEntity<RoleListResponse> getRoles() {
//        try {
//            log.info("Fetching all roles");
//            List<RoleResponse> roles = rolesService.getRoles();
//
//            if (roles.isEmpty()) {
//                log.info("No roles found");
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            RoleListResponse response = new RoleListResponse();
//            response.setRoles(roles);
//            log.info("Roles fetched successfully from database");
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error while fetching roles: {}", e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Actualiza la información de un rol existente.
//     */
//    @Override
//    public ResponseEntity<RoleResponse> putRole(Integer idRole, RolesIdRoleBody body) {
//        try {
//            if (body == null || body.getNewRole() == null || body.getNewRole().isEmpty()) {
//                log.error("Invalid request body or missing role name");
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            log.info("Attempting to update role with id: {} and role name: {}", idRole, body.getNewRole());
//            HttpStatus status = rolesService.putRoleById(idRole, body.getNewRole());
//
//            if (status == HttpStatus.NOT_FOUND) {
//                log.error("Role with id {} not found", idRole);
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            log.info("Role updated successfully with id: {}", idRole);
//            RolesEntity updatedRole = rolesService.getRoleById(idRole);
//            RoleResponse response = rolesMapper.toApiDomain(updatedRole);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//
//        } catch (RolesException e) {
//            log.error("RolesException: {}", e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            log.error("Unexpected exception: {}", e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}