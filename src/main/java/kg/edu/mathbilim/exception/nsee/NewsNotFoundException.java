package kg.edu.mathbilim.exception.nsee;

import java.util.NoSuchElementException;

public class NewsNotFoundException extends NoSuchElementException {
    public NewsNotFoundException(String message) {
        super(message);
    }

  public NewsNotFoundException() {
      super("News not found");
  }
}
