package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.DateTimeRangeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateTimeRange {
    String message() default "Время начала должно быть позже времени конца";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startDateTimeField();

    String endDateTimeField();
}