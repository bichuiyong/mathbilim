package kg.edu.mathbilim.service.impl.abstracts;

import kg.edu.mathbilim.dto.abstracts.AdminContentDto;
import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.abstracts.AdminContent;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslationService;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public abstract class AbstractTranslatableContentService<
        E extends AdminContent,
        D extends AdminContentDto,
        T extends ContentTranslationDto,
        R extends BaseContentRepository<E>,
        M extends BaseMapper<E, D>,
        TS extends BaseTranslationService<T>
        > extends AbstractContentService<E, D, R, M>
        implements BaseTranslatableService<D, T> {

    protected final TS translationService;

    protected AbstractTranslatableContentService(R repository, M mapper, UserService userService,
                                                 FileService fileService, TS translationService) {
        super(repository, mapper, userService, fileService);
        this.translationService = translationService;
    }

    @Override
    protected BaseTranslationService<?> getTranslationService() {
        return translationService;
    }

    protected abstract List<T> getTranslationsFromDto(D dto);

    @Override
    protected void handleTranslations(D dto, Long entityId) {
        List<T> translations = getTranslationsFromDto(dto);
        if (translations != null && !translations.isEmpty()) {
            Set<T> translationSet = new LinkedHashSet<>(translations);
            translationService.saveTranslations(entityId, translationSet);
        }
    }
}
