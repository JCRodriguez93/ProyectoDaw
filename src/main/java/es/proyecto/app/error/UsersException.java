package es.proyecto.app.error;


/**
 * Excepción personalizada para errores relacionados con usuarios.
 * <p>
 * Esta clase define constantes para situaciones comunes de error al manejar usuarios,
 * como usuarios no encontrados, IDs inválidos, cuerpos nulos, validaciones de contraseña,
 * emails duplicados y errores al actualizar usuarios.
 * </p>
 *
 * Extiende {@link RuntimeException} y se usa para representar errores específicos
 * en la lógica de negocio o capa de servicio relacionados con usuarios.
 *
 * @author Jorge
 */
public class UsersException extends RuntimeException {

    public static final UsersException NO_USER_FOUND_EXCEPTION = new UsersException("No user found with the specified ID");
    public static final UsersException INVALID_USER_ID_EXCEPTION = new UsersException("Invalid user ID provided");
    public static final UsersException NULL_BODY_EXCEPTION = new UsersException("Null body provided for the user");
    public static final UsersException MISSING_USER_NAME_EXCEPTION = new UsersException("User name is required for updating");
    public static final UsersException INVALID_PASSWORD_EXCEPTION = new UsersException("Password must have at least 8 characters, upper and lower case, number and special character");
    public static final UsersException DUPLICATE_EMAIL_EXCEPTION = new UsersException("Duplicate email is not allowed");
    public static final UsersException ERROR_UPDATING_USER_EXCEPTION = new UsersException("Error updating user");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error ocurrido.
     */
    public UsersException(String message) {
        super(message);
    }
}
