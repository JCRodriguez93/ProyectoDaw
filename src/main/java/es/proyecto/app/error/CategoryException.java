package es.proyecto.app.error;

public class CategoryException extends RuntimeException {

    public static final CategoryException NO_CATEGORY_FOUND_EXCEPTION = new CategoryException("No category found with the specified ID");
    public static final CategoryException NULL_BODY_EXCEPTION = new CategoryException("Null body provided for the category");
    public static final CategoryException MISSING_CATEGORY_NAME_EXCEPTION = new CategoryException("Category name is required");
    public static final CategoryException CATEGORY_ALREADY_EXISTS_EXCEPTION = new CategoryException("Category with the specified name already exists");
    public static final CategoryException MISSING_CATEGORY_ID_EXCEPTION = new CategoryException("Category ID is required");
    public static final CategoryException INVALID_CATEGORY_ID_EXCEPTION = new CategoryException("Invalid category ID provided");

    public CategoryException(String message) {
        super(message);
    }
}
