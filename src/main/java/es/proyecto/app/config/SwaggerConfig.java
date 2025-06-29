package es.proyecto.app.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para la documentación automática de la API REST.
 * <p>
 * Una vez iniciado el servidor, la documentación estará disponible en:
 * <a href="http://localhost:8080/swagger-ui/index.html">Swagger UI</a>
 */
@Configuration
public class SwaggerConfig {


    /**
     * Define la configuración principal de la documentación OpenAPI.
     *
     * @return un objeto {@link OpenAPI} que contiene la información básica del servicio REST.
     */
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("Spring Boot Proyecto DAW")
                .description("Spring Boot Rest API")
                .contact(new Contact().name("Jorge Campos Rodríguez"))
                .version("1.0.0"));
    }


}
