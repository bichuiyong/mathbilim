package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseContentService;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BookService extends BaseContentService<BookDto> {
    BookDto createBook(MultipartFile attachment,MultipartFile file, BookDto bookDto);
    BookDto updateBook(long id,MultipartFile file,BookDto bookDto, MultipartFile mpImage);

    Page<BookDto> getAllBooks(String status, String query, int page, int size, String sortBy, String sortDirection, Long categoryId);
}
