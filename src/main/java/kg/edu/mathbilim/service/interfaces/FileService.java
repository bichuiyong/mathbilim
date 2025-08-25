package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.enums.FileCategory;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.post.Post;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    File getEntityById(Long id);

    FileDto getById(Long id);

    boolean existsById(Long fileId);

    @Transactional
    List<File> uploadFilesForPost(MultipartFile[] files, Post post);

    @Transactional
    List<File> uploadFilesForEvent(MultipartFile[] files, Event event);

    @Transactional
    List<File> uploadFilesForNews(MultipartFile[] files, News news);

    @Transactional
    FileDto uploadAvatar(MultipartFile avatarFile);

    @Transactional
    File uploadAvatarReturnEntity(MultipartFile avatarFile);

    @Transactional
    File uploadFileReturnEntity(MultipartFile multipartFile, String context);

    @Transactional
    File uploadFileReturnEntity(MultipartFile multipartFile, String context, FileCategory expectedCategory);

    @Transactional
    FileDto uploadFile(MultipartFile multipartFile, String context);

    @Transactional
    FileDto uploadFile(MultipartFile multipartFile, String context, FileCategory expectedCategory);

    @Transactional
    FileDto updateFile(Long fileId, MultipartFile newFile);

    @Transactional
    void deleteFile(Long fileId);

    byte[] downloadFile(Long fileId);

    InputStream downloadFileStream(Long fileId);
}
