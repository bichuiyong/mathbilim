package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.config.S3Config;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.exception.iae.FileValidationException;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.mapper.FileMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.FileRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.S3Service;
import kg.edu.mathbilim.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    @Transactional
    @Override
    public List<File> uploadFilesForPost(MultipartFile[] files, Post post) {
        List<File> uploadedFiles = new LinkedList<>();
        String context = "posts/";

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File uploadedFile = uploadFileReturnEntity(file, context);
                uploadedFile.setPosts(new LinkedList<>(Collections.singleton(post)));
                fileRepository.saveAndFlush(uploadedFile);
                uploadedFiles.add(uploadedFile);
                log.info("File uploaded for post {}: {}", post.getId(), file.getOriginalFilename());
            }
        }

        return uploadedFiles;
    }

    @Transactional
    @Override
    public List<File> uploadFilesForEvent(MultipartFile[] files, Event event) {
        List<File> uploadedFiles = new LinkedList<>();
        String context = "events/" + event.getId();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File uploadedFile = uploadFileReturnEntity(file, context);
                uploadedFile.setEvents(new LinkedList<>(Collections.singleton(event)));
                fileRepository.saveAndFlush(uploadedFile);
                uploadedFiles.add(uploadedFile);
                log.info("File uploaded for event {}: {}", event.getId(), file.getOriginalFilename());
            }
        }

        return uploadedFiles;
    }
    @Transactional
    @Override
    public List<File> uploadFilesForNews(MultipartFile[] files, News news) {
        List<File> uploadedFiles = new LinkedList<>();
        String context = "news/";

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File uploadedFile = uploadFileReturnEntity(file, context);
                uploadedFile.setNews(new LinkedList<>(Collections.singleton(news)));
                fileRepository.saveAndFlush(uploadedFile);
                uploadedFiles.add(uploadedFile);
                log.info("File uploaded for news {}", news.getId());
            }
        }

        return uploadedFiles;
    }

    @Transactional
    @Override
    public FileDto uploadAvatar(MultipartFile avatarFile) {
        return uploadFile(avatarFile, "avatars");
    }

    @Transactional
    @Override
    public File uploadAvatarReturnEntity(MultipartFile avatarFile){
        return uploadFileReturnEntity(avatarFile, "avatars");
    }

    /// ДЛЯ РАБОТЫ С S3

    @Transactional
    @Override
    public File uploadFileReturnEntity(MultipartFile multipartFile, String context) {
        try {
            FileUtil.validateFile(multipartFile);

            String mimeType = tika.detect(multipartFile.getInputStream());
            FileType fileType = FileType.determineFileType(mimeType, multipartFile.getOriginalFilename());

            String s3Key = FileUtil.buildS3Key(multipartFile.getOriginalFilename(), context, fileType, s3Config);
            s3Service.uploadFile(multipartFile, s3Key, mimeType);

            File file = createFileEntity(multipartFile, s3Key, fileType);
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
    public FileDto uploadFile(MultipartFile multipartFile, String context) {
        return fileMapper.toDto(uploadFileReturnEntity(multipartFile, context));
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

    @Override
    public InputStream downloadFileStream(Long fileId) {
        File file = getEntityById(fileId);
        try{
           return s3Service.downloadFileStream(file.getFilePath());
        }catch (IOException e){
            throw new FileValidationException("Ошибка скачивания файла");
        }
    }

    private File createFileEntity(MultipartFile multipartFile, String s3Key, FileType fileType) {
        return File.builder()
                .filename(multipartFile.getOriginalFilename())
                .filePath(s3Key)
                .type(fileType)
                .size(multipartFile.getSize())
                .s3Link(s3Config.getBaseUrl() + "/" + s3Key)
                .build();
    }
}
