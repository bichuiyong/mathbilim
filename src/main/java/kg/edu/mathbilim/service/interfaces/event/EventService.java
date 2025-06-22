package kg.edu.mathbilim.service.interfaces.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;

public interface EventService extends BaseTranslatableService<EventDto, EventTranslationDto> {
    EventDto create(CreateEventDto createEventDto);

    DisplayEventDto getDisplayEventById(Long id);
}
