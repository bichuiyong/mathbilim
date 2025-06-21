package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class BlogNotFoundException extends NoSuchElementException {
    public BlogNotFoundException(String message) {
        super(message);
    }

    public BlogNotFoundException() {
        super("Blog not found");
    }
}
