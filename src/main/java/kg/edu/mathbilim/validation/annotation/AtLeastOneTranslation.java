package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.AtLeastOneTranslationValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneTranslationValidator.class)
public @interface AtLeastOneTranslation {
    String message() default "Должен быть заполнен хотя бы один перевод";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}