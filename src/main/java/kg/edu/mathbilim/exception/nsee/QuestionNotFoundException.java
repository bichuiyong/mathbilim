package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class QuestionNotFoundException extends NoSuchElementException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
    public QuestionNotFoundException() {
        super("Question not found");
    }
}
