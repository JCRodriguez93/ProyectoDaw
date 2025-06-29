package es.proyecto.app.controller;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.UsersMapper;
import es.proyecto.app.security.JwtTokenProvider;
import es.proyecto.app.service.RolesService;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.AuthApi;
import es.swagger.codegen.models.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Controlador REST responsable de gestionar la autenticación y el registro de usuarios.
 *
 * <p>Implementa los endpoints definidos en la interfaz {@link AuthApi}, generada por Swagger Codegen.
 * Este controlador incluye las operaciones para:
 * <ul>
 *     <li>Iniciar sesión y generar un token JWT.</li>
 *     <li>Cerrar sesión invalidando el token JWT.</li>
 *     <li>Registrar nuevos usuarios en el sistema.</li>
 * </ul>
 *
 * <p>Utiliza servicios como {@link UsersService}, {@link RolesService} y {@link JwtTokenProvider}
 * para interactuar con la lógica de negocio y la seguridad.</p>
 *
 * <p>Todos los endpoints devuelven respuestas normalizadas con objetos como
 * {@link AuthResponse} o {@link LogoutResponse}, incluyendo mensajes y estados HTTP adecuados.</p>
 *
 * @author Jorge
 */
@RestController
public class AuthController implements AuthApi {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Inicia sesión de un usuario existente verificando sus credenciales (email y contraseña).
     *
     * <p>Este método recibe un objeto {@link LoginRequest} con el email y la contraseña
     * del usuario. Comprueba si el usuario existe y si la contraseña coincide con la almacenada.
     * Si las credenciales son válidas, genera un token JWT y devuelve una respuesta exitosa.
     * Si no, devuelve un error de autenticación.</p>
     *
     * @param body Objeto {@link LoginRequest} que contiene el email y la contraseña del usuario.
     * @return {@link ResponseEntity} con un objeto {@link AuthResponse}.
     *         Si la autenticación falla, retorna HTTP 401 con mensaje de error.
     *         Si es exitosa, retorna HTTP 200 con el token JWT generado.
     */
    @Override
    public ResponseEntity<AuthResponse> loginUser(LoginRequest body) {
        logger.info("Attempting to log in with email: {}", body.getEmail());
        UsersEntity user = usersService.findByEmail(body.getEmail());
        if (user == null || !passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            logger.error("Invalid login attempt for email: {}", body.getEmail());
            AuthResponse response = new AuthResponse();
            response.setError("Wrong credentials try again");
            response.setToken("");
            response.setMessage("Wrong credentials try again");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String token = jwtTokenProvider.generateToken(user.getEmail());
        logger.info("User logged in successfully: {}", body.getEmail());
        AuthResponse response = new AuthResponse();
        response.setError("");
        response.setToken(token);
        response.setMessage("Successful login");
        return ResponseEntity.ok(response);
    }

    /**
     * Cierra la sesión del usuario invalidando el token JWT recibido en la cabecera Authorization.
     *
     * <p>Este método recupera la cabecera "Authorization" de la petición HTTP actual,
     * extrae el token JWT y lo invalida utilizando {@code jwtTokenProvider.invalidateToken()}.
     * Si no se encuentra un token válido, se registra una advertencia.</p>
     *
     * @return {@link ResponseEntity} con un objeto {@link LogoutResponse}.
     *         Siempre retorna HTTP 200 con un mensaje de éxito, incluso si no se encontró token.
     */
    @Override
    public ResponseEntity<LogoutResponse> logoutUser() {
        // Obtener la petición actual sin modificar la firma del método
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Invalida el token usando tu método definido
            jwtTokenProvider.invalidateToken(token);
            logger.info("Token invalidado: {}", token);
        } else {
            logger.warn("No se encontró token en la cabecera para logout.");
        }

        LogoutResponse response = new LogoutResponse();
        response.setError("");
        response.setMessage("Successful logout");
        return ResponseEntity.ok(response);
    }


    /**
     * Registra un nuevo usuario en el sistema.
     *
     * <p>Este método valida los datos proporcionados en la solicitud {@link RegisterRequest},
     * comprueba si el email ya está en uso y si el rol de usuario especificado existe.
     * Si todo es válido, crea un nuevo usuario utilizando {@code usersService}.</p>
     *
     * @param body Objeto {@link RegisterRequest} que contiene la información del usuario a registrar.
     * @return {@link ResponseEntity} con un objeto {@link AuthResponse}:
     *         <ul>
     *             <li>HTTP 201 (CREATED) si el usuario fue creado correctamente.</li>
     *             <li>HTTP 400 (BAD_REQUEST) si los datos del usuario están incompletos o nulos.</li>
     *             <li>HTTP 409 (CONFLICT) si el email ya está registrado.</li>
     *             <li>HTTP 500 (INTERNAL_SERVER_ERROR) si el rol no existe.</li>
     *         </ul>
     */
    @Override
    public ResponseEntity<AuthResponse> registerUser(RegisterRequest body) {
        logger.info("Attempting to register user with email: {}", body.getEmail());

        if (body == null || body.getEmail() == null || body.getPassword() == null) {
            logger.error("Registration failed due to null or empty user data");
            AuthResponse response = new AuthResponse();
            response.setError("User data cannot be null");
            response.setToken("");
            response.setMessage("User data cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Verificamos si el email ya está registrado
        if (usersService.existsByEmail(body.getEmail())) {
            logger.error("Email already in use: {}", body.getEmail());
            AuthResponse response = new AuthResponse();
            response.setError("Email already in use");
            response.setToken("");
            response.setMessage("Email already in use");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Obtener el rol "USER" utilizando RolesService
        RolesEntity userRole = rolesService.getRoleById(body.getRoleId());
        if (userRole == null) {
            logger.error("Role with ID 1 not found");
            AuthResponse response = new AuthResponse();
            response.setError("User role not found");
            response.setToken("");
            response.setMessage("User role not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        // Crear User
        User newUser = new User()
                .userName(body.getUserName())
                .userSurname(body.getUserSurname())
                .userBirth(body.getUserBirth())
                .email(body.getEmail())
                .password(body.getPassword())
                .roleId(userRole.getIdRole());

        usersService.createUser(newUser);
        logger.info("User registered successfully with email: {}", body.getEmail());

        AuthResponse response = new AuthResponse();
        response.setError("");
        response.setToken("");
        response.setMessage("User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
