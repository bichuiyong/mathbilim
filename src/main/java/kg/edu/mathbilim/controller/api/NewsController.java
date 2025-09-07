package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController("restNews")
@RequestMapping("api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;


    @GetMapping()
    public ResponseEntity<?> all(
            @RequestParam(required = false) String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        Page<NewsDto> news = newsService.getPage(query, page, size, sortBy, sortDirection);

        List<NewsDto> filteredNews = news.stream()
                .filter(dto -> !dto.isDeleted())
                .collect(Collectors.toList());


        Page<NewsDto> filteredPage = new PageImpl<>(
                filteredNews,
                news.getPageable(),
                filteredNews.size()
        );

        return ResponseEntity.ok(filteredPage);
    }


    @PostMapping("{id}/share")
    public ResponseEntity<Void> shareNews(@PathVariable Long id) {
        newsService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("main")
    public ResponseEntity<?> getLatestNews() {
        return ResponseEntity.ofNullable(newsService.getNewsByMainPage());
    }


}
