package kg.edu.mathbilim.service.interfaces;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.EventDto;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {
    void create(@Valid EventDto event, MultipartFile mainImage, MultipartFile[] attachments);
}
