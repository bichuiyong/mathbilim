package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class BookNotFoundException extends NoSuchElementException {
    public BookNotFoundException(String message) {
        super(message);
    }
    public BookNotFoundException() {
        super("Book not found");
    }
}
