package kg.edu.mathbilim.service.impl.news;

import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.mapper.news.NewsTranslationMapper;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.repository.news.NewsTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslationService;
import kg.edu.mathbilim.service.interfaces.news.NewsTranslationService;
import org.springframework.stereotype.Service;

@Service
public class NewsTranslationServiceImpl extends
        AbstractTranslationService<
                NewsTranslationDto,
                NewsTranslation,
                NewsTranslationId,
                NewsTranslationRepository,
                NewsTranslationMapper
                >
        implements NewsTranslationService {

    public NewsTranslationServiceImpl(NewsTranslationRepository repository, NewsTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected NewsTranslationId createTranslationId(Long entityId, String languageCode) {
        NewsTranslationId id = new NewsTranslationId();
        id.setNewsId(entityId);
        id.setLanguageCode(languageCode);
        return id;
    }

    @Override
    protected void setEntityId(NewsTranslationDto dto, Long entityId) {
        dto.setNewsId(entityId);
    }

    @Override
    protected String getEntityName() {
        return "news";
    }

    @Override
    protected Long getEntityIdFromDto(NewsTranslationDto dto) {
        return dto.getNewsId();
    }

    @Override
    protected void deleteAllTranslationsByEntityIdImpl(Long entityId) {
        repository.deleteByNews_Id(entityId);
    }
}