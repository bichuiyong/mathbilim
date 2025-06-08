package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException() {super("Пользователь не был найден");}
}
