package kg.edu.mathbilim.service.interfaces.news;

import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsService extends BaseTranslatableService<NewsDto, NewsTranslationDto> {
    @Transactional
    NewsDto create(CreateNewsDto createNewsDto);

    Page<NewsDto> getContentByCreatorIdNews(Long creatorId, Pageable pageable, String query);

    Page<NewsDto> getHistoryNews(Long creatorId, Pageable pageable, String query, String status);

    NewsDto getNewsById(Long id);

    Page<NewsDto> getNewsByLang(String query, int page, int size, String sortBy, String sortDirection, String lang);

    News findByNewsId(Long newsId);

    List<NewsDto> getNewsByMainPage();
}
