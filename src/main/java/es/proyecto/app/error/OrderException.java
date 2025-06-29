package es.proyecto.app.error;


/**
 * Excepción personalizada para errores relacionados con las órdenes.
 * <p>
 * Esta clase contiene varias instancias predefinidas que representan
 * diferentes condiciones de error comunes en el manejo de órdenes,
 * tales como órdenes no encontradas, IDs inválidos, cuerpos nulos,
 * estados inválidos o conflictos de actualización según el estado actual.
 * </p>
 *
 * Hereda de {@link RuntimeException} y se usa para representar problemas
 * específicos en la lógica de negocio o capa de servicio relacionados con
 * la gestión de órdenes.
 *
 * @author Jorge
 */
public class OrderException extends RuntimeException {

    public static final OrderException NO_ORDER_FOUND_EXCEPTION = new OrderException("No order found with the specified ID");
    public static final OrderException INVALID_ORDER_ID_EXCEPTION = new OrderException("Invalid order ID provided");
    public static final OrderException NULL_BODY_EXCEPTION = new OrderException("Null body provided for the order");
    public static final OrderException MISSING_ORDER_ID_EXCEPTION = new OrderException("Order ID is required for the order");
    public static final OrderException DUPLICATED_ORDER_ID_EXCEPTION = new OrderException("Order with the specified ID already exists");
    public static final OrderException INVALID_ORDER_STATUS_EXCEPTION = new OrderException("Invalid order status provided");
    public static final OrderException INVALID_UPDATE_DUE_CURRENT_STATUS_EXCEPTION = new OrderException("Order with the specified ID cannot be updated due to its current status");

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error ocurrido.
     */
    public OrderException(String message) {
        super(message);
    }
}

