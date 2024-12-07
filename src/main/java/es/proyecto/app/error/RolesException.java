package es.proyecto.app.error;

public class RolesException extends RuntimeException {

    public static final RolesException NO_ROLE_FOUND_EXCEPTION = new RolesException("No role found with the specified ID");
    public static final RolesException NULL_BODY_EXCEPTION = new RolesException("Null body provided for the role");


    public RolesException(String message) {
        super(message);
    }
}
