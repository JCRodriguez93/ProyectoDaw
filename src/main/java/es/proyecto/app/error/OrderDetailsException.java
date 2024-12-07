package es.proyecto.app.error;
public class OrderDetailsException extends RuntimeException {

    public static final OrderDetailsException NO_ORDER_DETAILS_FOUND_EXCEPTION = new OrderDetailsException("No order details found with the specified ID");
    public static final OrderDetailsException INVALID_ORDER_DETAILS_ID_EXCEPTION = new OrderDetailsException("Invalid order details ID provided");
    public static final OrderDetailsException NULL_BODY_EXCEPTION = new OrderDetailsException("Null body provided for order details");
    public static final OrderDetailsException MISSING_PRODUCT_ID_EXCEPTION = new OrderDetailsException("Product ID is required in order details");
    public static final OrderDetailsException MISSING_ORDER_ID_EXCEPTION = new OrderDetailsException("Order ID is required in order details");
    public static final OrderDetailsException INVALID_QUANTITY_EXCEPTION = new OrderDetailsException("Invalid quantity specified for the product");

    public OrderDetailsException(String message) {
        super(message);
    }
}

