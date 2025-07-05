package es.proyecto.app.Controller;

import es.proyecto.app.controller.UsersController;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.models.DeleteResponse;
import es.swagger.codegen.models.User;
import es.swagger.codegen.models.UserCreatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializamos los mocks de Mockito
        MockitoAnnotations.openMocks(this);

        // Creamos un usuario de prueba
        user = new User();
        user.setIdUser(1);
        user.setUserName("JohnDoe");
        user.setUserSurname("Doe");
        user.setUserBirth(LocalDateTime.now());
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
    }

    //hecho
    @Test
    @DisplayName("Crear usuario con datos validos")
    public void createUserWithValidDataAndThenReturnCreated() {
        // Configuramos el mock para que el correo del usuario NO exista
        when(usersService.existsByEmail(user.getEmail())).thenReturn(false);

        // Llamamos al método del controlador para crear el usuario
        ResponseEntity<UserCreatedResponse> response = usersController.createUser(user);

        // Verificamos que el status HTTP devuelto sea CREATED (201)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Verificamos que el servicio de usuarios se llame con el usuario correcto
        verify(usersService).createUser(user);
    }
    @Test
    @DisplayName("Crear usuario con email duplicado → 409 CONFLICT")
    void createUserWithDuplicateEmailAndThenReturnConflict() {
        // 1. Creamos un User (modelo de API) con un email que ya existe
        User duplicateUser = new User();
        duplicateUser.setUserName("John");
        duplicateUser.setUserSurname("Doe");
        duplicateUser.setUserBirth(LocalDateTime.now());
        duplicateUser.setEmail("duplicate@example.com");
        duplicateUser.setPassword("password123");
        duplicateUser.setRoleId(2); // obligatorio

        // 2. Simulamos que ese email ya está registrado
        when(usersService.existsByEmail(duplicateUser.getEmail())).thenReturn(true);

        // 3. Llamamos al endpoint
        ResponseEntity<UserCreatedResponse> response = usersController.createUser(duplicateUser);

        // 4. Verificamos que devuelve 409 CONFLICT
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    //hecho
    @Test
    @DisplayName("Crear usuario con nombre vacio")
    public void createUserWithEmptyNameAndThenThrowException() throws UsersException {
        // Establecemos el nombre del usuario vacío para simular error
        user.setUserName("");

        // Llamamos al método del controlador y esperamos que se lance la excepción correspondiente
        assertThrows(UsersException.class, () -> {
            usersController.createUser(user);
        });
    }
    //hecho
    @Test
    @DisplayName("obtener usuario por id")
    public void getUserByIdFoundAndThenReturnOK() {
        // Configuramos el mock para que el usuario exista
        when(usersService.getUserById(1)).thenReturn(user);

        // Llamamos al método del controlador para obtener el usuario por ID
        ResponseEntity<User> response = usersController.getUserById(1);

        // Verificamos que se retorna un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verificamos que el usuario retornado sea el esperado
        assertEquals(user, response.getBody());
    }
    //hecho
    @Test
    @DisplayName("obtener usuario por id no encontrado")
    public void getUserByIdNotFoundAndThenThrowNotFoundResponse() {
        // Configuramos el mock para que no se encuentre el usuario (retorne null)
        when(usersService.getUserById(1)).thenReturn(null);

        // Llamamos al método del controlador para obtener el usuario
        ResponseEntity<User> response = usersController.getUserById(1);

        // Verificamos que se retorne un status NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //hecho
    @Test
    @DisplayName("borrar usuario con id valido")
    public void deleteUserValidAndThenReturnNoContent() {
        // Configuramos el mock para que el usuario exista
        when(usersService.getUserById(1)).thenReturn(user);

        // Llamamos al método del controlador para eliminar el usuario
        ResponseEntity<DeleteResponse> response = usersController.deleteUser(1);

        // Verificamos que se retorne un status NO_CONTENT (204)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // Verificamos que se llame al método deleteUser del servicio con el ID correcto
        verify(usersService).deleteUser(1);
    }
    //hecho
    @Test
    @DisplayName("borrar usuario inexistente")
    public void deleteUserNotFoundAndThenThrowException() throws UsersException {
        // Configuramos el mock para que no se encuentre el usuario
        when(usersService.getUserById(1)).thenReturn(null);

        // Llamamos al método del controlador y esperamos la excepción correspondiente
        assertThrows(UsersException.class, () -> {
            usersController.deleteUser(1);
        });
    }
    //hecho
    @Test
    @DisplayName("actualizar usuario con datos validos")
    public void updateUserValidAndThenReturnOK() {
        // Creamos un usuario para la actualización
        User updatedUser = new User();
        updatedUser.setIdUser(1);  // Aseguramos que el ID no sea nulo
        updatedUser.setUserName("JaneDoe");

        // Configuramos el mock para que el usuario exista y se actualice correctamente
        when(usersService.getUserById(1)).thenReturn(user);
        when(usersService.updateUser(1, updatedUser)).thenReturn(HttpStatus.OK);

        // Llamamos al método del controlador para actualizar el usuario
        ResponseEntity<Void> response = usersController.updateUser(1, updatedUser);

        // Verificamos que se retorne un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verificamos que se haya llamado al método updateUser del servicio con los parámetros correctos
        verify(usersService).updateUser(1, updatedUser);
    }
    //hecho
    @Test
    @DisplayName("actualizar usuario no encontrado")
    public void updateUserNotFoundAndThenThrowException() throws UsersException {
        // Configuramos el mock para que no se encuentre el usuario (retorne null)
        when(usersService.getUserById(1)).thenReturn(null);

        // Creamos un usuario para la actualización
        User updatedUser = new User();
        updatedUser.setUserName("JaneDoe");

        // Llamamos al método del controlador y esperamos la excepción correspondiente
        assertThrows(UsersException.class, () -> {
            usersController.updateUser(1, updatedUser);
        });
    }

}
