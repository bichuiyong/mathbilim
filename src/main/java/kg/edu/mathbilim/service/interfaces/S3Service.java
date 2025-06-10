package kg.edu.mathbilim.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    void uploadFile(MultipartFile file, String s3Key, String mimeType) throws IOException;

    byte[] downloadFile(String s3Key) throws IOException;

    void deleteFile(String s3Key);
}
