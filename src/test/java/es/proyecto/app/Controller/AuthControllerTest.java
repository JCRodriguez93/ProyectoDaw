package es.proyecto.app.Controller;

import es.proyecto.app.controller.AuthController;
import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.UsersMapper;
import es.proyecto.app.security.JwtTokenProvider;
import es.proyecto.app.service.RolesService;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private UsersService usersService;

    @MockBean
    private RolesService rolesService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UsersMapper usersMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private UsersEntity userEntity;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private RolesEntity rolesEntity;



    @BeforeEach
    void setUp() {


        // Creamos una instancia de UsersEntity simulando un usuario existente
        userEntity = UsersEntity.builder()
                .email("test@test.com")
                .password("encodedPassword") // Contraseña ya codificada
                .build();

        // Creamos una instancia de RolesEntity para representar un rol en el sistema
        rolesEntity = RolesEntity.builder()
                .idRole(1) // ID del rol a asignar
                .build();

        // Creamos una instancia de LoginRequest para simular una solicitud de inicio de sesión.
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com"); // Usamos el mismo email del usuario existente.
        loginRequest.setPassword("password"); // Contraseña en texto plano para comparar.

        // Creamos una instancia de RegisterRequest para simular una solicitud de registro de un nuevo usuario.
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("newuser@test.com"); // Email de un usuario nuevo que intentará registrarse.
        registerRequest.setPassword("password"); // Contraseña en texto plano.

    }


    @Test
    @DisplayName("Login correcto de usuario existente y credenciales correctas")
    void loginUserWithValidCredentialsThenReturnsOK() {
        // Simulamos que el usuario existe en la base de datos
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(userEntity);

        // Simulamos que la contraseña ingresada es correcta
        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())).thenReturn(true);

        // Simulamos la generación del token JWT para el usuario autenticado
        when(jwtTokenProvider.generateToken(userEntity.getEmail())).thenReturn("token");

        // Llamamos al método de login del controlador
        ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);

        // Verificamos que la respuesta tenga un código de estado HTTP 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta no sea nulo
        assertNotNull(response.getBody());

        // Comprobamos que el mensaje de éxito sea el esperado
        assertEquals("Inicio de sesión exitoso", response.getBody().getMessage());

        // Verificamos que el token generado es el esperado
        assertEquals("token", response.getBody().getToken());
    }
    @Test
    @DisplayName("Login incorrecto de usuario existente y credenciales incorrectas")
    void loginUserWithInvalidCredentialsThenReturnsUnauthorized() {
        // Simulamos que el usuario existe en la base de datos
        when(usersService.findByEmail(loginRequest.getEmail()))
                .thenReturn(userEntity);

        // Simulamos que la contraseña es incorrecta
        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword()))
                .thenReturn(false);

        // Intentamos hacer login con credenciales inválidas
        ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);

        // Verificamos que devuelve un 401 Unauthorized
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Credenciales incorrectas", response.getBody().getError());
    }
    @Test
    @DisplayName("Login incorrecto de usuario inexistente en la base de datos")
    void loginUserWithNonExistentUserThenReturnsUnauthorized() {
        // Simulamos que el usuario no existe en la base de datos (findByEmail devuelve null)
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(null);

        // Intentamos hacer login con un usuario inexistente
        ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);

        // Verificamos que devuelve un 401 Unauthorized
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta no sea nulo
        assertNotNull(response.getBody());

        // Comprobamos que el mensaje de error sea el esperado
        assertEquals("Credenciales incorrectas", response.getBody().getError());
    }
    @Test
    @DisplayName("Conflicto de usuario actualmente en la base de datos")
    void registerUserWithEmailAlreadyInUseThenReturnsConflict() {
        // Simulamos que el email ya está registrado en el sistema
        when(usersService.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // Llamamos al método de registro del controlador
        ResponseEntity<AuthResponse> response = authController.registerUser(registerRequest);

        // Verificamos que la respuesta tenga un código de estado HTTP 409 (CONFLICT)
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta no sea nulo
        assertNotNull(response.getBody());

        // Comprobamos que el mensaje de error sea el esperado
        assertEquals("Email ya está en uso", response.getBody().getError());
    }
    @Test
    @DisplayName("Registro correcto de usuario")
    void registerUserWithValidDetailsThenReturnsCreated() {
        // Simulamos que el email NO está registrado en el sistema
        when(usersService.existsByEmail(registerRequest.getEmail())).thenReturn(false);

        // Simulamos que el servicio de roles devuelve un rol válido con ID 1
        when(rolesService.getRoleById(1)).thenReturn(rolesEntity);

        // Llamamos al método de registro del controlador
        ResponseEntity<AuthResponse> response = authController.registerUser(registerRequest);

        // Verificamos que la respuesta tenga un código de estado HTTP 201 (CREATED)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta no sea nulo
        assertNotNull(response.getBody());

        // Comprobamos que el mensaje de respuesta sea el esperado
        assertEquals("Usuario registrado exitosamente", response.getBody().getMessage());
    }
    @Test
    @DisplayName("Registro incorrecto por datos nulos")
    void registerUserWithNullDetailsThenReturnsBadRequest() {
        // Creamos una solicitud de registro con valores nulos
        RegisterRequest invalidRequest = new RegisterRequest();
        invalidRequest.setEmail(null);
        invalidRequest.setPassword(null);

        // Intentamos registrar un usuario con datos inválidos
        ResponseEntity<AuthResponse> response = authController.registerUser(invalidRequest);

        // Verificamos que devuelve un 400 Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta no sea nulo
        assertNotNull(response.getBody());

        // Comprobamos que el mensaje de error sea el esperado
        assertEquals("Los datos del usuario no pueden estar vacíos", response.getBody().getError());
    }
    @Test
    @DisplayName("Logout correcto")
    void logoutUserThenReturnsOK() {
        // Llamamos al método de logout del controlador
        ResponseEntity<LogoutResponse> response = authController.logoutUser();

        // Verificamos que la respuesta tenga un código de estado HTTP 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta no sea nulo
        assertNotNull(response.getBody());

        // Comprobamos que el mensaje de éxito sea el esperado
        assertEquals("Sesión cerrada exitosamente", response.getBody().getMessage());
    }



}