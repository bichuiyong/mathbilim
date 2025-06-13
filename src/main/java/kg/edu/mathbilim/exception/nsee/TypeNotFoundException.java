package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class TypeNotFoundException extends NoSuchElementException {
    public TypeNotFoundException(String message) {
        super(message);
    }
    public TypeNotFoundException(){
        super("Type not found");
    }
}
