package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import org.springframework.util.StringUtils;

import java.util.List;

public class AtLeastOneTranslationRequiredValidator implements ConstraintValidator<AtLeastOneTranslationRequired, List<? extends ContentTranslationDto>> {
    @Override
    public boolean isValid(List<? extends ContentTranslationDto> translations, ConstraintValidatorContext constraintValidatorContext) {
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