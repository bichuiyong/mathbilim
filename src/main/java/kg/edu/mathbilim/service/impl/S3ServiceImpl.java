package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.config.S3Config;
import kg.edu.mathbilim.service.interfaces.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    
    private final S3Client s3Client;
    private final S3Config s3Config;

    @Override
    public void uploadFile(MultipartFile file, String s3Key, String mimeType) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(s3Key)
                .contentType(mimeType)
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        log.info("File uploaded to S3: {}", s3Key);
    }

    @Override
    public byte[] downloadFile(String s3Key) throws IOException {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(s3Key)
                .build();

        byte[] fileContent = s3Client.getObject(request).readAllBytes();
        log.info("File downloaded from S3: {}", s3Key);
        return fileContent;
    }

    @Override
    public void deleteFile(String s3Key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(s3Key)
                .build();
        s3Client.deleteObject(request);
        log.info("File deleted from S3: {}", s3Key);
    }

    @Override
    public InputStream downloadFileStream(String s3Key) throws IOException {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(s3Key)
                .build();
        return s3Client.getObject(request);
    }
}
