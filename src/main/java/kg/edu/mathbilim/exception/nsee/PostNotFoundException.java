package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class PostNotFoundException extends NoSuchElementException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException() {
        super("Post not found");
    }
}
