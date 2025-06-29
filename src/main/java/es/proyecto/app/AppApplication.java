package es.proyecto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


/**
 * Clase principal que arranca la aplicación Spring Boot.
 * <p>
 * Contiene el método main que inicia el contexto de Spring.
 * </p>
 */
@SpringBootApplication()
public class AppApplication {

	/**
	 * Método principal que lanza la aplicación.
	 *
	 * @param args argumentos de línea de comandos (opcional).
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
