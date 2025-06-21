package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.reference.category.CategoryTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.reference.category.CategoryTranslationMapper;
import kg.edu.mathbilim.model.reference.category.CategoryTranslation;
import kg.edu.mathbilim.repository.reference.CategoryTranslationRepository;
import kg.edu.mathbilim.service.interfaces.reference.CategoryTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryTranslationServiceImpl implements CategoryTranslationService {
    private final CategoryTranslationRepository ctRepository;
    private final CategoryTranslationMapper ctMapper;

    @Override
    public List<CategoryTranslationDto> getTranslationsByCategoryId(Integer categoryId) {
        return ctRepository.findByCategoryId(categoryId)
                .stream()
                .map(ctMapper::toDto)
                .toList();
    }

    @Override
    public CategoryTranslation getTranslationEntity(Integer categoryId, String languageCode) {
        return ctRepository.findByCategoryIdAndLanguageCode(categoryId, languageCode)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этой категории не был найден"));
    }

    @Override
    public CategoryTranslationDto getTranslation(Integer categoryId, String languageCode) {
        return ctMapper.toDto(getTranslationEntity(categoryId, languageCode));
    }

    @Override
    public List<CategoryTranslationDto> getTranslationsByLanguage(String languageCode) {
        return ctRepository.findByLanguageCode(languageCode).stream()
                .map(ctMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CategoryTranslationDto createTranslation(CategoryTranslationDto dto) {
        CategoryTranslation translation = ctMapper.toEntity(dto);
        ctRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTranslation());
        return dto;
    }

    @Override
    @Transactional
    public CategoryTranslationDto updateTranslation(Integer categoryId, String languageCode, String newTranslation) {
        CategoryTranslation translation = getTranslationEntity(categoryId, languageCode);
        translation.setTranslation(newTranslation);
        CategoryTranslation saved = ctRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, newTranslation);
        return ctMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryTranslationDto upsertTranslation(CategoryTranslationDto dto) {
        return updateTranslation(dto.getCategoryId(), dto.getLanguageCode(), dto.getTranslation());
    }

    @Override
    @Transactional
    public void deleteTranslation(Integer categoryId, String languageCode) {
        CategoryTranslationId id = new CategoryTranslationId();
        id.setCategoryId(categoryId);
        id.setLanguageCode(languageCode);
        ctRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllTranslationsByCategoryId(Integer categoryId) {
        ctRepository.deleteByCategoryId(categoryId);
    }

    @Override
    public boolean existsTranslation(Integer categoryId, String languageCode) {
        return ctRepository.existsByCategoryIdAndLanguageCode(categoryId, languageCode);
    }
}
