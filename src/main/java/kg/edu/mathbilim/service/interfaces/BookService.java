package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseContentService;

public interface BookService extends BaseContentService<BookDto> {
    BookDto createBook(BookDto bookDto);
}
