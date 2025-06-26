package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.repository.olympiad.OlympiadStageRepository;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OlympiadStageServiceImpl implements OlympiadStageService {
    private final OlympiadStageRepository repository;


    @Override
    public void save(OlympiadCreateDto dto, Olympiad olympiad) {
        if (dto.getStages() == null) return;

        AtomicInteger order = new AtomicInteger(1);
        dto.getStages().forEach(s -> {
            OlympiadStage stage = OlympiadStage.builder()
                    .startDate(s.getEventStartDate())
                    .endDate(s.getEventEndDate())
                    .registrationStart(s.getRegistrationStart())
                    .registrationEnd(s.getRegistrationEnd())
                    .olympiad(olympiad)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .stageOrder(order.getAndIncrement())
                    .build();
            repository.saveAndFlush(stage);
        });

    }
}
