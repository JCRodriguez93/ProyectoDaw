package es.proyecto.app.error;

/**
 * Excepción personalizada para errores relacionados con los detalles de un pedido.
 * <p>
 * Contiene varias instancias predefinidas para representar diferentes situaciones
 * comunes de error en el manejo de detalles de pedidos, como IDs inválidos,
 * ausencia de datos requeridos o cantidades inválidas.
 * </p>
 *
 * Esta excepción extiende {@link RuntimeException} y se utiliza para manejar
 * errores específicos en la capa de negocio o servicio relacionados con
 * los detalles de los pedidos.
 *
 * @author Jorge
 */
public class OrderDetailsException extends RuntimeException {

    public static final OrderDetailsException NO_ORDER_DETAILS_FOUND_EXCEPTION = new OrderDetailsException("No order details found with the specified ID");
    public static final OrderDetailsException INVALID_ORDER_DETAILS_ID_EXCEPTION = new OrderDetailsException("Invalid order details ID provided");
    public static final OrderDetailsException MISSING_PRODUCT_ID_EXCEPTION = new OrderDetailsException("Product ID is required in order details");
    public static final OrderDetailsException MISSING_ORDER_ID_EXCEPTION = new OrderDetailsException("Order ID is required in order details");
    public static final OrderDetailsException INVALID_QUANTITY_EXCEPTION = new OrderDetailsException("Invalid quantity specified for the product");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error ocurrido.
     */
    public OrderDetailsException(String message) {
        super(message);
    }
}

