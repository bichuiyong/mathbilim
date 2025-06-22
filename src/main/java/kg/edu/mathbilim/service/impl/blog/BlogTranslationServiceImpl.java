package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.mapper.blog.BlogTranslationMapper;
import kg.edu.mathbilim.model.blog.BlogTranslation;
import kg.edu.mathbilim.model.blog.BlogTranslationId;
import kg.edu.mathbilim.repository.blog.BlogTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslationService;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import org.springframework.stereotype.Service;

@Service
public class BlogTranslationServiceImpl extends
        AbstractTranslationService<
                BlogTranslationDto,
                BlogTranslation,
                BlogTranslationId,
                BlogTranslationRepository,
                BlogTranslationMapper
                >
        implements BlogTranslationService {

    public BlogTranslationServiceImpl(BlogTranslationRepository repository, BlogTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected BlogTranslationId createTranslationId(Long entityId, String languageCode) {
        BlogTranslationId id = new BlogTranslationId();
        id.setBlogId(entityId);
        id.setLanguageCode(languageCode);
        return id;
    }

    @Override
    protected void setEntityId(BlogTranslationDto dto, Long entityId) {
        dto.setBlogId(entityId);
    }

    @Override
    protected String getEntityName() {
        return "blog";
    }

    @Override
    protected Long getEntityIdFromDto(BlogTranslationDto dto) {
        return dto.getBlogId();
    }

    @Override
    protected void deleteAllTranslationsByEntityIdImpl(Long entityId) {
        repository.deleteByBlogId(entityId);
    }
}
