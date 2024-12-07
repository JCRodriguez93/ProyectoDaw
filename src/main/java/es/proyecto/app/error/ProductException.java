package es.proyecto.app.error;

public class ProductException extends RuntimeException {

    public static final ProductException NO_PRODUCT_FOUND_EXCEPTION = new ProductException("No product found with the specified ID");
    public static final ProductException INVALID_PRODUCT_ID_EXCEPTION = new ProductException("Invalid product ID provided");
    public static final ProductException NULL_BODY_EXCEPTION = new ProductException("Null body provided for the product");
    public static final ProductException MISSING_PRODUCT_NAME_EXCEPTION = new ProductException("Product name is required");
    public static final ProductException INVALID_PRODUCT_PRICE_EXCEPTION = new ProductException("Invalid product price specified");
    public static final ProductException OUT_OF_STOCK_EXCEPTION = new ProductException("The product is out of stock");
    public static final ProductException DUPLICATE_PRODUCT_EXCEPTION = new ProductException("A product with the same name already exists");

    public ProductException(String message) {
        super(message);
    }
}

