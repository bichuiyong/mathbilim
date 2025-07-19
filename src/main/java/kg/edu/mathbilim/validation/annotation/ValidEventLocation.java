package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.EventLocationValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventLocationValidator.class)
@Documented
public @interface ValidEventLocation {
    String message() default "Для офлайн мероприятия укажите адрес, для онлайн - URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
