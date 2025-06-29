package es.proyecto.app.security;

import org.springframework.security.core.userdetails.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticación JWT que intercepta las peticiones HTTP para validar el token JWT
 * y establecer el contexto de seguridad si el token es válido.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Construye un filtro de autenticación JWT con el proveedor de tokens especificado.
     *
     * @param jwtTokenProvider Proveedor para validar y extraer información del token JWT.
     */
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Procesa la solicitud HTTP interceptando la cabecera Authorization para validar el token JWT.
     * Si el token es válido, establece la autenticación en el contexto de seguridad.
     *
     * @param request     Petición HTTP entrante.
     * @param response    Respuesta HTTP.
     * @param filterChain Cadena de filtros.
     * @throws ServletException En caso de error de servlet.
     * @throws IOException      En caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token de la cabecera Authorization
        String token = getTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Si el token es válido, obtenemos el email del token
            String email = jwtTokenProvider.getEmailFromToken(token);

            // Aquí podría establecer el contexto de seguridad con el usuario autenticado
            List<SimpleGrantedAuthority> authorities = jwtTokenProvider.getRolesFromToken(token)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            new User(email, "", authorities), // Usuario con email y roles
                            null,
                            authorities
                    )
            );
        }

        // Continúa con el siguiente filtro
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT de la cabecera Authorization de la petición HTTP.
     *
     * @param request Petición HTTP.
     * @return El token JWT si está presente y correctamente formado, o {@code null} en caso contrario.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Eliminar "Bearer " y devolver el token
        }
        return null;
    }
}
