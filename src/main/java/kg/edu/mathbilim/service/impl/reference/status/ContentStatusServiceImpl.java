package kg.edu.mathbilim.service.impl.reference.status;

import kg.edu.mathbilim.dto.reference.status.ContentStatusDto;
import kg.edu.mathbilim.mapper.reference.status.ContentStatusMapper;
import kg.edu.mathbilim.repository.reference.status.ContentStatusRepository;
import kg.edu.mathbilim.service.interfaces.reference.status.ContentStatusService;
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
