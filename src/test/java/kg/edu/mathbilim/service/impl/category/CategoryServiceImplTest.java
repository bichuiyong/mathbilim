package kg.edu.mathbilim.service.impl.category;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.mapper.reference.CategoryMapper;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import kg.edu.mathbilim.repository.reference.CategoryRepository;
import kg.edu.mathbilim.repository.reference.CategoryTranslationRepository;
import kg.edu.mathbilim.service.impl.reference.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceIntegrationTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryTranslationRepository categoryTranslationRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;
    private CategoryTranslation categoryTranslation;
    private CategoryTranslationDto categoryTranslationDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1);

        categoryDto = CategoryDto.builder()
                .id(1)
                .build();

        categoryTranslation = new CategoryTranslation();

        categoryTranslationDto = CategoryTranslationDto.builder()
                .typeId(1)
                .languageCode("en")
                .translation("Mathematics")
                .build();

        category.setTranslations(Arrays.asList(categoryTranslation));
    }

    @Test
    void inheritedMethods_CallCorrectRepositoryMethods() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(categoryMapper.toDto(any(Category.class))).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.getAllCategories();

        assertFalse(result.isEmpty());
        verify(categoryRepository).findAll();
        verify(categoryMapper, times(1)).toDto(any(Category.class));
    }

    @Test
    void abstractMethods_Implementation_CreateNewEntity() {
        Category newCategory = categoryService.createNewEntity();

        assertNotNull(newCategory);
        assertInstanceOf(Category.class, newCategory);
        assertNull(newCategory.getId());
    }

    @Test
    void abstractMethods_Implementation_CreateTranslationDto() {
        Integer typeId = 5;
        String languageCode = "ky";
        String translation = "Математика";

        CategoryTranslationDto result = categoryService.createTranslationDto(typeId, languageCode, translation);

        assertNotNull(result);
        assertEquals(typeId, result.getTypeId());
        assertEquals(languageCode, result.getLanguageCode());
        assertEquals(translation, result.getTranslation());
    }

    @Test
    void abstractMethods_Implementation_SetTypeIdInTranslation() {
        CategoryTranslationDto dto = CategoryTranslationDto.builder()
                .languageCode("en")
                .translation("Test")
                .build();
        Integer newTypeId = 10;

        categoryService.setTypeIdInTranslation(dto, newTypeId);

        assertEquals(newTypeId, dto.getTypeId());
    }

    @Test
    void searchFunctionality_GetAllCategoriesByQuery() {
        String searchName = "мат";
        String language = "ru";
        List<Category> foundCategories = Arrays.asList(category);

        when(categoryRepository.findAllByQuery(searchName, language)).thenReturn(foundCategories);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.getAllCategoriesByQuery(searchName, language);

        assertEquals(1, result.size());
        verify(categoryRepository).findAllByQuery(searchName, language);
    }

    @Test
    void performance_BatchOperations() {
        List<Category> largeList = Arrays.asList(category, category, category, category, category);
        when(categoryRepository.findAll()).thenReturn(largeList);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.getAllCategories();

        assertEquals(largeList.size(), result.size());
        verify(categoryMapper, times(largeList.size())).toDto(any(Category.class));
    }

}