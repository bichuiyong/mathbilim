package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.AllowedExtensionValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedExtensionValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedExtension {
    String message() default "Недопустимое расширение файла";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] value();
}
