package kg.edu.mathbilim.exception.iae;

public class FileValidationException extends IllegalArgumentException {
    public FileValidationException(String s) {
        super(s);
    }

    public FileValidationException() {
        super("File is not valid");
    }
}
