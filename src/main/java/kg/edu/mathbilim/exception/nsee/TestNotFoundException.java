package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class TestNotFoundException extends NoSuchElementException {
    public TestNotFoundException(String message) {
        super(message);
    }

    public TestNotFoundException() {
        super("Test not found");
    }
}
