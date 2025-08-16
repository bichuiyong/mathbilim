package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class AttemptNotFoundException extends NoSuchElementException {
    public AttemptNotFoundException() {
        super("Attempt not found");
    }
    public AttemptNotFoundException(String message) {
        super(message);
    }
}
