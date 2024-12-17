package es.proyecto.app.error;

public class UsersException extends RuntimeException {

    public static final UsersException NO_USER_FOUND_EXCEPTION = new UsersException("No user found with the specified ID");
    public static final UsersException INVALID_USER_ID_EXCEPTION = new UsersException("Invalid user ID provided");
    public static final UsersException NULL_BODY_EXCEPTION = new UsersException("Null body provided for the user");
    public static final UsersException MISSING_USER_NAME_EXCEPTION = new UsersException("User name is required for updating");
    public static final UsersException DUPLICATE_USER_EXCEPTION = new UsersException("A user with the same username or email already exists");
    public static final UsersException INVALID_EMAIL_FORMAT_EXCEPTION = new UsersException("The provided email format is invalid");
    public static final UsersException USER_ROLE_NOT_FOUND_EXCEPTION = new UsersException("User role not found for the specified user");
    public static final UsersException MISSING_ID_EXCEPTION = new UsersException("User role not found for the specified user");

    public UsersException(String message) {
        super(message);
    }
}

