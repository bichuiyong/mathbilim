package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageCreateDto;
import kg.edu.mathbilim.validation.annotation.ValidOlympStage;

import java.time.LocalDate;

public class OlympStageValidator implements ConstraintValidator<ValidOlympStage, OlympiadStageCreateDto> {

    @Override
    public boolean isValid(OlympiadStageCreateDto dto, ConstraintValidatorContext context) {
        boolean valid = true;

        LocalDate regStart = dto.getRegistrationStart();
        LocalDate regEnd = dto.getRegistrationEnd();
        LocalDate eventStart = dto.getEventStartDate();
        LocalDate eventEnd = dto.getEventEndDate();

        context.disableDefaultConstraintViolation();

        if (regStart != null && regEnd != null && regEnd.isBefore(regStart)) {
            context.buildConstraintViolationWithTemplate("Дата окончания регистрации не может быть раньше даты начала")
                    .addPropertyNode("registrationEnd")
                    .addConstraintViolation();
            valid = false;
        }

        if (eventStart != null && eventEnd != null && eventEnd.isBefore(eventStart)) {
            context.buildConstraintViolationWithTemplate("Дата окончания события не может быть раньше даты начала")
                    .addPropertyNode("eventEndDate")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}