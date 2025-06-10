package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.BookDto;
import org.springframework.data.domain.Page;

public interface BookService {
    BookDto getById(Long id);

    Page<BookDto> getBookPage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);

    BookDto createBook(BookDto bookDto);
}
