package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.post.PostTranslationMapper;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import kg.edu.mathbilim.repository.post.PostTranslationRepository;
import kg.edu.mathbilim.service.interfaces.post.PostTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostTranslationServiceImpl implements PostTranslationService {
    private final PostTranslationRepository ptRepository;
    private final PostTranslationMapper ptMapper;

    @Override
    public List<PostTranslationDto> getTranslationsByPostId(Long postId) {
        return ptRepository.findByPostId(postId)
                .stream()
                .map(ptMapper::toDto)
                .toList();
    }

    @Override
    public PostTranslation getTranslationEntity(Long postId, String languageCode) {
        PostTranslationId id = new PostTranslationId();
        id.setPostId(postId);
        id.setLanguageCode(languageCode);

        return ptRepository.findById(id)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этого поста не был найден"));
    }

    @Override
    public PostTranslationDto getTranslation(Long postId, String languageCode) {
        return ptMapper.toDto(getTranslationEntity(postId, languageCode));
    }

    @Override
    public List<PostTranslationDto> getTranslationsByLanguage(String languageCode) {
        return ptRepository.findByIdLanguageCode(languageCode)
                .stream()
                .map(ptMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public PostTranslationDto createTranslation(PostTranslationDto dto) {
        PostTranslation translation = ptMapper.toEntity(dto);
        ptRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTitle());
        return dto;
    }

    @Transactional
    @Override
    public PostTranslationDto updateTranslation(Long postId, String languageCode, String title, String content) {
        PostTranslation translation = getTranslationEntity(postId, languageCode);
        translation.setTitle(title);
        translation.setContent(content);
        PostTranslation saved = ptRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, title);
        return ptMapper.toDto(saved);
    }

    @Transactional
    @Override
    public PostTranslationDto upsertTranslation(PostTranslationDto dto) {
        PostTranslationId id = new PostTranslationId();
        id.setPostId(dto.getPostId());
        id.setLanguageCode(dto.getLanguageCode());

        boolean exists = ptRepository.existsById(id);

        if (exists) {
            return updateTranslation(dto.getPostId(), dto.getLanguageCode(), dto.getTitle(), dto.getContent());
        } else {
            return createTranslation(dto);
        }
    }

    @Transactional
    @Override
    public void deleteTranslation(Long postId, String languageCode) {
        PostTranslationId id = new PostTranslationId();
        id.setPostId(postId);
        id.setLanguageCode(languageCode);
        ptRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllTranslationsByPostId(Long postId) {
        ptRepository.deleteByPostId(postId);
    }

    @Override
    public boolean existsTranslation(Long postId, String languageCode) {
        PostTranslationId id = new PostTranslationId();
        id.setPostId(postId);
        id.setLanguageCode(languageCode);
        return ptRepository.existsById(id);
    }

    @Transactional
    @Override
    public void saveTranslations(Long postId, Set<PostTranslationDto> translations) {
        if (translations == null || translations.isEmpty()) {
            return;
        }

        for (PostTranslationDto translation : translations) {
            if (translation.getTitle() != null && !translation.getTitle().trim().isEmpty()
                    && translation.getContent() != null && !translation.getContent().trim().isEmpty()) {
                translation.setPostId(postId);
                upsertTranslation(translation);
            }
        }

        log.info("Saved {} translations for post {}", translations.size(), postId);
    }
}