package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class EventTypeNotFoundException extends NoSuchElementException {
    public EventTypeNotFoundException() {super("Event type not found");}
}
