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
            response.setError("Credenciales incorrectas");
            response.setToken("");
            response.setMessage("Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String token = jwtTokenProvider.generateToken(user.getEmail());
        logger.info("User logged in successfully: {}", body.getEmail());
        AuthResponse response = new AuthResponse();
        response.setError("");
        response.setToken(token);
        response.setMessage("Inicio de sesión exitoso");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LogoutResponse> logoutUser() {
        logger.info("Logging out user");
        LogoutResponse response = new LogoutResponse();
        response.setError("");
        response.setMessage("Sesión cerrada exitosamente");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<AuthResponse> registerUser(RegisterRequest body) {
        logger.info("Attempting to register user with email: {}", body.getEmail());
        if (usersService.existsByEmail(body.getEmail())) {
            logger.error("Email already in use: {}", body.getEmail());
            AuthResponse response = new AuthResponse();
            response.setError("Email ya está en uso");
            response.setToken("");
            response.setMessage("Email ya está en uso");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Obtener el rol "USER" utilizando RolesService
        RolesEntity userRole = rolesService.getRoleById(1);

        // Crear User
        User newUser = new User()
                .userName(body.getUserName())
                .userSurname(body.getUserSurname())
                .userBirth(body.getUserBirth())
                .email(body.getEmail())
                .password(passwordEncoder.encode(body.getPassword()))
                .roleId(userRole.getIdRole());

        usersService.createUser(newUser);
        logger.info("User registered successfully with email: {}", body.getEmail());

        AuthResponse response = new AuthResponse();
        response.setError("");
        response.setToken("");
        response.setMessage("Usuario registrado exitosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
