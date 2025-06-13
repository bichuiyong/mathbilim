package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.AtLeastOneTranslationRequiredValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneTranslationRequiredValidator.class)
public @interface AtLeastOneTranslationRequired {
    String message() default "Должен быть заполнен хотя бы один перевод";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}