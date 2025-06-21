package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.event.EventTypeTranslationMapper;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.repository.event.EventTypeTranslationRepository;
import kg.edu.mathbilim.service.interfaces.event.EventTypeTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventTypeTranslationServiceImpl implements EventTypeTranslationService {
    private final EventTypeTranslationRepository ettRepository;
    private final EventTypeTranslationMapper ettMapper;

    @Override
    public List<EventTypeTranslationDto> getTranslationsByEventTypeId(Integer eventTypeId) {
        return ettRepository.findByEventTypeId(eventTypeId)
                .stream()
                .map(ettMapper::toDto)
                .toList();
    }

    @Override
    public EventTypeTranslation getTranslationEntity(Integer eventTypeId, String languageCode) {
        return ettRepository.findByEventTypeIdAndLanguageCode(eventTypeId, languageCode)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этого типа события не был найден"));
    }

    @Override
    public EventTypeTranslationDto getTranslation(Integer eventTypeId, String languageCode) {
        return ettMapper.toDto(getTranslationEntity(eventTypeId, languageCode));
    }

    @Override
    public List<EventTypeTranslationDto> getTranslationsByLanguage(String languageCode) {
        return ettRepository.findByLanguageCode(languageCode).stream()
                .map(ettMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventTypeTranslationDto createTranslation(EventTypeTranslationDto dto) {
        EventTypeTranslation translation = ettMapper.toEntity(dto);
        ettRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTranslation());
        return dto;
    }

    @Override
    @Transactional
    public EventTypeTranslationDto updateTranslation(Integer eventTypeId, String languageCode, String newTranslation) {
        EventTypeTranslation translation = getTranslationEntity(eventTypeId, languageCode);
        translation.setTranslation(newTranslation);
        EventTypeTranslation saved = ettRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, newTranslation);
        return ettMapper.toDto(saved);
    }

    @Override
    @Transactional
    public EventTypeTranslationDto upsertTranslation(EventTypeTranslationDto dto) {
        return updateTranslation(dto.getEventTypeId(), dto.getLanguageCode(), dto.getTranslation());
    }

    @Override
    @Transactional
    public void deleteTranslation(Integer eventTypeId, String languageCode) {
        EventTypeTranslationId id = new EventTypeTranslationId();
        id.setEventTypeId(eventTypeId);
        id.setLanguageCode(languageCode);
        ettRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllTranslationsByEventTypeId(Integer eventTypeId) {
        ettRepository.deleteByEventTypeId(eventTypeId);
    }

    @Override
    public boolean existsTranslation(Integer eventTypeId, String languageCode) {
        return ettRepository.existsByEventTypeIdAndLanguageCode(eventTypeId, languageCode);
    }
}