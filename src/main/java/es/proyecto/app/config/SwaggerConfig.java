package es.proyecto.app.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    /**
     * El siguiente bean contiene el acceso a la API.
     * Visible desde http://localhost:8080/swagger-ui/index.html
     * @return API del micro-servicio.
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
