package kg.edu.mathbilim.util;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import kg.edu.mathbilim.enums.Language;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class TranslationUtil {
    
    public static <T extends ContentTranslationDto> List<T> createDefaultTranslations(
            Function<String, T> translationBuilder) {
        return Arrays.stream(Language.values())
                .map(lang -> {
                    T translation = translationBuilder.apply(lang.getCode());
                    translation.setTitle("");
                    translation.setContent("");
                    return translation;
                })
                .collect(Collectors.toList());
    }
}