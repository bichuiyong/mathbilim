package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.EventTypeDto;
import kg.edu.mathbilim.mapper.EventTypeMapper;
import kg.edu.mathbilim.model.EventType;
import kg.edu.mathbilim.repository.EventTypeRepository;
import kg.edu.mathbilim.service.interfaces.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;

    @Override
    public EventTypeDto getEventTypeByName(String name) {
        EventType e = eventTypeRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("such event type not found"));
        return eventTypeMapper.toDto(e);
    }

    @Override
    public List<EventTypeDto> getAllEventTypes() {
        return eventTypeRepository.findAll()
                .stream()
                .map(eventTypeMapper::toDto)
                .toList();
    }

    @Override
    public boolean existsByEventType(String eventType) {
        return eventTypeRepository.findByName(eventType).isPresent();
    }

    @Override
    public EventTypeDto createEventType(EventTypeDto eventTypeDto) {
        return eventTypeMapper.toDto(eventTypeRepository.save(eventTypeMapper.toEntity(eventTypeDto)));
    }

    @Override
    public void deleteEventType(Integer eventTypeDto) {
        EventType eventType = eventTypeRepository.findById(eventTypeDto).orElseThrow(()-> new NoSuchElementException("such event type not found"));
        eventTypeRepository.delete(eventType);
    }

    @Override
    public EventTypeDto updateEventType(EventTypeDto eventTypeDto) {
        return eventTypeMapper.toDto(eventTypeRepository.save(eventTypeMapper.toEntity(eventTypeDto)));
    }
}
