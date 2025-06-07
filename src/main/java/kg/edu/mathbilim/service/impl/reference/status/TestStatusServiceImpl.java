package kg.edu.mathbilim.service.impl.reference.status;

import kg.edu.mathbilim.dto.reference.status.TestStatusDto;
import kg.edu.mathbilim.mapper.reference.status.TestStatusMapper;
import kg.edu.mathbilim.repository.reference.status.TestStatusRepository;
import kg.edu.mathbilim.service.interfaces.reference.status.TestStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestStatusServiceImpl implements TestStatusService {
    private final TestStatusRepository testStatusRepository;
    private final TestStatusMapper testStatusMapper;

    @Override
    public List<TestStatusDto> getAllStatuses() {
        return testStatusRepository.findAll()
                .stream()
                .map(testStatusMapper::toDto)
                .toList();
    }
}
