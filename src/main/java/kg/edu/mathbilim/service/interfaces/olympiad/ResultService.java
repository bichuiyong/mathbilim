package kg.edu.mathbilim.service.interfaces.olympiad;

import org.springframework.web.multipart.MultipartFile;

public interface ResultService {
    String uploadResult(MultipartFile uploadFile, long stageId);
}
