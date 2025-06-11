package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.config.S3Config;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.exception.iae.FileValidationException;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.mapper.FileMapper;
import kg.edu.mathbilim.model.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Post;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.repository.FileRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.S3Service;
import kg.edu.mathbilim.util.FileUtil;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final S3Service s3Service;
    private final S3Config s3Config;
    private final Tika tika = new Tika();

    @Override
    public File getEntityById(Long id) {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    @Override
    public FileDto getById(Long id) {
        return fileMapper.toDto(getEntityById(id));
    }

    @Override
    public boolean existsById(Long fileId) {
        return fileRepository.existsById(fileId);
    }

    @Override
    public Page<FileDto> getFilePage(String query,
                                     int page, int size,
                                     String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (StringUtils.hasText(query)) {
            return getPage(() -> fileRepository.findByQuery(query, pageable));
        }
        return getPage(() -> fileRepository.findAll(pageable));
    }

    @Override
    public Page<FileDto> getUserFiles(User user, String query,
                                      int page, int size,
                                      String sortBy, String sortDirection) {
        String userEmail = user.getEmail();
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (StringUtils.hasText(userEmail)) {
            return getPage(() -> fileRepository.findByUserWithQuery(userEmail, query, pageable));
        }
        return getPage(() -> fileRepository.findByUser_Email(userEmail, pageable));
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

    @Transactional
    @Override
    public Set<File> uploadFilesForPost(MultipartFile[] files, Post post, User user) {
        Set<File> uploadedFiles = new LinkedHashSet<>();
        String context = "posts/" + post.getSlug();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File uploadedFile = uploadFileReturnEntity(file, context, user);
                uploadedFile.setPosts(new LinkedHashSet<>(Collections.singleton(post)));
                fileRepository.saveAndFlush(uploadedFile);
                uploadedFiles.add(uploadedFile);
                log.info("File uploaded for post {}: {}", post.getSlug(), file.getOriginalFilename());
            }
        }

        return uploadedFiles;
    }

    @Transactional
    @Override
    public Set<File> uploadFilesForEvent(MultipartFile[] files, Event event, User user) {
        Set<File> uploadedFiles = new LinkedHashSet<>();
        String context = "events/" + event.getId();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File uploadedFile = uploadFileReturnEntity(file, context, user);
                uploadedFile.setEvents(new LinkedHashSet<>(Collections.singleton(event)));
                fileRepository.saveAndFlush(uploadedFile);
                uploadedFiles.add(uploadedFile);
                log.info("File uploaded for event {}: {}", event.getId(), file.getOriginalFilename());
            }
        }

        return uploadedFiles;
    }

    @Transactional
    @Override
    public FileDto uploadAvatar(MultipartFile avatarFile, User user) {
        return uploadFile(avatarFile, "avatars", user);
    }

    /// ДЛЯ РАБОТЫ С S3

    @Transactional
    @Override
    public File uploadFileReturnEntity(MultipartFile multipartFile, String context, User user) {
        try {
            log.info("Starting file upload for user: {}", user.getEmail());

            FileUtil.validateFile(multipartFile);

            String mimeType = tika.detect(multipartFile.getInputStream());
            FileType fileType = FileType.determineFileType(mimeType, multipartFile.getOriginalFilename());

            String s3Key = FileUtil.buildS3Key(multipartFile.getOriginalFilename(), context, fileType, s3Config);
            s3Service.uploadFile(multipartFile, s3Key, mimeType);

            File file = createFileEntity(multipartFile, s3Key, fileType, user);
            fileRepository.save(file);
            log.info("File uploaded successfully: {} (type: {})", s3Key, fileType.getName());

            return file;
        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new FileValidationException("Ошибка загрузки файла");
        }
    }

    @Transactional
    @Override
    public FileDto uploadFile(MultipartFile multipartFile, String context, User user) {
        return fileMapper.toDto(uploadFileReturnEntity(multipartFile, context, user));
    }

    @Transactional
    @Override
    public FileDto updateFile(Long fileId, MultipartFile newFile) {
        try {
            File existingFile = getEntityById(fileId);

            FileUtil.validateFile(newFile);
            String mimeType = tika.detect(newFile.getInputStream());
            FileType fileType = FileType.determineFileType(mimeType, newFile.getOriginalFilename());

            s3Service.deleteFile(existingFile.getFilePath());

            String s3Key = FileUtil.buildS3Key(newFile.getOriginalFilename(), "general", fileType, s3Config);
            s3Service.uploadFile(newFile, s3Key, mimeType);

            existingFile.setFilename(newFile.getOriginalFilename());
            existingFile.setFilePath(s3Key);
            existingFile.setType(fileType);
            existingFile.setSize(newFile.getSize());
            existingFile.setS3Link(s3Config.getBaseUrl() + "/" + s3Key);
            existingFile.setUpdatedAt(Instant.now());

            fileRepository.save(existingFile);
            log.info("File updated successfully: {}", fileId);
            return fileMapper.toDto(existingFile);

        } catch (IOException e) {
            log.error("Error updating file: {}", e.getMessage());
            throw new FileValidationException("Ошибка обновления файла");
        }
    }

    @Transactional
    @Override
    public void deleteFile(Long fileId) {
        File file = getEntityById(fileId);
        s3Service.deleteFile(file.getFilePath());
        fileRepository.delete(file);
        log.info("File deleted successfully: {}", fileId);
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        File file = getEntityById(fileId);
        try {
            return s3Service.downloadFile(file.getFilePath());
        } catch (IOException e) {
            log.error("Error downloading file: {}", e.getMessage());
            throw new FileValidationException("Ошибка скачивания файла");
        }
    }

    private File createFileEntity(MultipartFile multipartFile, String s3Key, FileType fileType, User user) {
        Instant now = Instant.now();
        return File.builder()
                .filename(multipartFile.getOriginalFilename())
                .filePath(s3Key)
                .type(fileType)
                .user(user)
                .size(multipartFile.getSize())
                .s3Link(s3Config.getBaseUrl() + "/" + s3Key)
                .status(ContentStatus.PENDING_REVIEW)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
