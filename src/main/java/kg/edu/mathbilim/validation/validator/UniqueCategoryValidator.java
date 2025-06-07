package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.service.interfaces.CategoryService;
import kg.edu.mathbilim.validation.annotation.UniqueCategory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {
    CategoryService categoryService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name  != null && !categoryService.existsByName(name);
    }
}
