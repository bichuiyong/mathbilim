package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.validation.annotation.ValidDateTimeRange;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDateTime;

public class DateTimeRangeValidator implements ConstraintValidator<ValidDateTimeRange, Object> {
    private String startDateTimeField;
    private String endDateTimeField;
    private String message;

    @Override
    public void initialize(ValidDateTimeRange constraintAnnotation) {
        startDateTimeField = constraintAnnotation.startDateTimeField();
        endDateTimeField = constraintAnnotation.endDateTimeField();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        LocalDateTime startDateTime = (LocalDateTime) wrapper.getPropertyValue(startDateTimeField);
        LocalDateTime endDateTime = (LocalDateTime) wrapper.getPropertyValue(endDateTimeField);

        if (startDateTime == null || endDateTime == null) {
            return true;
        }

        boolean isValid = startDateTime.isBefore(endDateTime);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(endDateTimeField)
                    .addConstraintViolation();
        }

        return isValid;
    }
}