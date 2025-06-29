package es.proyecto.app.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;


/**
 * Proveedor de tokens JWT para generar, validar, extraer información y manejar tokens JWT.
 * Esta clase también soporta la invalidación manual de tokens.
 */
@Component
public class JwtTokenProvider {

    /**
     * Clave secreta para firmar los tokens JWT.
     */
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    /**
     * Tiempo de expiración del token en milisegundos, configurado vía properties.
     */
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Almacenamiento en memoria para tokens inválidos (revocados).
     */
    private Set<String> invalidatedTokens = new HashSet<>();

    /**
     * Genera un token JWT para el email dado.
     *
     * @param email Correo electrónico que se incluirá como sujeto del token.
     * @return Token JWT firmado con fecha de emisión y expiración.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Valida si un token JWT es válido y no ha sido invalidado.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si el token es válido y no ha sido invalidado; {@code false} en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            // Verificar si el token ha sido invalidado explícitamente
            if (invalidatedTokens.contains(token)) {
                return false;
            }

            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extrae el email (subject) del token JWT.
     *
     * @param token Token JWT.
     * @return El email contenido en el token.
     */
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret).build()
                .parseClaimsJws(token).getBody().getSubject(); }

    /**
     * Extrae la lista de roles almacenados en las claims del token JWT.
     *
     * @param token Token JWT.
     * @return Lista de roles como cadenas de texto; lista vacía si no existen roles.
     */
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaims(token);

        if (claims == null || !claims.containsKey("roles")) {
            return Collections.emptyList(); //  Devuelve lista vacía de forma segura
        }

        Object rolesObject = claims.get("roles");

        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .map(Object::toString) // Convierte cada objeto a String
                    .toList();
        }

        return Collections.emptyList(); // Si no es una lista, devuelve lista vacía
    }

    /**
     * Obtiene las claims (reclamaciones) del token JWT.
     *
     * @param token Token JWT.
     * @return Claims del token.
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Marca un token JWT como invalidado (revocado).
     *
     * @param token Token JWT a invalidar.
     */
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
}

