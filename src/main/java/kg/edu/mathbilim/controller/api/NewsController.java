package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("restNews")
@RequestMapping("api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;


    @GetMapping()
    public ResponseEntity<?> all(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt")  String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {

        return ResponseEntity.ofNullable(newsService.getNewsPage(page, size, sortBy, sortDirection));
    }
}
