package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.FileDto;
import org.springframework.data.domain.Page;

public interface FileService {
    FileDto getById(Long id);

    Page<FileDto> getFilePage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);
}
