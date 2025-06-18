package kg.edu.mathbilim.service.interfaces.news;

import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface NewsTranslationService {
    @Transactional
   void saveTranslations(Long id, Set<NewsTranslationDto> newsTranslationDtoSet);

    NewsTranslationDto upsertTranslation(NewsTranslationDto dto);

    @Transactional
    NewsTranslationDto updateTranslation(Long newsId, String languageCode, String title, String content);

    @Transactional
    NewsTranslationDto createTranslation(NewsTranslationDto dto);
}
