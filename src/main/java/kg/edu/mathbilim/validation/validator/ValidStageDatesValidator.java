package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageCreateDto;
import kg.edu.mathbilim.validation.annotation.ValidStageDates;

import java.time.LocalDate;
import java.util.List;

public class ValidStageDatesValidator implements ConstraintValidator<ValidStageDates, OlympiadCreateDto> {

    @Override
    public void initialize(ValidStageDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(OlympiadCreateDto olympiad, ConstraintValidatorContext context) {
        if (olympiad == null) {
            return true;
        }

        LocalDate olympiadStartDate = olympiad.getStartDate();
        LocalDate olympiadEndDate = olympiad.getEndDate();
        List<OlympiadStageCreateDto> stages = olympiad.getStages();

        if (olympiadStartDate == null || olympiadEndDate == null || stages == null) {
            return true;
        }

        if (olympiadStartDate.isAfter(olympiadEndDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Дата начала олимпиады должна быть раньше даты окончания.")
                    .addPropertyNode("startDate").addConstraintViolation();
            return false;
        }

        for (OlympiadStageCreateDto stage : stages) {
            if (stage == null) continue;

            if (!isDateInRange(stage.getEventStartDate(), olympiadStartDate, olympiadEndDate) ||
                    !isDateInRange(stage.getEventEndDate(), olympiadStartDate, olympiadEndDate) ||
                    !isDateInRange(stage.getRegistrationStart(), olympiadStartDate, olympiadEndDate) ||
                    !isDateInRange(stage.getRegistrationEnd(), olympiadStartDate, olympiadEndDate)) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Даты этапов должны быть в диапазоне дат олимпиады.")
                        .addPropertyNode("stages").addConstraintViolation();
                return false;
            }

            if (stage.getEventStartDate() != null && stage.getEventEndDate() != null &&
                    stage.getEventStartDate().isAfter(stage.getEventEndDate())) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Дата начала этапа должна быть раньше даты окончания.")
                        .addPropertyNode("stages").addConstraintViolation();
                return false;
            }

            if (stage.getRegistrationStart() != null && stage.getRegistrationEnd() != null &&
                    stage.getRegistrationStart().isAfter(stage.getRegistrationEnd())) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Дата начала регистрации этапа должна быть раньше даты окончания.")
                        .addPropertyNode("stages").addConstraintViolation();
                return false;
            }
        }

        return true;
    }

    private boolean isDateInRange(LocalDate date, LocalDate start, LocalDate end) {
        return date != null && !date.isBefore(start) && !date.isAfter(end);
    }
}
