package kg.edu.mathbilim.service.interfaces.event;

import kg.edu.mathbilim.dto.event.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    EventDto getById(Long id);

    Page<EventDto> getEventPage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);

    @Transactional
    EventDto create(EventDto eventDto, MultipartFile mainImage, MultipartFile[] attachments, List<Long> organizationIds);
}
