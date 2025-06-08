package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.exception.nsee.CategoryNotFoundException;
import kg.edu.mathbilim.mapper.reference.CategoryMapper;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.repository.reference.CategoryRepository;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> cities = categoryRepository.findAll();
        if (cities.isEmpty()) {
            throw new CategoryNotFoundException("Категорий не было найдено!");
        }
        return cities.stream().map(categoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto getByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(CategoryNotFoundException::new);
        return categoryMapper.toDto(category);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    @Transactional
    @Override
    public void create(CategoryDto dto) {
        String name = dto.getName().trim().toUpperCase();
        Category category = categoryMapper.toEntity(dto);
        category.setName(name);

        categoryRepository.save(category);
        log.info("Created Category: {}", category);
    }

    @Transactional
    @Override
    public void delete(Integer id){
        categoryRepository.deleteById(id);
        log.info("Deleted Category: {}", id);
    }
}
