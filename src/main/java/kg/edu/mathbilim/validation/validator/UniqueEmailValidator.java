package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.validation.annotation.UniqueEmail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !userService.existsByEmail(email);
    }
}
