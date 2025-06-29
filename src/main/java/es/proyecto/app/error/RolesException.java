package es.proyecto.app.error;

/**
 * Excepción personalizada para errores relacionados con los roles.
 * <p>
 * Esta clase define instancias predefinidas que representan situaciones comunes
 * de error al gestionar roles, como roles no encontrados, cuerpos nulos,
 * IDs inválidos y errores al actualizar roles.
 * </p>
 *
 * Extiende {@link RuntimeException} y se utiliza para representar errores específicos
 * en la lógica de negocio o capa de servicio relacionados con la gestión de roles.
 *
 * @author Jorge
 */
public class RolesException extends RuntimeException {

    public static final RolesException NO_ROLE_FOUND_EXCEPTION = new RolesException("No role found with the specified ID");
    public static final RolesException NULL_BODY_EXCEPTION = new RolesException("Null body provided for the role");
    public static final RolesException INVALID_ID_EXCEPTION = new RolesException("Invalid ID provided for the role");
    public static final RolesException ERROR_UPDATING_ROLE_EXCEPTION = new RolesException("Error updating role");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message Mensaje descriptivo del error ocurrido.
     */
    public RolesException(String message) {
        super(message);
    }
}
