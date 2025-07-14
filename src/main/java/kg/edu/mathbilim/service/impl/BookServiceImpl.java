package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.enums.ContentStatus;
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
import org.springframework.data.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public Page<BookDto> getHisotryBook(Long creatorId, Pageable pageable) {
        return getContentByCreatorId(creatorId, pageable);
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
    public BookDto createBook(MultipartFile attachment,MultipartFile image, BookDto bookDto) {
        if(attachment.isEmpty()) {
            throw new BookNotFoundException();
        }
        bookDto.setFile(fileService.uploadFile(attachment,"books/" + bookDto.getId()));
        bookDto.setCreator(userService.getAuthUser());
        return createBase(bookDto, image, null);
    }

    @Override
    public BookDto updateBook(long id, MultipartFile file, BookDto bookDto, MultipartFile image) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if(file.isEmpty()){
            bookDto.setFile(fileMapper.toDto(book.getFile()));
        }
        if(image.isEmpty()){
            bookDto.setMainImage(fileMapper.toDto(book.getMainImage()));
        }
        else if(!image.isEmpty() && !file.isEmpty()){
            bookDto.setFile(fileService.updateFile(book.getFile().getId(),file));
            bookDto.setMainImage(fileService.updateFile(book.getMainImage().getId(),image));
        }
        bookDto.setCreator(userService.getAuthUser());
        bookDto.setId(id);
        return createBase(bookDto, image, null);
    }

    @Override
    public Page<BookDto> getAllBooks(String status,String query,int page, int size, String sortBy, String sortDirection, Long categoryId) {
        ContentStatus status1 =  ContentStatus.fromName(status);
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if(query!=null) {
            return PaginationUtil.getPage(()->repository.findAllBooksByLanguageAndQuery(query,status1,pageable), mapper::toDto);
        }
        if(categoryId!=null) {
            return PaginationUtil.getPage(()-> repository.findAllBooksByCategory(categoryId,status1,pageable), mapper::toDto);
        }
       return PaginationUtil.getPage(()->repository.findAllBooksByLanguage(status1,pageable), mapper::toDto);
    }
}