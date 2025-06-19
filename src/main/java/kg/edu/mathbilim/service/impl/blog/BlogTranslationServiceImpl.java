package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.blog.BlogTranslationMapper;
import kg.edu.mathbilim.model.blog.BlogTranslation;
import kg.edu.mathbilim.model.blog.BlogTranslationId;
import kg.edu.mathbilim.repository.blog.BlogTranslationRepository;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogTranslationServiceImpl implements BlogTranslationService {
    private final BlogTranslationRepository blogTranslationRepository;
    private final BlogTranslationMapper blogTranslationMapper;

    @Override
    public void saveTranslations(Long blogId, Set<BlogTranslationDto> translations) {
        if (translations == null || translations.isEmpty()) {
            return;
        }

        for (BlogTranslationDto translation : translations) {
            if (translation.getTitle() != null && !translation.getTitle().trim().isEmpty()
                    && translation.getContent() != null && !translation.getContent().trim().isEmpty()) {
                translation.setBlogId(blogId);
                upsertTranslation(translation);
            }
        }

        log.info("Saved {} translations for post {}", translations.size(), blogId);
    }

    @Transactional
    @Override
    public BlogTranslationDto upsertTranslation(BlogTranslationDto dto) {
        BlogTranslationId id = new BlogTranslationId();
        id.setBlogId(dto.getBlogId());
        id.setLanguageCode(dto.getLanguageCode());

        boolean exists = blogTranslationRepository.existsById(id);

        if (exists) {
            return updateTranslation(dto.getBlogId(), dto.getLanguageCode(), dto.getTitle(), dto.getContent());
        } else {
            return createTranslation(dto);
        }
    }

    @Transactional
    @Override
    public BlogTranslationDto createTranslation(BlogTranslationDto dto) {
        BlogTranslation translation = blogTranslationMapper.toEntity(dto);
        blogTranslationRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTitle());
        return dto;
    }

    @Transactional
    @Override
    public BlogTranslationDto updateTranslation(Long blogId, String languageCode, String title, String content) {
        BlogTranslation translation = getTranslationEntity(blogId, languageCode);
        translation.setTitle(title);
        translation.setContent(content);
        BlogTranslation saved = blogTranslationRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, title);
        return blogTranslationMapper.toDto(saved);
    }

    @Override
    public BlogTranslation getTranslationEntity(Long blogId, String languageCode) {
        BlogTranslationId id = new BlogTranslationId();
        id.setBlogId(blogId);
        id.setLanguageCode(languageCode);

        return blogTranslationRepository.findById(id)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этого поста не был найден"));
    }
}
