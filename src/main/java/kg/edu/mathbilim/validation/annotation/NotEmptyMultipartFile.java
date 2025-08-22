package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.NotEmptyMultipartFileValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyMultipartFileValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyMultipartFile {
    String message() default "Файл обязателен";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}