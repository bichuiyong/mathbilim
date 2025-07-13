package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService extends BaseContentService<BookDto> {
    Page<BookDto> getContentByCreatorIdBook(Long creatorId, Pageable pageable);

    Page<BookDto> getBooksForModeration(Pageable pageable);

    BookDto createBook(BookDto bookDto);
}
