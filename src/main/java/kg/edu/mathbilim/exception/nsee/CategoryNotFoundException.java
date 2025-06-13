package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class CategoryNotFoundException extends NoSuchElementException {
    public CategoryNotFoundException() {
        super("Category not found");
    }
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
