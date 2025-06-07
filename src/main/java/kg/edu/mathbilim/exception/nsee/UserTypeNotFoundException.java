package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class UserTypeNotFoundException extends NoSuchElementException {
    public UserTypeNotFoundException(String message) {
        super(message);
    }

    public UserTypeNotFoundException() {
        super("User type not found");
    }
}
