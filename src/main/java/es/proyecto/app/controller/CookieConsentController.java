package es.proyecto.app.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieConsentController {

    @PostMapping("/consent")
    public ResponseEntity<String> setCookieConsent(HttpServletResponse response) {
        Cookie consentCookie = new Cookie("cookies-accepted", "true");
        consentCookie.setHttpOnly(false); // Disponible para el frontend
        consentCookie.setSecure(true); // Solo enviar por HTTPS
        consentCookie.setMaxAge(365 * 24 * 60 * 60); // Caduca en un año
        consentCookie.setPath("/");
        response.addCookie(consentCookie);
        return ResponseEntity.ok("Consentimiento aceptado");
    }

    @GetMapping("/check-consent")
    public ResponseEntity<String> checkCookieConsent(@CookieValue(value = "cookies-accepted", defaultValue = "false") String consent) {
        if ("true".equals(consent)) {
            return ResponseEntity.ok("Consentimiento dado");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Consentimiento no encontrado");
    }
}
