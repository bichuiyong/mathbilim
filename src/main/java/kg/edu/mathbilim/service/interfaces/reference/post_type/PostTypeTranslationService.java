package kg.edu.mathbilim.service.interfaces.reference.post_type;

import kg.edu.mathbilim.dto.reference.post_type.PostTypeTranslationDto;
import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslation;

import java.util.List;

public interface PostTypeTranslationService {
    List<PostTypeTranslationDto> getTranslationsByPostTypeId(Integer postTypeId);

    PostTypeTranslation getTranslationEntity(Integer postTypeId, String languageCode);

    PostTypeTranslationDto getTranslation(Integer postTypeId, String languageCode);

    List<PostTypeTranslationDto> getTranslationsByLanguage(String languageCode);

    PostTypeTranslationDto createTranslation(PostTypeTranslationDto dto);

    PostTypeTranslationDto updateTranslation(Integer postTypeId, String languageCode, String newTranslation);

    PostTypeTranslationDto upsertTranslation(PostTypeTranslationDto dto);

    void deleteTranslation(Integer postTypeId, String languageCode);

    void deleteAllTranslationsByPostTypeId(Integer postTypeId);

    boolean existsTranslation(Integer postTypeId, String languageCode);
}
