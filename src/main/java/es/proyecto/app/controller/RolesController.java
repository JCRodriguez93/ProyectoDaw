package es.proyecto.app.controller;


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

    @Autowired
    private RolesService rolesService;

    /**
     * Implementación de la lógica para obtener todos los roles.
     */
    @Override
    public ResponseEntity<List<RoleListResponse>> getRoles() {
        try {
            List<RoleListResponse> rolesList = rolesService.getRoles();
            return new ResponseEntity<>(rolesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Implementación para actualizar un rol existente.
     */
    @Override
    public ResponseEntity<RoleResponse> updateRole(RolesBody body) {
        try {
            // Aquí tomamos el nombre del rol que viene en el body y el servicio lo procesa
            HttpStatus status = rolesService.putRoleById(body.getIdRole(), body.getRoleName());

            if (status == HttpStatus.OK) {
                // Devolver la información del rol actualizado
                RoleResponse response = new RoleResponse();
                response.setIdRole(body.getIdRole());
                response.setRoleName(body.getRoleName());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}