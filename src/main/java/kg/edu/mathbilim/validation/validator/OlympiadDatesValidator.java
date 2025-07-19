package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.validation.annotation.ValidOlympiadDates;

public class OlympiadDatesValidator implements ConstraintValidator<ValidOlympiadDates, OlympiadCreateDto> {

    @Override
    public boolean isValid(OlympiadCreateDto dto, ConstraintValidatorContext context) {
        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            return true;
        }

        return !dto.getEndDate().isBefore(dto.getStartDate());
    }
}
