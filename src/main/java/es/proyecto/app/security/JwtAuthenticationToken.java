package es.proyecto.app.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;

    public JwtAuthenticationToken(String email) {
        super(null);
        this.email = email;
        setAuthenticated(true); // El token es válido, por lo que se marca como autenticado
    }

    @Override
    public Object getCredentials() {
        return null; // No necesitamos las credenciales ya que el token ya ha sido validado
    }

    @Override
    public Object getPrincipal() {
        return email; // El "principal" es el email del usuario que hemos extraído del token
    }
}
