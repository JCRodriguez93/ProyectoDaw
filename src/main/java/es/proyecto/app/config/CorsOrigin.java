package es.proyecto.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de CORS para la aplicación.
 * <p>
 * Esta clase permite configurar las reglas de Cross-Origin Resource Sharing (CORS)
 * para aceptar peticiones desde cualquier origen, con métodos y cabeceras permitidos
 * y soporte para cookies o tokens.
 */
@Configuration
public class CorsOrigin {

    /**
     * Bean que configura las reglas de CORS para todas las rutas de la aplicación.
     *
     * @return una implementación de {@link WebMvcConfigurer} con la configuración CORS aplicada
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            /**
             * Define las reglas de CORS para todos los endpoints del backend.
             * Permite cualquier origen, todos los métodos comunes y cabeceras, y
             * habilita el uso de credenciales (como cookies o tokens en cabeceras).
             *
             * @param registry el registro donde se definen los mapeos CORS
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
