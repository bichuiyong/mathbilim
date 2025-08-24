package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.exception.accs.ContentNotAvailableException;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.exception.nsee.BookNotFoundException;
import kg.edu.mathbilim.mapper.BookMapper;
import kg.edu.mathbilim.mapper.FileMapper;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.BookRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractContentService;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    private final BookRepository bookRepository;
    private final FileMapper fileMapper;

    public BookServiceImpl(BookRepository repository, BookMapper mapper, UserService userService, FileService fileService, CategoryService categoryService, BookRepository bookRepository, FileMapper fileMapper) {
        super(repository, mapper, userService, fileService);
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.fileMapper = fileMapper;
    }


    @Override
    public Page<BookDto> getAllBooks(
            String status,
            String query,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
        );

        Page<Book> books = repository.searchByStatusAndQuery(
                ContentStatus.valueOf(status),
                "%" + (query == null ? "" : query.toLowerCase()) + "%",
                pageable
        );

        return books.map(mapper::toDto);
    }


    @Override
    public void approve(Long id, String email) {
        User user = userService.findByEmail(email);
        Book book = repository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setStatus(ContentStatus.APPROVED);
        book.setApprovedBy(user);
        repository.saveAndFlush(book);
    }

    @Override
    public void reject(Long id, String email) {
        User user = userService.findByEmail(email);
        Book book = repository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setStatus(ContentStatus.REJECTED);
        book.setApprovedBy(user);
        repository.saveAndFlush(book);
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
    }

    @Override
    public BookDto getBookById(Long id, String email) {
        Book book = repository.findById(id)
                .orElseThrow(BlogNotFoundException::new);

        if (email == null || email.trim().isEmpty()) {
            if (book.getStatus() != ContentStatus.APPROVED) {
                throw new ContentNotAvailableException("Для просмотра этой книги необходимо войти в систему");
            }
            return mapper.toDto(book);
        }

        User user = userService.findByEmail(email);

        boolean isOwner = book.getCreator().getId().equals(user.getId());
        boolean isAdmin = user.getRole() != null && "ADMIN".equals(user.getRole().getName());
        boolean isModer = user.getRole() != null && "MODER".equals(user.getRole().getName());
        boolean isSuperAdmin = user.getRole() != null && "SUPER_ADMIN".equals(user.getRole().getName());

        boolean hasAdminPrivileges = isAdmin || isModer || isSuperAdmin;

        if (isOwner) {
            incrementViewCount(id);
            return mapper.toDto(book);
        }

        if (hasAdminPrivileges) {
            incrementViewCount(id);
            return mapper.toDto(book);
        }

        if (book.getStatus() == ContentStatus.PENDING_REVIEW) {
            throw new ContentNotAvailableException("Книга находится на модерации и недоступен для просмотра");
        }

        if (book.getStatus() == ContentStatus.REJECTED) {
            throw new ContentNotAvailableException("Книга был отклонен модерацией и недоступен для просмотра");
        }

        if (book.getStatus() != ContentStatus.APPROVED) {
            throw new ContentNotAvailableException("Книга недоступен для просмотра");

        }

        incrementViewCount(id);
        return mapper.toDto(book);
    }

    @Override
    public Page<BookDto> getContentByCreatorIdBook(Long creatorId, Pageable pageable, String query) {

        if (query != null) {
            Page<Book> allBooksWithQuery = repository.getBooksByStatusWithQuery(ContentStatus.APPROVED, pageable, query);

            return allBooksWithQuery.map(mapper::toDto);
        }

        Page<Book> allBooks = repository.getBooksByCreator(ContentStatus.APPROVED, pageable, creatorId);


        return allBooks.map(mapper::toDto);
    }

    @Override
    public Page<BookDto> getHisotryBook(Long creatorId, Pageable pageable, String query, String status) {
        ContentStatus contentStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                contentStatus = ContentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
            }
        }

        if (query != null && !query.trim().isEmpty()) {
            if (contentStatus != null) {
                return repository.getBooksByCreatorAndStatusAndQuery(
                        contentStatus, creatorId, query, pageable
                ).map(mapper::toDto);
            } else {
                return repository.getBooksWithQuery(
                        creatorId, query.trim(), pageable
                ).map(mapper::toDto);
            }
        }

        if (contentStatus != null) {
            return repository.getBooksByCreatorAndStatus(
                            contentStatus, creatorId, pageable
                    )
                    .map(mapper::toDto);
        }

        return getContentByCreatorId(creatorId, pageable);
    }


    @Override
    public Page<BookDto> getBooksForModeration(Pageable pageable, String query) {
        if (query != null) {
            Page<Book> allBooksWithQuery = repository.getBooksByStatusWithQuery(ContentStatus.PENDING_REVIEW, pageable, query);
            return allBooksWithQuery.map(mapper::toDto);
        }

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
    public BookDto createBook(MultipartFile attachment, MultipartFile image, BookDto bookDto) {
        if (attachment.isEmpty()) {
            throw new BookNotFoundException();
        }
        bookDto.setFile(fileService.uploadFile(attachment, "books/" + bookDto.getId()));
        bookDto.setCreator(userService.getAuthUser());
        return createBase(bookDto, image, null);
    }

    @Override
    public BookDto updateBook(long id, MultipartFile file, BookDto bookDto, MultipartFile image) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if (file.isEmpty()) {
            bookDto.setFile(fileMapper.toDto(book.getFile()));
        }
        if (image.isEmpty()) {
            bookDto.setMainImage(fileMapper.toDto(book.getMainImage()));
        } else if (!image.isEmpty() && !file.isEmpty()) {
            bookDto.setFile(fileService.updateFile(book.getFile().getId(), file));
            bookDto.setMainImage(fileService.updateFile(book.getMainImage().getId(), image));
        }
        bookDto.setCreator(userService.getAuthUser());
        bookDto.setId(id);
        return createBase(bookDto, image, null);
    }

    @Override
    public Page<BookDto> getAllBooks(String status, String query, int page, int size, String sortBy, String sortDirection, Long categoryId) {
        ContentStatus status1 = ContentStatus.fromName(status);
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query != null) {
            return PaginationUtil.getPage(() -> repository.findAllBooksByLanguageAndQuery(query, status1, pageable), mapper::toDto);
        }
        if (categoryId != null) {
            return PaginationUtil.getPage(() -> repository.findAllBooksByCategory(categoryId, status1, pageable), mapper::toDto);
        }
        return PaginationUtil.getPage(() -> repository.findAllBooksByLanguage(status1, pageable), mapper::toDto);
    }

    @Override
    public Long countBookForModeration() {
        return repository.countByStatus(ContentStatus.PENDING_REVIEW);
    }
}