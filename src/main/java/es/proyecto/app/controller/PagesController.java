package es.proyecto.app.controller;

import es.swagger.codegen.api.DefaultApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PagesController implements DefaultApi {
    @Override
    public ResponseEntity<String> getHomePage() {
        // Generar un redireccionamiento manualmente
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/index.html");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);  // Utiliza el código de estado 302 (FOUND) para redireccionar

    }
}
