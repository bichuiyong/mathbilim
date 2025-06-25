package kg.edu.mathbilim.service.interfaces.news;

import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface NewsService extends BaseTranslatableService<NewsDto, NewsTranslationDto> {
    @Transactional
    NewsDto create(CreateNewsDto createNewsDto);

    Page<NewsDto> getNewsByLang(String query, int page, int size, String sortBy, String sortDirection, String lang);
}
