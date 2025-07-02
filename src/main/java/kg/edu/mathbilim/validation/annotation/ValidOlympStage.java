package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.OlympStageValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OlympStageValidator.class)
@Documented
public @interface ValidOlympStage {
    String message() default "Некорректные диапазоны дат";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
