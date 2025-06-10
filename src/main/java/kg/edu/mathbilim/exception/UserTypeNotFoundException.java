package kg.edu.mathbilim.exception;

public class UserTypeNotFoundException extends RuntimeException {
    public UserTypeNotFoundException(String message) {
        super(message);
    }

    public UserTypeNotFoundException() {
        super("User type not found");
    }
}
