package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.mapper.event.EventTypeTranslationMapper;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.repository.event.EventTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeTranslationService;
import kg.edu.mathbilim.service.interfaces.event.EventTypeTranslationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeTranslationServiceImpl extends AbstractTypeTranslationService<
        EventTypeTranslation,
        EventTypeTranslationDto,
        EventTypeTranslationRepository,
        EventTypeTranslationMapper> implements EventTypeTranslationService {

    public EventTypeTranslationServiceImpl(EventTypeTranslationRepository repository,
                                           EventTypeTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getNotFoundMessage() {
        return "Перевод для этого типа события не был найден";
    }

    @Override
    public List<EventTypeTranslationDto> getTranslationsByEventTypeId(Integer eventTypeId) {
        return super.getTranslationsByTypeId(eventTypeId);
    }

    @Override
    public void deleteAllTranslationsByEventTypeId(Integer eventTypeId) {
        super.deleteAllTranslationsByTypeId(eventTypeId);
    }
}