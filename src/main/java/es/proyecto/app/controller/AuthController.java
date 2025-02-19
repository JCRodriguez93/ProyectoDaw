package es.proyecto.app.controller;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.UsersMapper;
import es.proyecto.app.security.JwtTokenProvider;
import es.proyecto.app.service.RolesService;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.AuthApi;
import es.swagger.codegen.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Override
    public ResponseEntity<LogoutResponse> logoutUser() {
        logger.info("Logging out user");

        LogoutResponse response = new LogoutResponse();
        response.setError("");
        response.setMessage("Successful logout");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

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
