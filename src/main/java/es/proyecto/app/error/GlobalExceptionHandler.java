package es.proyecto.app.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la aplicación REST.
 * <p>
 * Gestiona excepciones específicas relacionadas con la validación y expiración
 * de tokens JWT, devolviendo respuestas HTTP adecuadas con mensajes descriptivos.
 * </p>
 * <ul>
 *   <li>{@link io.jsonwebtoken.ExpiredJwtException} → HTTP 401 Unauthorized con mensaje "El token ha expirado"</li>
 *   <li>{@link io.jsonwebtoken.JwtException} → HTTP 401 Unauthorized con mensaje "Token no válido"</li>
 * </ul>
 *
 * Este controlador permite centralizar el manejo de errores de autenticación
 * para mejorar la consistencia y claridad de las respuestas del API.
 *
 * @author Jorge
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción lanzada cuando el token JWT ha expirado.
     *
     * @return respuesta HTTP 401 con mensaje de token expirado.
     */
    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El token ha expirado");
    }

    /**
     * Maneja excepciones generales relacionadas con JWT inválidos.
     *
     * @return respuesta HTTP 401 con mensaje de token no válido.
     */
    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ResponseEntity<String> handleJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
    }
}
