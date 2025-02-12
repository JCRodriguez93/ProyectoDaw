package es.proyecto.app.Controller;

import es.proyecto.app.controller.RolesController;
import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.error.RolesException;
import es.proyecto.app.service.RolesService;
import es.swagger.codegen.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RolesControllerTest {

    @Mock
    private RolesService rolesService;

    @InjectMocks
    private RolesController rolesController;

    private Role role;

    @BeforeEach
    public void setup() {
        // Inicializamos los mocks de Mockito
        MockitoAnnotations.openMocks(this);

        // Creamos un rol de prueba
        role = new Role();
        role.setRoleName("Admin");
    }

    @Test
    @DisplayName("Actualizar rol con éxito")
    public void updateRoleSuccessThenReturnOK() {
        // Simulamos que el rol existe
        when(rolesService.getRoleById(1)).thenReturn(new RolesEntity());

        // Llamamos al método del controlador para actualizar el rol
        ResponseEntity<Role> response = rolesController.putRoleId("1", role);

        // Verificamos que la respuesta tenga el código de estado OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el servicio se haya llamado para actualizar el rol
        verify(rolesService).updateRole(any(RolesEntity.class));
    }

    @Test
    @DisplayName("Actualizar rol con ID no válido")
    public void updateRoleWithNotValidIdThenThrowException() throws RolesException {
        // Llamamos al método con un ID no válido y esperamos la excepción correspondiente
        RolesException exception = assertThrows(RolesException.class, () -> {
            rolesController.putRoleId("invalid", role);
        });

        // Verificamos que la excepción tenga el mensaje correcto
        assertEquals("Error updating role", exception.getMessage());
    }

    @Test
    @DisplayName("Actualizar rol no encontrado")
    public void updateRoleNotFoundThenThrowException() throws RolesException {
        // Simulamos que el rol no existe
        when(rolesService.getRoleById(1)).thenReturn(null);

        // Llamamos al método del controlador y esperamos la excepción correspondiente
        RolesException exception = assertThrows(RolesException.class, () -> {
            rolesController.putRoleId("1", role);
        });

        // Verificamos que la excepción tenga el mensaje correcto
        assertEquals("Error updating role", exception.getMessage());
    }

    @Test
    @DisplayName("Error al actualizar rol")
    public void updateRoleErrorThenThrowException() throws RolesException {
        // Simulamos una excepción al intentar actualizar el rol
        RolesEntity rolesEntity = new RolesEntity();
        when(rolesService.getRoleById(1)).thenReturn(rolesEntity);
        doThrow(RolesException.ERROR_UPDATING_ROLE_EXCEPTION).when(rolesService).updateRole(rolesEntity);

        // Llamamos al método del controlador y esperamos la excepción correspondiente
        RolesException exception = assertThrows(RolesException.class, () -> {
            rolesController.putRoleId("1", role);
        });

        // Verificamos que la excepción tenga el mensaje correcto
        assertEquals("Error updating role", exception.getMessage());
    }


}