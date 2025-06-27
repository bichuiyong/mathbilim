package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.OlympiadDatesValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OlympiadDatesValidator.class)
@Documented
public @interface ValidOlympiadDates {
    String message() default "Дата окончания не может быть раньше даты начала";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
