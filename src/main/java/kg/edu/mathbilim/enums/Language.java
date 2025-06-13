package kg.edu.mathbilim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Language {
    RU("ru", "Русский"),
    KY("ky", "Кыргызча"),
    EN("en", "English");
    
    private final String code;
    private final String displayName;
    
    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (language.code.equals(code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language code: " + code);
    }
    
    public static boolean isValidCode(String code) {
        for (Language language : values()) {
            if (language.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, String> getLanguagesMap() {
        Map<String, String> languages = new LinkedHashMap<>();
        for (Language lang : Language.values()) {
            languages.put(lang.getCode(), lang.getDisplayName());
        }
        return languages;
    }
}