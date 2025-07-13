package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BookNotFoundException;
import kg.edu.mathbilim.mapper.BookMapper;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.repository.BookRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractContentService;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl extends
        AbstractContentService<
                Book,
                BookDto,
                BookRepository,
                BookMapper
                >
        implements BookService {

    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository repository, BookMapper mapper, UserService userService, FileService fileService, CategoryService categoryService) {
        super(repository, mapper, userService, fileService);
        this.categoryService = categoryService;
    }

    @Override
    protected RuntimeException getNotFoundException() {
        return new BookNotFoundException();
    }

    @Override
    protected String getEntityName() {
        return "book";
    }

    @Override
    protected String getFileUploadPath(Book entity) {
        return "books/" + entity.getId();
    }

    @Override
    protected void handleTranslations(BookDto dto, Long entityId) {
        // Book не имеет переводов, поэтому пустая реализация
    }

    @Override
    public Page<BookDto> getContentByCreatorIdBook(Long creatorId, Pageable pageable) {
        Page<BookDto> allBooks = getContentByCreatorId(creatorId, pageable);

        List<BookDto> approvedBooks = allBooks
                .stream()
                .filter(book -> book.getStatus() == ContentStatus.APPROVED)
                .toList();

        return new PageImpl<>(approvedBooks, pageable, approvedBooks.size());
    }

    @Override
    public Page<BookDto> getBooksForModeration(Pageable pageable) {
        Page<Book> books = repository.getBooksByStatus(ContentStatus.PENDING_REVIEW, pageable);
        return PaginationUtil.getPage(() -> books, mapper::toDto);
    }

    @Override
    protected void processAdditionalFields(Object createDto, Book savedEntity) {
        if (createDto instanceof BookDto bookDto) {
            Category category = categoryService.getCategoryEntity(bookDto.getCategory().getId());
            savedEntity.setCategory(category);
        }
    }

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        return createBase(bookDto, null, null);
    }
}