package es.proyecto.app.controller;

import es.swagger.codegen.api.DefaultApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
