package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.config.S3Config;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.reference.types.FileTypeDto;
import kg.edu.mathbilim.exception.iae.FileValidationException;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.mapper.FileMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.model.reference.types.FileType;
import kg.edu.mathbilim.repository.FileRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.types.FileTypeService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    private final FileTypeService fileTypeService;
    private final S3Client s3Client;
    private final S3Config s3Config;
    private final Tika tika = new Tika();

    private File getEntityById(Long id) {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    @Override
    public FileDto getById(Long id) {
        return fileMapper.toDto(getEntityById(id));
    }

    @Override
    public Page<FileDto> getFilePage(String query,
                                     int page, int size,
                                     String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> fileRepository.findAll(pageable));
        }
        return getPage(() -> fileRepository.findByQuery(query, pageable));
    }

    @Override
    public Page<FileDto> getUserFiles(User user, String query,
                                      int page, int size,
                                      String sortBy, String sortDirection) {
        String userEmail = user.getEmail();
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> fileRepository.findByUser_Email(userEmail, pageable));
        }
        return getPage(() -> fileRepository.findByUserWithQuery(userEmail, query, pageable));
    }

    @Override
    public void delete(Long id) {
        fileRepository.deleteById(id);
        log.info("Deleted file: {}", id);
    }

    private Page<FileDto> getPage(Supplier<Page<File>> supplier, String notFoundMessage) {
        Page<File> filePage = supplier.get();
        if (filePage.isEmpty()) {
            throw new FileNotFoundException(notFoundMessage);
        }
        log.info("Получено {} файлов на странице", filePage.getSize());
        return filePage.map(fileMapper::toDto);
    }

    private Page<FileDto> getPage(Supplier<Page<File>> supplier) {
        return getPage(supplier, "Файлы не были найдены");
    }


    /// ДЛЯ РАБОТЫ С S3

    @Transactional
    @Override
    public FileDto uploadFile(MultipartFile multipartFile, String context, User user) {
        try {
            log.info("Starting upload file");

            validateFile(multipartFile);

            String detectedMimeType = tika.detect(multipartFile.getInputStream());
            FileType fileType = fileTypeService.determineFileTypeEntity(detectedMimeType, multipartFile.getOriginalFilename());
            validateFileSize(multipartFile.getSize(), fileType.getMimeType());

            String uniqueFileName = generateUniqueFileName(multipartFile.getOriginalFilename());

            String category = fileTypeService.getFileCategory(fileType.getMimeType());
            String folder = s3Config.getFolder(context, category);
            String s3key = folder + uniqueFileName;

            uploadToS3(multipartFile, s3key, detectedMimeType);

            File file = File.builder()
                    .filename(multipartFile.getOriginalFilename())
                    .filePath(s3key)
                    .type(fileType)
                    .user(user)
                    .size(multipartFile.getSize())
                    .s3Link(s3Config.getBaseUrl() + "/" + s3key)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

            fileRepository.save(file);
            log.info("File uploaded successfully");
            return fileMapper.toDto(file);
        } catch (IOException e) {
            log.error("Error uploading file {}", e.getMessage());
            throw new FileValidationException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public FileDto updateFile(Long fileId, MultipartFile newFile) {
        FileDto existingFile = getById(fileId);
        try {
            validateFile(newFile);
            String detectedMimeType = tika.detect(newFile.getInputStream());
            FileTypeDto fileTypeDto = fileTypeService.determineFileType(detectedMimeType, newFile.getOriginalFilename());
            validateFileSize(newFile.getSize(), fileTypeDto.getMimeType());

            deleteFromS3(existingFile.getFilePath());

            String uniqueFileName = generateUniqueFileName(newFile.getOriginalFilename());
            String category = fileTypeService.getFileCategory(fileTypeDto.getMimeType());
            String folder = s3Config.getFolder("general", category);
            String s3Key = folder + uniqueFileName;

            uploadToS3(newFile, s3Key, detectedMimeType);

            existingFile.setFilename(newFile.getOriginalFilename());
            existingFile.setFilePath(s3Key);
            existingFile.setType(fileTypeDto);
            existingFile.setSize(newFile.getSize());
            existingFile.setS3Link(s3Config.getBaseUrl() + "/" + s3Key);
            existingFile.setUpdatedAt(Instant.now());

            File file = fileMapper.toEntity(existingFile);
            fileRepository.save(file);
            log.info("File updated successfully");
            return existingFile;
        } catch (IOException e) {
            log.error("Error updating file {}", e.getMessage());
            throw new FileValidationException(e.getMessage());
        }
    }


    @Transactional
    @Override
    public void deleteFile(Long fileId) {
        File file = getEntityById(fileId);
        deleteFromS3(file.getFilePath());
        fileRepository.delete(file);
        log.info("File deleted successfully");
    }

    @Override
    public byte[] dowloadFile(Long fileId) {
        File file = getEntityById(fileId);
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(file.getFilePath())
                    .build();

            return s3Client.getObject(getObjectRequest).readAllBytes();
        } catch (IOException e) {
            log.error("Error downloading file {}", e.getMessage());
            throw new FileValidationException(e.getMessage());
        }
    }

    @Override
    public boolean existsById(Long fileId) {
        return fileRepository.existsById(fileId);
    }

    private void deleteFromS3(String s3Key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(s3Key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        log.info("Deleted file from s3 bucket {}", s3Key);
    }

    private void uploadToS3(MultipartFile multipartFile, String s3key, String detectedMimeType) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(s3key)
                .contentType(detectedMimeType)
                .contentLength(multipartFile.getSize())
                .build();


        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
        log.info("Upload file to s3: {}", s3key);
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString().replace("-", "") + originalFilename;
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("Файл не может быть пустым");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new FileValidationException("Имя файла не может быть пустым");
        }
    }

    private void validateFileSize(long fileSize, String mimeType) {
        String category = fileTypeService.getFileCategory(mimeType);
        String maxSizeStr = s3Config.getMaxSize(category);

        if (maxSizeStr != null) {
            long maxSize = parseSize(maxSizeStr);
            if (fileSize > maxSize) {
                throw new FileValidationException(
                        String.format("Размер файла превышает максимально допустимый размер для типа %s: %s",
                                category, maxSizeStr));
            }
        }
    }

    private long parseSize(String sizeStr) {
        if (sizeStr.endsWith("MB")) {
            return Long.parseLong(sizeStr.substring(0, sizeStr.length() - 2)) * 1024 * 1024;
        } else if (sizeStr.endsWith("KB")) {
            return Long.parseLong(sizeStr.substring(0, sizeStr.length() - 2)) * 1024;
        }
        return Long.parseLong(sizeStr);
    }
}
