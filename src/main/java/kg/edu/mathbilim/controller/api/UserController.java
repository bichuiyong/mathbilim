package kg.edu.mathbilim.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController("restUser")
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final EventService eventService;
    private final BlogService blogService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUserPage(@RequestParam(required = false, defaultValue = "1") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false) String query,
                                                     @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                     @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(userService.getUserPage(query, page, size, sortBy, sortDirection));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ofNullable(userService.getDtoById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> toggleUser(@PathVariable Long id) {
        userService.toggleUserBlocking(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDto userDto, HttpServletRequest request) {
        userService.createUser(userDto, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserEditDto userDto, @PathVariable Long id) {
        userService.updateUser(userDto, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("content")
    public ResponseEntity<Page<?>> getContentByCreator(
            Pageable pageable,
            @RequestParam Long creatorId,
            @RequestParam String type) {

        Page<?> contentPage;
        switch (type.toLowerCase()) {
            case "post" -> contentPage = postService.getPostsByCreator(creatorId, pageable);
            case "event" -> contentPage = eventService.getContentByCreatorIdEvent(creatorId, pageable);
            case "book" -> contentPage = bookService.getContentByCreatorIdBook(creatorId, pageable);
            case "blog" -> contentPage = blogService.getContentByCreatorIdBlog(creatorId, pageable);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
        }
        return ResponseEntity.ok(contentPage);
    }


    @GetMapping("moder")
    public ResponseEntity<Page<?>> getContentForModeration(
            Pageable pageable,
            @RequestParam String type) {
        Page<BlogDto> blogs = blogService.getBlogsForModeration(pageable);

        for (BlogDto blog : blogs) {
            for (BlogTranslationDto translation : blog.getBlogTranslations()) {
                log.info("Blog ID: {}, Language code: {}", blog.getId(), translation.getLanguageCode());
            }
        }

        Page<PostDto> post = postService.getPostsForModeration(pageable);

        for (PostDto blog : post) {
            for (PostTranslationDto translation : blog.getPostTranslations()) {
                log.info("Post ID: {}, Language code: {}", blog.getId(), translation.getLanguageCode());
            }
        }

        Page<?> contentPage;

        switch (type.toLowerCase()) {
            case "post" -> contentPage = postService.getPostsForModeration(pageable);
            case "event" -> contentPage = eventService.getEventsForModeration(pageable);
            case "book" -> contentPage = bookService.getBooksForModeration(pageable);
            case "blog" -> contentPage = blogService.getBlogsForModeration(pageable);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
        }
        Page<BookDto> contentPageBook = bookService.getBooksForModeration(pageable);
        contentPageBook.getContent().forEach(book -> {
            log.info("Book ID: {}", book.getId());
        });

        return ResponseEntity.ok(contentPage);
    }

    @GetMapping("{type}/{id}")
    public ResponseEntity<?> getContentDetails(@PathVariable String type, @PathVariable Long id) {
        try {
            Object content = switch (type.toLowerCase()) {
                case "posts" -> postService.getPostById(id);
                case "events" -> eventService.getById(id);
                case "books" -> bookService.getById(id);
                case "blogs" -> blogService.getById(id);
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
            };
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Ошибка при получении контента: " + e.getMessage()
            ));
        }
    }
}
