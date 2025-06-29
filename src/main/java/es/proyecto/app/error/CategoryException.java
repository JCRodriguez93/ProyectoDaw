package es.proyecto.app.error;

/**
 * Excepción personalizada para errores relacionados con la gestión de categorías.
 * <p>
 * Contiene constantes estáticas predefinidas para representar diferentes tipos
 * de errores que pueden ocurrir durante operaciones CRUD con categorías.
 * <p>
 * Esta clase extiende {@link RuntimeException} para permitir su uso en cualquier
 * punto de la aplicación sin necesidad de manejo explícito obligatorio.
 * </p>
 * Ejemplos de excepciones predefinidas incluyen:
 * <ul>
 *   <li>No se encontró la categoría con el ID especificado</li>
 *   <li>Cuerpo nulo proporcionado para la categoría</li>
 *   <li>Falta el nombre de la categoría</li>
 *   <li>La categoría ya existe</li>
 *   <li>ID de categoría inválido o ausente</li>
 *   <li>Error al actualizar la categoría</li>
 * </ul>
 *
 * @author Jorge
 */
public class CategoryException extends RuntimeException {

    public static final CategoryException NO_CATEGORY_FOUND_EXCEPTION = new CategoryException("No category found with the specified ID");
    public static final CategoryException NULL_BODY_EXCEPTION = new CategoryException("Null body provided for the category");
    public static final CategoryException MISSING_CATEGORY_NAME_EXCEPTION = new CategoryException("Category name is required");
    public static final CategoryException CATEGORY_ALREADY_EXISTS_EXCEPTION = new CategoryException("Category with the specified name already exists");
    public static final CategoryException MISSING_CATEGORY_ID_EXCEPTION = new CategoryException("Category ID is required");
    public static final CategoryException INVALID_CATEGORY_ID_EXCEPTION = new CategoryException("Invalid category ID provided");
    public static final CategoryException ERROR_UPDATING_CATEGORY_EXCEPTION = new CategoryException("Error updating category");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error ocurrido.
     */
    public CategoryException(String message) {
        super(message);
    }
}
