package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class RoleNotFoundException extends NoSuchElementException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException() {
        super("Role not found");
    }
}
