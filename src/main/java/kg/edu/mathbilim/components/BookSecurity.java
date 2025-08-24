package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component("bookSecurity")
public class BookSecurity extends ContentSecurity<Book, BookRepository> {
    public BookSecurity(BookRepository repository) {
        super(repository);
    }
}
