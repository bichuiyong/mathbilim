package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslation;
import org.springframework.util.StringUtils;

import java.util.List;

public class AtLeastOneTranslationValidator implements ConstraintValidator<AtLeastOneTranslation, List<EventTranslationDto>> {

    @Override
    public boolean isValid(List<EventTranslationDto> translations, ConstraintValidatorContext context) {
        if (translations == null || translations.isEmpty()) {
            return false;
        }

        return translations.stream()
                .anyMatch(translation ->
                    StringUtils.hasText(translation.getTitle()) &&
                    StringUtils.hasText(translation.getContent())
                );
    }
}