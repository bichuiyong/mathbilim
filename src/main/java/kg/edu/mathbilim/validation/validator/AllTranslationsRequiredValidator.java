package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import org.springframework.util.StringUtils;

import java.util.List;

public class AllTranslationsRequiredValidator
        implements ConstraintValidator<AllTranslationsRequired, List> {

    @Override
    public boolean isValid(List translations, ConstraintValidatorContext context) {
        if (translations == null || translations.isEmpty()) {
            return false;
        }

        return translations.stream()
                .allMatch(item -> {
                    if (!(item instanceof TypeTranslationDto translation)) {
                        return false;
                    }
                    return StringUtils.hasText(translation.getLanguageCode()) &&
                            StringUtils.hasText(translation.getTranslation());
                });
    }
}
