package kg.edu.mathbilim.service.interfaces.post;

import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.model.post.PostTranslation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface PostTranslationService {
    List<PostTranslationDto> getTranslationsByPostId(Long postId);

    PostTranslation getTranslationEntity(Long postId, String languageCode);

    PostTranslationDto getTranslation(Long postId, String languageCode);

    List<PostTranslationDto> getTranslationsByLanguage(String languageCode);

    @Transactional
    PostTranslationDto createTranslation(PostTranslationDto dto);

    @Transactional
    PostTranslationDto updateTranslation(Long postId, String languageCode, String title, String content);

    @Transactional
    PostTranslationDto upsertTranslation(PostTranslationDto dto);

    @Transactional
    void deleteTranslation(Long postId, String languageCode);

    @Transactional
    void deleteAllTranslationsByPostId(Long postId);

    boolean existsTranslation(Long postId, String languageCode);

    @Transactional
    void saveTranslations(Long postId, Set<PostTranslationDto> translations);
}
