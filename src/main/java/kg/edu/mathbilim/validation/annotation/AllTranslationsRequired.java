package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.AllTranslationsRequiredValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllTranslationsRequiredValidator.class)
public @interface AllTranslationsRequired {
    String message() default "Все переводы должны быть заполнены";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
