package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.mapper.reference.CategoryMapper;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import kg.edu.mathbilim.repository.reference.CategoryRepository;
import kg.edu.mathbilim.repository.reference.CategoryTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeContentService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends AbstractTypeContentService<
        Category,
        CategoryDto,
        CategoryTranslation,
        CategoryTranslationDto,
        CategoryRepository,
        CategoryTranslationRepository,
        CategoryMapper>   implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository repository,
                               CategoryTranslationRepository translationRepository,
                               CategoryMapper mapper, CategoryRepository categoryRepository) {
        super(repository, translationRepository, mapper);
        this.categoryRepository = categoryRepository;
        this.categoryMapper = mapper;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return getAll();
    }

    @Override
    public Category getCategoryEntity(Integer id) {
        return getEntity(id);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        return getByIdOrThrow(id);
    }

    @Override
    public List<CategoryDto> getCategoriesByLanguage(String languageCode) {
        return getByLanguage(languageCode);
    }

    @Transactional
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return create(categoryDto);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        return update(id, categoryDto);
    }

    @Transactional
    @Override
    public void deleteCategory(Integer id) {
        delete(id);
    }

    @Transactional
    @Override
    public CategoryDto addTranslation(Integer categoryId, String languageCode, String translation) {
        return addTranslation(categoryId, languageCode, translation);
    }

    @Transactional
    @Override
    public CategoryDto removeTranslation(Integer categoryId, String languageCode) {
        return removeTranslation(categoryId, languageCode);
    }

    @Override
    public List<CategoryDto> getAllCategoriesByQuery(String name, String lang) {
        return categoryRepository.findAllByQuery(
                lang,
                name).stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    protected Category createNewEntity() {
        return new Category();
    }

    @Override
    protected CategoryTranslationDto createTranslationDto(Integer typeId, String languageCode, String translation) {
        return CategoryTranslationDto.builder()
                .typeId(typeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();
    }

    @Override
    protected void setTypeIdInTranslation(CategoryTranslationDto translationDto, Integer typeId) {
        translationDto.setTypeId(typeId);
    }
}