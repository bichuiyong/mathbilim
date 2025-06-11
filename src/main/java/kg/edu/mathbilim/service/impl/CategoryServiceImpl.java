package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.CategoryDto;
import kg.edu.mathbilim.mapper.CategoryMapper;
import kg.edu.mathbilim.model.Category;
import kg.edu.mathbilim.repository.CategoryRepository;
import kg.edu.mathbilim.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getCategoryByName(String name) {

         Category category = categoryRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("Category with name " + name + " not found"));
         return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public boolean existsByCategory(String email) {
        return categoryRepository.findByName(email).isPresent();
    }

    @Override
    public CategoryDto createCategory(CategoryDto category) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public void deleteCategory(Integer category) {
        Category category1 = categoryRepository.findById(category).orElseThrow(()-> new NoSuchElementException("Category with id " + category + " not found"));
        categoryRepository.delete(category1);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto category) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public CategoryDto getCategoryById(Integer category) {
        return categoryMapper.toDto(categoryRepository.findById(category).orElseThrow(()-> new NoSuchElementException("Category with id " + category + " not found")));
    }
}
