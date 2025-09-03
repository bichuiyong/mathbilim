package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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

    BookDto getBookById(Long id, String email);

    Page<BookDto> getContentByCreatorIdBook(Long creatorId, Pageable pageable, String quary);

    Page<BookDto> getHistoryBook(Long creatorId, Pageable pageable, String query, String status);

    Page<BookDto> getAllBook(Pageable pageable, String query, String status);

    Page<BookDto> getBooksForModeration(Pageable pageable, String query);


    BookDto createBook(MultipartFile attachment,MultipartFile file, BookDto bookDto);
    BookDto updateBook(long id,MultipartFile file,BookDto bookDto, MultipartFile mpImage);

    Page<BookDto> getAllBooks(String status, String query, int page, int size, String sortBy, String sortDirection, Long categoryId);

    Long countBookForModeration();
}
