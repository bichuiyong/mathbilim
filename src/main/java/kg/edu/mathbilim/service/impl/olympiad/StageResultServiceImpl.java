package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.repository.olympiad.StageResultRepository;
import kg.edu.mathbilim.service.interfaces.olympiad.StageResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageResultServiceImpl implements StageResultService {
    private final StageResultRepository stageResultRepository;
}
