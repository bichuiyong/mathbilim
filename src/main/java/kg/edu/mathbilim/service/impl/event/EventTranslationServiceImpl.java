package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.mapper.event.EventTranslationMapper;
import kg.edu.mathbilim.model.event.EventTranslation;
import kg.edu.mathbilim.model.event.EventTranslationId;
import kg.edu.mathbilim.repository.event.EventTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslationService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import org.springframework.stereotype.Service;

@Service
public class EventTranslationServiceImpl extends
        AbstractTranslationService<
                EventTranslationDto,
                EventTranslation,
                EventTranslationId,
                EventTranslationRepository,
                EventTranslationMapper
                >
        implements EventTranslationService {

    public EventTranslationServiceImpl(EventTranslationRepository repository, EventTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected EventTranslationId createTranslationId(Long entityId, String languageCode) {
        EventTranslationId id = new EventTranslationId();
        id.setEventId(entityId);
        id.setLanguageCode(languageCode);
        return id;
    }

    @Override
    protected void setEntityId(EventTranslationDto dto, Long entityId) {
        dto.setEventId(entityId);
    }

    @Override
    protected String getEntityName() {
        return "event";
    }

    @Override
    protected Long getEntityIdFromDto(EventTranslationDto dto) {
        return dto.getEventId();
    }

    @Override
    protected void deleteAllTranslationsByEntityIdImpl(Long entityId) {
        repository.deleteByEventId(entityId);
    }
}