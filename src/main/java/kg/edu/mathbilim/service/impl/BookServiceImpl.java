package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.dto.CategoryDto;
import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BookNotFoundException;
import kg.edu.mathbilim.mapper.BookMapper;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.repository.BookRepository;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.CategoryService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.util.FileUtil;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryService categoryService;
    private static final String SUB_DIR="books";
    private FileUtil fileUtil;

    private Book getEntityById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.toDto(getEntityById(id));
    }

    @Override
    public Page<BookDto> getBookPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> bookRepository.findAll(pageable));
        }
        return getPage(() -> bookRepository.findByQuery(query, pageable));
    }

    @Override
    public void delete(Long id, UserDto user) {
        Book book = bookRepository.findByIdAndUserId(id, user.getId()).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
        log.info("Deleted Book with id {}", id);
    }

    @Override
    public BookDto createBook(BookDto bookDto, List<String> keys, List<String> values) {
        CategoryDto category = categoryService.getCategoryById(bookDto.getCategory().getId());
        Map<String, String> metadata = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = i < values.size() ? values.get(i) : "";
            if (key != null && !key.isBlank()) {
                metadata.put(key, value);
            }
        }
        bookDto.setCategory(category);
        bookDto.setMetadata(metadata);
        bookDto.setCreatedAt(Instant.now());
        bookDto.setUpdatedAt(Instant.now());
        bookDto.setStatus(ContentStatus.PENDING_REVIEW);


        log.info("Created Book with id {}", bookDto.getId());
        return bookMapper.toDto(bookRepository.save(bookMapper.toEntity(bookDto)));
    }



    private Page<BookDto> getPage(Supplier<Page<Book>> supplier, String notFoundMessage) {
        Page<Book> bookPage = supplier.get();
        if (bookPage.isEmpty()) {
            throw new BookNotFoundException(notFoundMessage);
        }
        log.info("Получено {} книг на странице", bookPage.getSize());
        return bookPage.map(bookMapper::toDto);
    }

    private Page<BookDto> getPage(Supplier<Page<Book>> supplier) {
        return getPage(supplier, "книги не были найдены");
    }
}
