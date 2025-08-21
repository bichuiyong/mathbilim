package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import org.springframework.web.multipart.MultipartFile;

public class AllowedExtensionValidator implements ConstraintValidator<AllowedExtension, MultipartFile> {

    private String[] allowedExtensions;

    @Override
    public void initialize(AllowedExtension constraintAnnotation) {
        this.allowedExtensions = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            return false;
        }

        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        for (String allowed : allowedExtensions) {
            if (extension.equalsIgnoreCase(allowed)) {
                return true;
            }
        }
        return false;
    }
}