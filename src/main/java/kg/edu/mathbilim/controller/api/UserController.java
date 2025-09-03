package kg.edu.mathbilim.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private final NewsService newsService;

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
    public ResponseEntity<Void> createUserFromAdmin(@RequestBody @Valid UserDto userDto, HttpServletRequest request) {
        userService.createUserFromAdmin(userDto, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserEditDto userDto, @PathVariable Long id) {
        userService.updateUser(userDto, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("content")
    public ResponseEntity<Page<?>> getContentByCreator(
            Pageable pageable,
            @RequestParam Long creatorId,
            @RequestParam String type,
            @RequestParam(required = false) String query) {

        log.info("Получен запрос на контент: creatorId={}, type={}, page={}, size={}, query={}",
                creatorId, type, pageable.getPageNumber(), pageable.getPageSize(), query);

        Page<?> contentPage;

        try {
            switch (type.toLowerCase()) {
                case "post" -> {
                    contentPage = postService.getPostsByCreator(creatorId, pageable, query);
                    log.info("Получено {} постов", contentPage.getNumberOfElements());
                }
                case "event" -> {
                    contentPage = eventService.getContentByCreatorIdEvent(creatorId, pageable, query);
                    log.info("Получено {} событий", contentPage.getNumberOfElements());
                }
                case "book" -> {
                    contentPage = bookService.getContentByCreatorIdBook(creatorId, pageable, query);
                    log.info("Получено {} книг", contentPage.getNumberOfElements());
                }
                case "blog" -> {
                    contentPage = blogService.getContentByCreatorIdBlog(creatorId, pageable, query);
                    log.info("Получено {} блогов", contentPage.getNumberOfElements());
                }
                case "news" -> {
                    contentPage = newsService.getContentByCreatorIdNews(creatorId, pageable, query);
                    log.info("Получено {} новостей", contentPage.getNumberOfElements());
                }
                default -> {
                    log.warn("Неверный тип контента: {}", type);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
                }
            }
        } catch (Exception e) {
            log.error("Ошибка при получении контента для creatorId={}, type={}", creatorId, type, e);
            throw e;
        }

        return ResponseEntity.ok(contentPage);
    }



    @GetMapping("history")
    public ResponseEntity<Page<?>> getHistoryByCreator(
            Pageable pageable,
            @RequestParam Long id,
            @RequestParam String type,
            @RequestParam String status,
            @RequestParam(required = false) String query) {

        Page<?> historyPage;
        switch (type.toLowerCase()) {
            case "post" -> historyPage = postService.getHisotryPost(id, pageable, query, status);
            case "event" -> historyPage = eventService.getHistoryEvent(id, pageable, query, status);
            case "book" -> historyPage = bookService.getHistoryBook(id, pageable, query, status);
            case "blog" -> historyPage = blogService.getHistoryBlog(id, pageable, query, status);
            case "news" -> historyPage = newsService.getHistoryNews(id, pageable, query, status);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
        }
        return ResponseEntity.ok(historyPage);
    }


    @GetMapping("moder")
    public ResponseEntity<Page<?>> getContentForModeration(
            Pageable pageable,
            @RequestParam String type,
            @RequestParam(required = false) String query) {
        Page<BlogDto> blogs = blogService.getBlogsForModeration(pageable, query);

        for (BlogDto blog : blogs) {
            for (BlogTranslationDto translation : blog.getBlogTranslations()) {
                log.info("Blog ID: {}, Language code: {}", blog.getId(), translation.getLanguageCode());
            }
        }

        Page<PostDto> post = postService.getPostsForModeration(pageable, query);

        for (PostDto blog : post) {
            for (PostTranslationDto translation : blog.getPostTranslations()) {
                log.info("Post ID: {}, Language code: {}", blog.getId(), translation.getLanguageCode());
            }
        }

        Page<?> contentPage;

        switch (type.toLowerCase()) {
            case "post" -> contentPage = postService.getPostsForModeration(pageable, query);
            case "event" -> contentPage = eventService.getEventsForModeration(pageable, query);
            case "book" -> contentPage = bookService.getBooksForModeration(pageable, query);
            case "blog" -> contentPage = blogService.getBlogsForModeration(pageable, query);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
        }
        Page<BookDto> contentPageBook = bookService.getBooksForModeration(pageable, query);
        contentPageBook.getContent().forEach(book -> {
            log.info("Book query: {}", query);
        });

        return ResponseEntity.ok(contentPage);
    }


    @GetMapping("count")
    public ResponseEntity<String> count() {
        int totalCount = 0;
        totalCount += postService.countPostsForModeration();
        totalCount += eventService.countEventForModeration();
        totalCount += bookService.countBookForModeration();
        totalCount += blogService.countBlogForModeration();

        String result = totalCount > 99 ? "99+" : String.valueOf(totalCount);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{type}/{id}")
    public ResponseEntity<?> getContentDetails(@PathVariable String type, @PathVariable Long id) {
        try {
            Object content = switch (type.toLowerCase()) {
                case "posts" -> postService.getPostById(id);
                case "events" -> eventService.getById(id);
                case "books" -> bookService.getById(id);
                case "blogs" -> blogService.getById(id);
                case "news" -> newsService.getById(id);
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
