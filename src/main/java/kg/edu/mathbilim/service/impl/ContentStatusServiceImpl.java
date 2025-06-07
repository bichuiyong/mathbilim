package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.ContentStatusDto;
import kg.edu.mathbilim.mapper.ContentStatusMapper;
import kg.edu.mathbilim.repository.ContentStatusRepository;
import kg.edu.mathbilim.service.interfaces.ContentStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentStatusServiceImpl implements ContentStatusService {
    private final ContentStatusRepository contentStatusRepository;
    private final ContentStatusMapper contentStatusMapper;

    @Override
    public List<ContentStatusDto> getAllStatuses() {
        return contentStatusRepository.findAll()
                .stream()
                .map(contentStatusMapper::toDto)
                .toList();
    }
}
