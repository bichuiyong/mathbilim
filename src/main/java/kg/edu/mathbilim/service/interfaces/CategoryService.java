package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.CategoryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto getByName(String name);

    boolean existsByName(String name);


    void create(CategoryDto dto);

    @Transactional
    void delete(Integer id);
}
