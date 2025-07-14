package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService extends BaseContentService<BookDto> {
    Page<BookDto> getAllBooks(
            String status,
            String query,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    void approve(Long id, String email);

    void reject(Long id, String email);

    Page<BookDto> getContentByCreatorIdBook(Long creatorId, Pageable pageable);

    Page<BookDto> getHisotryBook(Long creatorId, Pageable pageable);

    Page<BookDto> getBooksForModeration(Pageable pageable);

    BookDto createBook(BookDto bookDto);
}
