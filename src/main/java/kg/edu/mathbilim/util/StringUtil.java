package kg.edu.mathbilim.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class StringUtil {

    public String normalizeField(String field, boolean capitalize) {
        if (field == null || field.isBlank()) {
            return null;
        }

        String normalized = field.trim().toLowerCase();
        return capitalize ? StringUtils.capitalize(normalized) : normalized;
    }

}
