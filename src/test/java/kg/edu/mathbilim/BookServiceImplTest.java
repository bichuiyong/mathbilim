package kg.edu.mathbilim;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BookNotFoundException;
import kg.edu.mathbilim.mapper.BookMapper;
import kg.edu.mathbilim.mapper.FileMapper;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.BookRepository;
import kg.edu.mathbilim.service.impl.BookServiceImpl;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import kg.edu.mathbilim.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserService userService;
    @Mock
    private FileService fileService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private FileMapper fileMapper;
    @Mock
    private MultipartFile attachment;
    @Mock
    private MultipartFile image;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;
    private User authUser;
    private UserDto authUserDto;

    @BeforeEach
    void setUp() {
        authUser = User.builder()
                .id(1L)
                .name("Test")
                .surname("User")
                .email("test@example.com")
                .enabled(true)
                .role(Role.builder().id(1).name("USER").build())
                .build();

        authUserDto = UserDto.builder()
                .id(1L)
                .name("Test")
                .surname("User")
                .email("test@example.com")
                .role(
                        RoleDto.builder()
                                .name("USER")
                                .build()
                )
                .build();

        book = Book.builder()
                .id(1L)
                .name("Test Book")
                .description("Test Description")
                .creator(authUser)
                .file(File.builder()
                        .id(1L)
                        .filename("book.pdf")
                        .filePath("s3/books/1/book.pdf")
                        .build())
                .mainImage(File.builder()
                        .id(2L)
                        .filename("image.jpg")
                        .filePath("s3/books/1/image.jpg")
                        .build())
                .category(Category.builder().id(1).build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(ContentStatus.PENDING_REVIEW)
                .build();

        bookDto = BookDto.builder()
                .id(1L)
                .name("Test Book")
                .description("Test Description")
                .file(FileDto.builder().id(1L).build())
                .creator(authUserDto)
                .category(CategoryDto.builder().id(1).build())
                .build();

        lenient().when(userService.getAuthUser()).thenReturn(authUserDto);
        lenient().when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);
    }
    @Test
    @DisplayName("Create book - success")
    void createBook_Success() {
        when(attachment.isEmpty()).thenReturn(false);
        when(fileService.uploadFile(any(), anyString())).thenReturn(FileDto.builder().id(1L).build());
        when(bookMapper.toEntity(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.createBook(attachment, image, bookDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(fileService).uploadFile(eq(attachment), anyString());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Create book - empty attachment should throw exception")
    void createBook_EmptyAttachment_ShouldThrowException() {
        when(attachment.isEmpty()).thenReturn(true);

        assertThatThrownBy(() -> bookService.createBook(attachment, image, bookDto))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    @DisplayName("Update book - success with both files")
    void updateBook_SuccessWithBothFiles() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(attachment.isEmpty()).thenReturn(false);
        when(image.isEmpty()).thenReturn(false);
        when(fileService.updateFile(eq(1L), any())).thenReturn(FileDto.builder().id(1L).build());
        when(fileService.updateFile(eq(2L), any())).thenReturn(FileDto.builder().id(2L).build());
        when(bookMapper.toEntity(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.updateBook(1L, attachment, bookDto, image);

        assertThat(result).isNotNull();
        verify(fileService).updateFile(eq(1L), eq(attachment));
        verify(fileService).updateFile(eq(2L), eq(image));
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Update book - success with empty files")
    void updateBook_SuccessWithEmptyFiles() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(attachment.isEmpty()).thenReturn(true);
        when(image.isEmpty()).thenReturn(true);
        when(fileMapper.toDto(any(File.class))).thenReturn(FileDto.builder().id(1L).build());
        when(bookMapper.toEntity(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.updateBook(1L, attachment, bookDto, image);

        assertThat(result).isNotNull();
        verify(fileService, never()).updateFile(anyLong(), any());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Update book - not found should throw exception")
    void updateBook_NotFound_ShouldThrowException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBook(1L, attachment, bookDto, image))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    @DisplayName("Get all books - with query")
    void getAllBooks_WithQuery() {
        Pageable pageable = PaginationUtil.createPageableWithSort(1, 10, "name", "asc");
        when(bookRepository.findAllBooksByLanguageAndQuery(eq("test"), eq(ContentStatus.APPROVED), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        Page<BookDto> result = bookService.getAllBooks("APPROVED", "test", 1, 10, "name", "asc", null);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findAllBooksByLanguageAndQuery(eq("test"), eq(ContentStatus.APPROVED), eq(pageable));
    }


    @Test
    @DisplayName("Get all books - with category")
    void getAllBooks_WithCategory() {
        Pageable pageable = PaginationUtil.createPageableWithSort(1, 10, "name", "asc");
        when(bookRepository.findAllBooksByCategory(eq(1L), eq(ContentStatus.APPROVED), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        Page<BookDto> result = bookService.getAllBooks("APPROVED", null, 1, 10, "name", "asc", 1L);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findAllBooksByCategory(eq(1L), eq(ContentStatus.APPROVED), eq(pageable));
    }

    @Test
    @DisplayName("Get all books - default case")
    void getAllBooks_DefaultCase() {
        Pageable pageable = PaginationUtil.createPageableWithSort(1, 10, "name", "asc");

        when(bookRepository.findAllBooksByLanguage(eq(ContentStatus.PENDING_REVIEW), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        Page<BookDto> result = bookService.getAllBooks("PENDING_REVIEW", null, 1, 10, "name", "asc", null);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findAllBooksByLanguage(eq(ContentStatus.PENDING_REVIEW), eq(pageable));
    }


    @Test
    @DisplayName("Get all books - invalid status")
    void getAllBooks_InvalidStatus() {
        assertThatThrownBy(() -> bookService.getAllBooks("APPROVED", null, 1, 10, "name", "asc", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}