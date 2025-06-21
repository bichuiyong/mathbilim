package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.exception.nsee.CategoryNotFoundException;
import kg.edu.mathbilim.mapper.reference.CategoryMapper;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.repository.reference.CategoryRepository;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryTranslationService categoryTranslationService;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    private Category getCategoryEntity(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        return categoryMapper.toDto(getCategoryEntity(id));
    }

    @Override
    public List<CategoryDto> getCategoriesByLanguage(String languageCode) {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryDto dto = categoryMapper.toDto(category);
                    dto.setCategoryTranslations(List.of(categoryTranslationService.getTranslation(category.getId(), languageCode)));
                    return dto;
                })
                .toList();
    }

    @Transactional
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        Category savedCategory = categoryRepository.save(category);

        CategoryDto savedDto = categoryMapper.toDto(savedCategory);

        if (categoryDto.getCategoryTranslations() != null && !categoryDto.getCategoryTranslations().isEmpty()) {
            List<CategoryTranslationDto> savedTranslations = categoryDto
                    .getCategoryTranslations()
                    .stream()
                    .map(translation -> {
                        translation.setCategoryId(savedCategory.getId());
                        return categoryTranslationService.createTranslation(translation);
                    })
                    .toList();
            savedDto.setCategoryTranslations(savedTranslations);
        }

        return savedDto;
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        CategoryDto dto = getCategoryById(id);

        if (categoryDto.getCategoryTranslations() != null) {
            categoryTranslationService.deleteAllTranslationsByCategoryId(id);

            List<CategoryTranslationDto> savedTranslations =
                    categoryDto.getCategoryTranslations()
                            .stream()
                            .map(translation -> {
                                translation.setCategoryId(id);
                                return categoryTranslationService.createTranslation(translation);
                            })
                            .toList();

            dto.setCategoryTranslations(savedTranslations);
            return dto;
        }

        return dto;
    }

    @Transactional
    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public CategoryDto addTranslation(Integer categoryId, String languageCode, String translation) {
        CategoryDto category = getCategoryById(categoryId);

        CategoryTranslationDto translationDto = CategoryTranslationDto.builder()
                .categoryId(categoryId)
                .languageCode(languageCode)
                .translation(translation)
                .build();

        categoryTranslationService.upsertTranslation(translationDto);
        category.getCategoryTranslations().add(translationDto);
        return category;
    }

    @Transactional
    @Override
    public CategoryDto removeTranslation(Integer categoryId, String languageCode) {
        getCategoryById(categoryId);
        categoryTranslationService.deleteTranslation(categoryId, languageCode);
        return getCategoryById(categoryId);
    }
}