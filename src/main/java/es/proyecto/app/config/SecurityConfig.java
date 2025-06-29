package es.proyecto.app.config;

import es.proyecto.app.security.JwtAuthenticationFilter;
import es.proyecto.app.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configura la seguridad de la aplicación usando Spring Security.
 * <p>
 * Se encarga de:
 * <ul>
 *     <li>Registrar el filtro de autenticación JWT</li>
 *     <li>Definir las reglas de autorización</li>
 *     <li>Deshabilitar CSRF y login por formulario</li>
 *     <li>Proveer un codificador de contraseñas (BCrypt)</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor que inyecta el filtro de autenticación JWT.
     *
     * @param jwtAuthenticationFilter el filtro personalizado que gestiona la autenticación mediante tokens JWT
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Proporciona un codificador de contraseñas que utiliza el algoritmo BCrypt.
     *
     * @return una instancia de {@link PasswordEncoder} usando {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt para el cifrado de contraseñas
    }

    /**
     * Configura la cadena de filtros de seguridad de la aplicación.
     * <p>
     * Desactiva CSRF y login por formulario.
     * Solo permite el acceso a /cart si el usuario está autenticado.
     * Agrega el filtro JWT antes del filtro de autenticación por defecto.
     *
     * @param http el objeto {@link HttpSecurity} para configurar la seguridad web
     * @return la cadena de filtros de seguridad {@link SecurityFilterChain}
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cart").authenticated() // Proteges la API
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form.disable()) // Desactiva el login predeterminado
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                // Agregado el filtro JWT antes del filtro de autenticación por defecto

        return http.build();
    }
}
