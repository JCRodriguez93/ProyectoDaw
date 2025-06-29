package es.proyecto.app.error;

/**
 * Excepción personalizada para errores relacionados con las subcategorías.
 * <p>
 * Esta clase define varias instancias predefinidas que representan
 * diferentes situaciones comunes de error en el manejo de subcategorías,
 * tales como subcategorías no encontradas, IDs inválidos, datos nulos,
 * nombres duplicados y errores durante la actualización.
 * </p>
 *
 * Extiende {@link RuntimeException} y se utiliza para manejar errores específicos
 * en la lógica de negocio o capa de servicio relacionados con subcategorías.
 *
 * @author Jorge
 */
public class SubcategoryException extends RuntimeException {

    public static final SubcategoryException NO_SUBCATEGORY_FOUND_EXCEPTION = new SubcategoryException("No subcategory found with the specified ID");
    public static final SubcategoryException INVALID_SUBCATEGORY_ID_EXCEPTION = new SubcategoryException("Invalid subcategory ID provided");
    public static final SubcategoryException NULL_BODY_EXCEPTION = new SubcategoryException("Null body provided for the subcategory");
    public static final SubcategoryException MISSING_SUBCATEGORY_NAME_EXCEPTION = new SubcategoryException("Subcategory name is required");
    public static final SubcategoryException MISSING_SUBCATEGORY_ID_EXCEPTION = new SubcategoryException("Subcategory ID is required");
    public static final SubcategoryException DUPLICATE_SUBCATEGORY_NAME_EXCEPTION = new SubcategoryException("Duplicate subcategory name is not allowed");
    public static final SubcategoryException ERROR_UPDATING_SUBCATEGORY_EXCEPTION = new SubcategoryException("Error updating subcategory");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error ocurrido.
     */
    public SubcategoryException(String message) {
        super(message);
    }
}
