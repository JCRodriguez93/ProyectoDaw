package es.proyecto.app.error;

public class SubcategoryException extends RuntimeException {

    public static final SubcategoryException NO_SUBCATEGORY_FOUND_EXCEPTION = new SubcategoryException("No subcategory found with the specified ID");
    public static final SubcategoryException INVALID_SUBCATEGORY_ID_EXCEPTION = new SubcategoryException("Invalid subcategory ID provided");
    public static final SubcategoryException NULL_BODY_EXCEPTION = new SubcategoryException("Null body provided for the subcategory");
    public static final SubcategoryException MISSING_SUBCATEGORY_NAME_EXCEPTION = new SubcategoryException("Subcategory name is required");
    public static final SubcategoryException MISSING_SUBCATEGORY_ID_EXCEPTION = new SubcategoryException("Subcategory ID is required");
    public static final SubcategoryException DUPLICATE_SUBCATEGORY_NAME_EXCEPTION = new SubcategoryException("Duplicate subcategory name is not allowed");

    public SubcategoryException(String message) {
        super(message);
    }
}
