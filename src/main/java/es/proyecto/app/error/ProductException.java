package es.proyecto.app.error;

public class ProductException extends RuntimeException {

    public static final ProductException NO_PRODUCT_FOUND_EXCEPTION = new ProductException("No product found with the specified ID");
    public static final ProductException INVALID_PRODUCT_ID_EXCEPTION = new ProductException("Invalid product ID provided");
    public static final ProductException NULL_BODY_EXCEPTION = new ProductException("Null body provided for the product");
    public static final ProductException MISSING_PRODUCT_NAME_EXCEPTION = new ProductException("Product name is required");
    public static final ProductException MISSING_PRODUCT_ID_EXCEPTION = new ProductException("Product ID is required");
    public static final ProductException ERROR_UPDATING_PRODUCT_EXCEPTION = new ProductException("Error updating product");

    public ProductException(String message) {
        super(message);
    }
}
