package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collection;

public class AllowedExtensionValidator implements ConstraintValidator<AllowedExtension, Object> {

    private String[] allowedExtensions;

    @Override
    public void initialize(AllowedExtension constraintAnnotation) {
        this.allowedExtensions = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        if (value == null) {
//            return true; // null пропускаем
//        }

        if (value instanceof MultipartFile file) {
            return isFileValid(file);
        }

        if (value instanceof MultipartFile[] files) {
            return Arrays.stream(files).allMatch(this::isFileValid);
        }

        if (value instanceof Collection<?> collection) {
            return collection.stream()
                    .filter(MultipartFile.class::isInstance)
                    .map(f -> (MultipartFile) f)
                    .allMatch(this::isFileValid);
        }

        return false;
    }

    private boolean isFileValid(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return true; // пустые пропускаем
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            return false;
        }

        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return Arrays.stream(allowedExtensions)
                .anyMatch(ext -> ext.equalsIgnoreCase(extension));
    }
}