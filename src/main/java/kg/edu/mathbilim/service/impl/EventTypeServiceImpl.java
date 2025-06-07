package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.EventTypeDto;
import kg.edu.mathbilim.mapper.EventTypeMapper;
import kg.edu.mathbilim.repository.EventTypeRepository;
import kg.edu.mathbilim.service.interfaces.EventTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;

    @Override
    public List<EventTypeDto> getAllTypes() {
        return eventTypeRepository.findAll()
                .stream()
                .map(eventTypeMapper::toDto)
                .toList();
    }

}
