package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.reference.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController("restAdmin")
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class AdminController {

    private final RoleService roleService;
    private final TranslationService translationService;
    private final PostService postService;
    private final BlogService blogService;
    private final EventService eventService;
    private final BookService bookService;
    private final NewsService newsService;
    private final OlympiadService olympiadService;

    private static final String STATUS = "PENDING_REVIEW";

    @PostMapping("approve/{type}/{id}")
    public ResponseEntity<Map<String, String>> approve(@PathVariable String type,
                                                       @PathVariable Long id,
                                                       Principal principal) {
        try {
            switch (type.toLowerCase()) {
                case "post" -> postService.approve(id, principal.getName());
                case "event" -> eventService.approve(id, principal.getName());
                case "book" -> bookService.approve(id, principal.getName());
                case "blog" -> blogService.approve(id, principal.getName());
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
            }

            log.info("Content {} with id {} approved by {}", type, id, principal.getName());

            return ResponseEntity.ok(Map.of(
                    "message", "Контент одобрен",
                    "status", "success"
            ));
        } catch (Exception e) {
            log.error("Error approving content {} with id {}: {}", type, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Ошибка при одобрении контента",
                            "status", "error"
                    ));
        }
    }

    @PostMapping("reject/{type}/{id}")
    public ResponseEntity<Map<String, String>> reject(@PathVariable String type,
                                                      @PathVariable Long id,
                                                      Principal principal) {
        try {
            switch (type.toLowerCase()) {
                case "post" -> postService.reject(id, principal.getName());
                case "event" -> eventService.reject(id, principal.getName());
                case "book" -> bookService.reject(id, principal.getName());
                case "blog" -> blogService.reject(id, principal.getName());
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
            }

            log.info("Content {} with id {} rejected by {}", type, id, principal.getName());

            return ResponseEntity.ok(Map.of(
                    "message", "Контент отклонён",
                    "status", "success"
            ));
        } catch (Exception e) {
            log.error("Error rejecting content {} with id {}: {}", type, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Ошибка при отклонении контента",
                            "status", "error"
                    ));
        }
    }

    @GetMapping("content")
    public ResponseEntity<Page<?>> getAllContent(Pageable pageable,
                                                 @RequestParam String type,
                                                 @RequestParam String status,
                                                 @RequestParam(required = false) String query) {

        Page<?> contentPage;

        try {
            switch (type.toLowerCase()) {
                case "post" -> {
                    contentPage = postService.getAllPost(pageable, query, status);
                }
                case "event" -> {
                    contentPage = eventService.getAllEvent(pageable, query, status);
                }
                case "book" -> {
                    contentPage = bookService.getAllBook(pageable, query, status);
                }
                case "blog" -> {
                    contentPage = blogService.getAllBlogs(pageable, query, status);
                }
                case "news" -> {
                    contentPage = newsService.getAllNews(pageable, query);
                }
                case "olympiad" -> {
                    contentPage = olympiadService.getAllOlympiad(pageable, query);
                }
                default -> {
                    log.warn("Неверный тип контента: {}", type);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип контента");
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return ResponseEntity.ok(contentPage);
    }

}