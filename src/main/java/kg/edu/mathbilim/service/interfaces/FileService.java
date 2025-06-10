package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.model.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Post;
import kg.edu.mathbilim.model.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FileService {
    FileDto getById(Long id);

    Page<FileDto> getFilePage(String query, int page, int size, String sortBy, String sortDirection);

    Page<FileDto> getUserFiles(User user, String query,
                               int page, int size,
                               String sortBy, String sortDirection);

    @Transactional
    Set<File> uploadFilesForEvent(MultipartFile[] files, Event event, User user);

    String uploadAvatar(MultipartFile avatarFile, User user);

    @Transactional
    File uploadFileReturnEntity(MultipartFile multipartFile, String context, User user);

    @Transactional
    FileDto uploadFile(MultipartFile multipartFile, String context, User user);

    @Transactional
    Set<File> uploadFilesForPost(MultipartFile[] files, Post post, User user);

    @Transactional
    FileDto updateFile(Long fileId, MultipartFile newFile);

    @Transactional
    void deleteFile(Long fileId);

    byte[] downloadFile(Long fileId);

    boolean existsById(Long fileId);

    File getEntityById(Long id);
}
