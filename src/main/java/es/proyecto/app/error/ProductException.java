package es.proyecto.app.error;

/**
 * Excepción personalizada para errores relacionados con los productos.
 * <p>
 * Esta clase define varias instancias predefinidas que representan
 * diferentes situaciones comunes de error en el manejo de productos,
 * tales como productos no encontrados, IDs inválidos, datos nulos o
 * errores durante la actualización de un producto.
 * </p>
 *
 * Extiende {@link RuntimeException} y se utiliza para manejar errores específicos
 * en la lógica de negocio o capa de servicio relacionados con productos.
 *
 * @author Jorge
 */
public class ProductException extends RuntimeException {

    public static final ProductException NO_PRODUCT_FOUND_EXCEPTION = new ProductException("No product found with the specified ID");
    public static final ProductException INVALID_PRODUCT_ID_EXCEPTION = new ProductException("Invalid product ID provided");
    public static final ProductException NULL_BODY_EXCEPTION = new ProductException("Null body provided for the product");
    public static final ProductException MISSING_PRODUCT_NAME_EXCEPTION = new ProductException("Product name is required");
    public static final ProductException MISSING_PRODUCT_ID_EXCEPTION = new ProductException("Product ID is required");
    public static final ProductException ERROR_UPDATING_PRODUCT_EXCEPTION = new ProductException("Error updating product");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error ocurrido.
     */
    public ProductException(String message) {
        super(message);
    }
}
