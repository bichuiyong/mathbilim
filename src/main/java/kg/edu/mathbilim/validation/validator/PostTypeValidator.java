package kg.edu.mathbilim.validation.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.service.interfaces.PostTypeService;
import kg.edu.mathbilim.validation.annotation.PostType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostTypeValidator implements ConstraintValidator<PostType, String> {
    private final PostTypeService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && userService.existByPostType(email);
    }
}
