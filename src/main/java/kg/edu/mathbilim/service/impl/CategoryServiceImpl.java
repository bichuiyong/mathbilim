package kg.edu.mathbilim.service.impl;

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

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("Category with name " + name + " not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
