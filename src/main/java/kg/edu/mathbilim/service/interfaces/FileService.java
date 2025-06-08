package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.model.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileDto getById(Long id);

    Page<FileDto> getFilePage(String query, int page, int size, String sortBy, String sortDirection);

    Page<FileDto> getUserFiles(User user, String query,
                               int page, int size,
                               String sortBy, String sortDirection);

    void delete(Long id);

    @Transactional
    FileDto uploadFile(MultipartFile multipartFile, String context, User user);

    @Transactional
    FileDto updateFile(Long fileId, MultipartFile newFile);

    @Transactional
    void deleteFile(Long fileId);

    byte[] dowloadFile(Long fileId);

    boolean existsById(Long fileId);
}
