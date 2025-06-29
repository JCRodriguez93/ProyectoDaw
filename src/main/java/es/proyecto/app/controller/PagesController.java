package es.proyecto.app.controller;

import es.swagger.codegen.api.DefaultApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Controlador encargado de manejar las peticiones relacionadas con las páginas estáticas o iniciales del sistema.
 *
 * <p>Esta clase implementa la interfaz {@link DefaultApi}, generada a partir de la especificación OpenAPI,
 * y proporciona la lógica necesaria para redirigir a la página principal del cliente (por ejemplo, "/index.html").</p>
 *
 * <p>El controlador puede utilizarse como punto de entrada principal al frontend de la aplicación desplegada,
 * especialmente útil en entornos donde el backend y el frontend se sirven desde la misma aplicación.</p>
 *
 * @author Jorge
 */
public class PagesController implements DefaultApi {

    /**
     * Maneja la petición para la página principal y realiza un redireccionamiento a "/index.html".
     * <p>
     * Construye una respuesta HTTP con cabecera "Location" apuntando a "/index.html"
     * y un código de estado 302 (FOUND) para indicar la redirección.
     *
     * @return un {@link ResponseEntity} con las cabeceras de redirección y el estado HTTP 302.
     */
    @Override
    public ResponseEntity<String> getHomePage() {
        // Generar un redireccionamiento manualmente
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/index.html");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);  // Utiliza el código de estado 302 (FOUND) para redireccionar

    }
}
