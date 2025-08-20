package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.TimeLimitValidator;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeLimitValidator.class)
@Documented
public @interface ValidTimeLimit {
    String message() default "timeLimit должен быть между {min} и {max}, если hasLimit = true";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 1;
    int max() default 3600;
}
