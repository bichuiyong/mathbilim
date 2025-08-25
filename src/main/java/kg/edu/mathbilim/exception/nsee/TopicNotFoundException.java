package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class TopicNotFoundException extends NoSuchElementException {
    public TopicNotFoundException(String message) {
        super(message);
    }

    public TopicNotFoundException() {
        super("Topic not found");
    }
}
