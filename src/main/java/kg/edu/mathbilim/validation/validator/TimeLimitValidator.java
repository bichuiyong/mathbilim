package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.test.TestCreateDto;
import kg.edu.mathbilim.validation.annotation.ValidTimeLimit;

public class TimeLimitValidator implements ConstraintValidator<ValidTimeLimit, TestCreateDto> {

    private int min;
    private int max;

    @Override
    public void initialize(ValidTimeLimit constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(TestCreateDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        if (Boolean.TRUE.equals(dto.getHasLimit())) {
            Integer timeLimit = dto.getTimeLimit();
            if (timeLimit == null) {
                return false;
            }
            return timeLimit >= min && timeLimit <= max;
        }

        return true;
    }
}
