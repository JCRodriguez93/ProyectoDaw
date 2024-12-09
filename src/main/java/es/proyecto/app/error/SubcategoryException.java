package es.proyecto.app.error;

public class SubcategoryException extends RuntimeException {

    public static final SubcategoryException NO_SUBCATEGORY_FOUND_EXCEPTION = new SubcategoryException("No subcategory found with the specified ID");
    public static final SubcategoryException INVALID_SUBCATEGORY_ID_EXCEPTION = new SubcategoryException("Invalid subcategory ID provided");
    public static final SubcategoryException NULL_BODY_EXCEPTION = new SubcategoryException("Null body provided for the subcategory");
    public static final SubcategoryException MISSING_SUBCATEGORY_NAME_EXCEPTION = new SubcategoryException("Subcategory name is required");
    public static final SubcategoryException DUPLICATE_SUBCATEGORY_EXCEPTION = new SubcategoryException("A subcategory with the same name already exists");
    public static final SubcategoryException INVALID_CATEGORY_ID_EXCEPTION = new SubcategoryException("Invalid category ID associated with the subcategory");

    public SubcategoryException(String message) {
        super(message);
    }
}

