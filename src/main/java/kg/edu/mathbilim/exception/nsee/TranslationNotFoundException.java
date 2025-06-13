package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class TranslationNotFoundException extends NoSuchElementException {
    public TranslationNotFoundException(String message) {
        super(message);
    }
    public TranslationNotFoundException() {
        super("Translation not found");
    }
}
