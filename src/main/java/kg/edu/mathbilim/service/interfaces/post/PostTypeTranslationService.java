package kg.edu.mathbilim.service.interfaces.post;

import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.model.post.PostTypeTranslation;

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
