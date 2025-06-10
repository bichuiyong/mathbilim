package kg.edu.mathbilim.service.impl;

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

    @Override
    public EventType getEventTypeByName(String name) {
        return eventTypeRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("such event type not found"));
    }

    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }
}
