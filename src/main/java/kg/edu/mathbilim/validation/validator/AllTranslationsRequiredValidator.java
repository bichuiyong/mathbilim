package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.translations.TypeTranslationDto;
import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import org.springframework.util.StringUtils;

import java.util.List;

public class AllTranslationsRequiredValidator implements ConstraintValidator<AllTranslationsRequired, List<? extends TypeTranslationDto>> {
    @Override
    public boolean isValid(List<? extends TypeTranslationDto> translations, ConstraintValidatorContext constraintValidatorContext) {
        if (translations == null || translations.isEmpty()) {
            return false;
        }

        return translations.stream()
                .allMatch(translation ->
                        StringUtils.hasText(translation.getLanguageCode()) &&
                                StringUtils.hasText(translation.getTranslation())
                );
    }
}
