package kg.edu.mathbilim.exception.nsee;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
  public CommentNotFoundException() {
    super("Comment not found");
  }
}
