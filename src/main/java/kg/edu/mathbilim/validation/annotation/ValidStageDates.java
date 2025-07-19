package kg.edu.mathbilim.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.edu.mathbilim.validation.validator.ValidStageDatesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStageDatesValidator.class)
public @interface ValidStageDates {
    String message() default "Даты этапов должны находиться в диапазоне дат олимпиады (с {startDate} по {endDate}).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}