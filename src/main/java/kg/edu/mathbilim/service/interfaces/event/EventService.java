package kg.edu.mathbilim.service.interfaces.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface EventService {
    EventDto getById(Long id);

    Page<EventDto> getEventPage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);

    @Transactional
    EventDto create(CreateEventDto createEventDto);
}
