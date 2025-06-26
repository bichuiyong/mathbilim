package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.post.PostTypeTranslationMapper;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.repository.post.PostTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeTranslationService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeTranslationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTypeTranslationServiceImpl extends AbstractTypeTranslationService<
        PostTypeTranslation,
        PostTypeTranslationDto,
        PostTypeTranslationRepository,
        PostTypeTranslationMapper> implements PostTypeTranslationService {

    public PostTypeTranslationServiceImpl(PostTypeTranslationRepository repository,
                                          PostTypeTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getNotFoundMessage() {
        return "Перевод для этого типа поста не был найден";
    }

    @Override
    public List<PostTypeTranslationDto> getTranslationsByPostTypeId(Integer postTypeId) {
        return super.getTranslationsByTypeId(postTypeId);
    }

    @Override
    public void deleteAllTranslationsByPostTypeId(Integer postTypeId) {
        super.deleteAllTranslationsByTypeId(postTypeId);
    }
}