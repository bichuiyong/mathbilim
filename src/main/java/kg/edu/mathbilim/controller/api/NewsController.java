package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restNews")
@RequestMapping("api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;


    @GetMapping()
    public ResponseEntity<?> all(
            @RequestParam(required = false) String query,
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt")  String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {

        return ResponseEntity.ofNullable(newsService.getPage(query,page, size, sortBy, sortDirection));
    }

    @PostMapping("{id}/share")
    public ResponseEntity<Void> shareNews(@PathVariable Long id) {
        newsService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }
}
