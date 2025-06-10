package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class OrganizationNotFound extends NoSuchElementException {
    public OrganizationNotFound() {
        super("Organization not found");
    }

    public OrganizationNotFound(String message) {
        super(message);
    }
}
