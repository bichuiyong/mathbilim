package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.OrganizationDto;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationService {
    OrganizationDto create(OrganizationDto dto, MultipartFile avatarFile);
}
