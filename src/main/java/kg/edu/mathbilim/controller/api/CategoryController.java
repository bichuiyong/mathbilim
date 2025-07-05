package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("restCategories")
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDto category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDto category, Integer categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, category));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
