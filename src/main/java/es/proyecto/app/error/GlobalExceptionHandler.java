package es.proyecto.app.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El token ha expirado");
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ResponseEntity<String> handleJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
    }
}
