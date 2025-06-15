package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.post_type.PostTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.post.PostTypeTranslationMapper;
import kg.edu.mathbilim.model.post.post_type.PostTypeTranslation;
import kg.edu.mathbilim.model.post.post_type.PostTypeTranslationId;
import kg.edu.mathbilim.repository.post.PostTypeTranslationRepository;
import kg.edu.mathbilim.service.interfaces.post.PostTypeTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostTypeTranslationServiceImpl implements PostTypeTranslationService {
    private final PostTypeTranslationRepository pttRepository;
    private final PostTypeTranslationMapper pttMapper;

    @Override
    public List<PostTypeTranslationDto> getTranslationsByPostTypeId(Integer postTypeId) {
        return pttRepository.findByPostTypeId(postTypeId)
                .stream()
                .map(pttMapper::toDto)
                .toList();
    }

    @Override
    public PostTypeTranslation getTranslationEntity(Integer postTypeId, String languageCode) {
        return pttRepository.findByPostTypeIdAndLanguageCode(postTypeId, languageCode)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этого типа поста не был найден"));
    }

    @Override
    public PostTypeTranslationDto getTranslation(Integer postTypeId, String languageCode) {
        return pttMapper.toDto(getTranslationEntity(postTypeId, languageCode));
    }

    @Override
    public List<PostTypeTranslationDto> getTranslationsByLanguage(String languageCode) {
        return pttRepository.findByLanguageCode(languageCode).stream()
                .map(pttMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PostTypeTranslationDto createTranslation(PostTypeTranslationDto dto) {
        PostTypeTranslation translation = pttMapper.toEntity(dto);
        pttRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTranslation());
        return dto;
    }

    @Override
    @Transactional
    public PostTypeTranslationDto updateTranslation(Integer postTypeId, String languageCode, String newTranslation) {
        PostTypeTranslation translation = getTranslationEntity(postTypeId, languageCode);
        translation.setTranslation(newTranslation);
        PostTypeTranslation saved = pttRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, newTranslation);
        return pttMapper.toDto(saved);
    }

    @Override
    @Transactional
    public PostTypeTranslationDto upsertTranslation(PostTypeTranslationDto dto) {
        return updateTranslation(dto.getPostTypeId(), dto.getLanguageCode(), dto.getTranslation());
    }

    @Override
    @Transactional
    public void deleteTranslation(Integer postTypeId, String languageCode) {
        PostTypeTranslationId id = new PostTypeTranslationId();
        id.setPostTypeId(postTypeId);
        id.setLanguageCode(languageCode);
        pttRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllTranslationsByPostTypeId(Integer postTypeId) {
        pttRepository.deleteByPostTypeId(postTypeId);
    }

    @Override
    public boolean existsTranslation(Integer postTypeId, String languageCode) {
        return pttRepository.existsByPostTypeIdAndLanguageCode(postTypeId, languageCode);
    }
}