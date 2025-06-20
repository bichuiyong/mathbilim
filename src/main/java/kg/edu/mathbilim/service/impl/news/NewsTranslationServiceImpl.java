package kg.edu.mathbilim.service.impl.news;

import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.mapper.news.NewsTranslationMapper;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.repository.news.NewsTranslationRepository;
import kg.edu.mathbilim.service.interfaces.news.NewsTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsTranslationServiceImpl implements NewsTranslationService {
    private final NewsTranslationRepository repository;
    private final NewsTranslationMapper mapper;


    @Transactional
    @Override
    public void saveTranslations(Long id, Set<NewsTranslationDto> translations) {
        if (translations == null || translations.isEmpty()) {
            return;
        }

        for (NewsTranslationDto translation : translations) {
            if (translation.getTitle() != null && !translation.getTitle().trim().isEmpty()
                    && translation.getContent() != null && !translation.getContent().trim().isEmpty()) {
                translation.setNewsId(id);
                upsertTranslation(translation);
            }
        }

        log.info("Saved {} translations for post {}", translations.size(), id);
    }
    @Override
    public NewsTranslationDto upsertTranslation(NewsTranslationDto dto) {
        NewsTranslationId id = new NewsTranslationId();
        id.setNewsId(dto.getNewsId());
        id.setLanguageCode(dto.getLanguageCode());

        boolean exists = repository.existsById(id);

        if (exists) {
            return updateTranslation(dto.getNewsId(), dto.getLanguageCode(), dto.getTitle(), dto.getContent());
        } else {
            return createTranslation(dto);
        }
    }

    @Transactional
    @Override
    public NewsTranslationDto updateTranslation(Long newsId, String languageCode, String title, String content) {
        NewsTranslation newsTranslation = getTranslationEntity(newsId, languageCode);
        newsTranslation.setTitle(title);
        newsTranslation.setContent(content);
        NewsTranslation newsTranslation1 =  repository.save(newsTranslation);
        log.info("Updated {} translation for post {}", newsTranslation1, newsId);
        return mapper.toDto(newsTranslation1);
    }

    @Transactional
    @Override
    public NewsTranslationDto createTranslation(NewsTranslationDto dto) {
        NewsTranslation newsTranslation =  repository.save(mapper.toEntity(dto));
        log.info("Saved {} translation for post {}", dto.getNewsId(), dto.getLanguageCode());
        return  mapper.toDto(newsTranslation);
    }

    private NewsTranslation getTranslationEntity(Long id, String languageCode) {
        NewsTranslationId newsTranslationId = new NewsTranslationId();
        newsTranslationId.setNewsId(id);
        newsTranslationId.setLanguageCode(languageCode);

        return repository.findById(newsTranslationId).orElseThrow(
                () -> new NoSuchElementException("No translation found for id: " + id)
        );
    }


}
