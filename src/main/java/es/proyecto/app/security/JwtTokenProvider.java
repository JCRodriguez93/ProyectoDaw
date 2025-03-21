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

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // Simulamos un almacenamiento en memoria para tokens invalidados
    private Set<String> invalidatedTokens = new HashSet<>();

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret).build()
                .parseClaimsJws(token).getBody().getSubject(); }

    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaims(token);

        if (claims == null || !claims.containsKey("roles")) {
            return Collections.emptyList(); // ✅ Devuelve lista vacía de forma segura
        }

        Object rolesObject = claims.get("roles");

        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .map(Object::toString) // Convierte cada objeto a String
                    .toList();
        }

        return Collections.emptyList(); // Si no es una lista, devuelve lista vacía
    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para invalidar el token
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
}

