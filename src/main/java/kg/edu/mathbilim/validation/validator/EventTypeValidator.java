package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.service.interfaces.EventTypeService;
import kg.edu.mathbilim.validation.annotation.EventType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventTypeValidator implements ConstraintValidator<EventType, String> {
    private final EventTypeService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && userService.existsByEventType(email);
    }
}
