package es.proyecto.app.Controller;

import es.proyecto.app.controller.AuthController;
import es.proyecto.app.controller.UsersController;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private UsersController usersController;

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

    private User validUser;


    @BeforeEach
    void setUp() {

        validUser = new User();
        validUser.setUserName("Test");
        validUser.setUserSurname("User");
        validUser.setUserBirth(LocalDateTime.of(1990, 1, 1, 0, 0));
        validUser.setEmail("test.user@example.com");
        validUser.setPassword("SecureP@ss1");
        validUser.setRoleId(2);  // existe en BD de ejemplo


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
        assertEquals("Successful login", response.getBody().getMessage());

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
        assertEquals("Wrong credentials try again", response.getBody().getError());
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
        assertEquals("Wrong credentials try again", response.getBody().getError());
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
        assertEquals("Email already in use", response.getBody().getError());
    }
    @Test
    @DisplayName("Registro correcto de usuario")
    void registerUserWithValidDetailsThenReturnsCreated() {
        // 1. Simulamos que el email NO está en uso
        when(usersService.existsByEmail(validUser.getEmail())).thenReturn(false);

        // 2. Como createUser es void, indicamos que no haga nada
        doNothing().when(usersService).createUser(validUser);

        // 3. Ejecutamos el endpoint
        ResponseEntity<UserCreatedResponse> response =
                usersController.createUser(validUser);

        // 4. Verificamos comportamiento
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Según tu implementación, el body puede ser null o vacío
        // Si tu método retorna un UserCreatedResponse, descomenta:
        // assertNotNull(response.getBody());
        // assertEquals("Usuario creado exitosamente", response.getBody().getMessage());

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
        assertEquals("Successful logout", response.getBody().getMessage());
    }



}