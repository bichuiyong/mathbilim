package kg.edu.mathbilim.service.interfaces.blog;

import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.model.blog.BlogTranslation;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BlogTranslationService extends BaseTranslationService<BlogTranslationDto> {
    void saveTranslations(Long blogId, Set<BlogTranslationDto> filledTranslations);

    @Transactional
    BlogTranslationDto upsertTranslation(BlogTranslationDto dto);

    @Transactional
    BlogTranslationDto createTranslation(BlogTranslationDto dto);

    @Transactional
    BlogTranslationDto updateTranslation(Long blogId, String languageCode, String title, String content);

    BlogTranslation getTranslationEntity(Long blogId, String languageCode);
}
