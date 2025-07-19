package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class FileNotFoundException extends NoSuchElementException {
    public FileNotFoundException(String notFoundMessage) {
        super(notFoundMessage);
    }
    public FileNotFoundException() {
        super("File not found");
    }
}
