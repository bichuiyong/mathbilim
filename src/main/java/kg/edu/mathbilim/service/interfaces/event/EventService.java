package kg.edu.mathbilim.service.interfaces.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;

public interface EventService extends BaseTranslatableService<EventDto, EventTranslationDto> {
    EventDto create(CreateEventDto createEventDto);

    DisplayEventDto getDisplayEventById(Long id);

    Page<EventDto> getEventsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);

    void approve(Long id);
}
