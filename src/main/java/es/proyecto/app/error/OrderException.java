package es.proyecto.app.error;
public class OrderException extends RuntimeException {

    public static final OrderException NO_ORDER_FOUND_EXCEPTION = new OrderException("No order found with the specified ID");
    public static final OrderException INVALID_ORDER_ID_EXCEPTION = new OrderException("Invalid order ID provided");
    public static final OrderException NULL_BODY_EXCEPTION = new OrderException("Null body provided for the order");
    public static final OrderException MISSING_ORDER_ID_EXCEPTION = new OrderException("Order ID is required for the order");
    public static final OrderException INVALID_ORDER_STATUS_EXCEPTION = new OrderException("Invalid order status specified");
    public static final OrderException ORDER_ALREADY_PROCESSED_EXCEPTION = new OrderException("The order has already been processed and cannot be modified");

    public OrderException(String message) {
        super(message);
    }
}

