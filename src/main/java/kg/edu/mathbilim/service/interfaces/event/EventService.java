package kg.edu.mathbilim.service.interfaces.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService extends BaseTranslatableService<EventDto, EventTranslationDto> {
    EventDto create(CreateEventDto createEventDto);

    void reject(Long id, String email);

    DisplayEventDto getDisplayEventById(Long id);

    Page<EventDto> getEventsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);

    void approve(Long id, String email);

    Page<EventDto> getContentByCreatorIdEvent(Long id, Pageable pageable, String query);

    Page<EventDto> getHistoryEvent(Long creatorId, Pageable pageable, String query, String status);

    Long countEventForModeration();

    Page<EventDto> getEventsForModeration(Pageable pageable, String query);

    Page<EventDto> getAllEvent(String type, String sort, Pageable pageable);
}
