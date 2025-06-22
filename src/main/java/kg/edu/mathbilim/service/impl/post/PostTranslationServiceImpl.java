package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.mapper.post.PostTranslationMapper;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import kg.edu.mathbilim.repository.post.PostTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslationService;
import kg.edu.mathbilim.service.interfaces.post.PostTranslationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTranslationServiceImpl extends
        AbstractTranslationService<
                PostTranslationDto,
                PostTranslation,
                PostTranslationId,
                PostTranslationRepository,
                PostTranslationMapper
                >
        implements PostTranslationService {

    public PostTranslationServiceImpl(PostTranslationRepository repository, PostTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected PostTranslationId createTranslationId(Long entityId, String languageCode) {
        PostTranslationId id = new PostTranslationId();
        id.setPostId(entityId);
        id.setLanguageCode(languageCode);
        return id;
    }

    @Override
    protected void setEntityId(PostTranslationDto dto, Long entityId) {
        dto.setPostId(entityId);
    }

    @Override
    protected String getEntityName() {
        return "post";
    }

    @Override
    protected Long getEntityIdFromDto(PostTranslationDto dto) {
        return dto.getPostId();
    }

    @Override
    protected void deleteAllTranslationsByEntityIdImpl(Long entityId) {
        repository.deleteByPostId(entityId);
    }
}