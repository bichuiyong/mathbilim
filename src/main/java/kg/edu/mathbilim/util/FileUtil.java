package kg.edu.mathbilim.util;

import kg.edu.mathbilim.config.S3Config;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.exception.iae.FileValidationException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UtilityClass
public class FileUtil {

    public String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString().replace("-", "") + "_" + originalFilename;
    }

    public String buildS3Key(String originalFilename, String context, FileType fileType, S3Config s3Config) {
        String uniqueFileName = generateUniqueFileName(originalFilename);
        String category = fileType.getCategoryName();
        String folder = s3Config.getFolder(context, category);
        return folder + uniqueFileName;
    }

    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("Файл не может быть пустым");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new FileValidationException("Имя файла не может быть пустым");
        }
    }
}
