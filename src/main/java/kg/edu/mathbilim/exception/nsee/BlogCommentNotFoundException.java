package kg.edu.mathbilim.exception.nsee;

public class BlogCommentNotFoundException extends RuntimeException {
    public BlogCommentNotFoundException(String message) {
        super(message);
    }
    public BlogCommentNotFoundException() {
        super("Blog comment not found");
    }

}
