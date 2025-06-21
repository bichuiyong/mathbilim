package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class UnauthorizedAccessException extends NoSuchElementException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
