package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class EventNotFoundException extends NoSuchElementException {
    public EventNotFoundException() {
        super("Event not found");
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
