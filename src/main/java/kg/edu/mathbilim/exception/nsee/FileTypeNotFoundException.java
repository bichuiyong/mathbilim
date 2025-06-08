package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class FileTypeNotFoundException extends NoSuchElementException {
    public FileTypeNotFoundException(String s) {
        super(s);
    }
    public FileTypeNotFoundException() {
        super("File type not found");
    }
}
