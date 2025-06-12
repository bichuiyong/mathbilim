package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.event.EventTranslationMapper;
import kg.edu.mathbilim.model.event.EventTranslation;
import kg.edu.mathbilim.model.event.EventTranslationId;
import kg.edu.mathbilim.repository.event.EventTranslationRepository;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventTranslationServiceImpl implements EventTranslationService {
    private final EventTranslationRepository etRepository;
    private final EventTranslationMapper etMapper;

    @Override
    public List<EventTranslationDto> getTranslationsByEventId(Long eventId) {
        return etRepository.findByEventId(eventId)
                .stream()
                .map(etMapper::toDto)
                .toList();
    }

    @Override
    public EventTranslation getTranslationEntity(Long eventId, String languageCode) {
        EventTranslationId id = new EventTranslationId();
        id.setEventId(eventId);
        id.setLanguageCode(languageCode);

        return etRepository.findById(id)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этого мероприятия не был найден"));
    }

    @Override
    public EventTranslationDto getTranslation(Long eventId, String languageCode) {
        return etMapper.toDto(getTranslationEntity(eventId, languageCode));
    }

    @Override
    public List<EventTranslationDto> getTranslationsByLanguage(String languageCode) {
        return etRepository.findByIdLanguageCode(languageCode)
                .stream()
                .map(etMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public EventTranslationDto createTranslation(EventTranslationDto dto) {
        EventTranslation translation = etMapper.toEntity(dto);
        etRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTitle());
        return dto;
    }

    @Transactional
    @Override
    public EventTranslationDto updateTranslation(Long eventId, String languageCode, String title, String content) {
        EventTranslation translation = getTranslationEntity(eventId, languageCode);
        translation.setTitle(title);
        translation.setContent(content);
        EventTranslation saved = etRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, title);
        return etMapper.toDto(saved);
    }

    @Transactional
    @Override
    public EventTranslationDto upsertTranslation(EventTranslationDto dto) {
        EventTranslationId id = new EventTranslationId();
        id.setEventId(dto.getEventId());
        id.setLanguageCode(dto.getLanguageCode());

        boolean exists = etRepository.existsById(id);

        if (exists) {
            return updateTranslation(dto.getEventId(), dto.getLanguageCode(), dto.getTitle(), dto.getContent());
        } else {
            return createTranslation(dto);
        }
    }

    @Transactional
    @Override
    public void deleteTranslation(Long eventId, String languageCode) {
        EventTranslationId id = new EventTranslationId();
        id.setEventId(eventId);
        id.setLanguageCode(languageCode);
        etRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllTranslationsByEventId(Long eventId) {
        etRepository.deleteByEventId(eventId);
    }

    @Override
    public boolean existsTranslation(Long eventId, String languageCode) {
        EventTranslationId id = new EventTranslationId();
        id.setEventId(eventId);
        id.setLanguageCode(languageCode);
        return etRepository.existsById(id);
    }

    @Transactional
    @Override
    public void saveTranslations(Long eventId, List<EventTranslationDto> translations) {
        if (translations == null || translations.isEmpty()) {
            return;
        }

        for (EventTranslationDto translation : translations) {
            if (translation.getTitle() != null && !translation.getTitle().trim().isEmpty()
                    && translation.getContent() != null && !translation.getContent().trim().isEmpty()) {
                translation.setEventId(eventId);
                upsertTranslation(translation);
            }
        }

        log.info("Saved {} translations for event {}", translations.size(), eventId);
    }
}