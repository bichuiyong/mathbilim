package kg.edu.mathbilim.util;

import kg.edu.mathbilim.config.S3Config;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.exception.iae.FileValidationException;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@UtilityClass
public class FileUtil {


    private static final String UPLOAD_DIR = "data/";

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

    @SneakyThrows
    public String saveUploadFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();
        //297f4ebe-c153-4359-abdd-b6841a5a340c_avatar_monn_volf.jpg

        Path pathDir = Paths.get(UPLOAD_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + resultFileName);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }

        return resultFileName;
    }

    @SneakyThrows
    public ResponseEntity<?> getOutputFile(String filename, String subDir, MediaType mediaType) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(UPLOAD_DIR + subDir + filename));
            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        }
    }

}
