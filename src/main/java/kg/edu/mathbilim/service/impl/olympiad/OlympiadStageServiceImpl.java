package kg.edu.mathbilim.service.impl.olympiad;

import jakarta.ws.rs.NotFoundException;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.ResultDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.repository.olympiad.OlympiadStageRepository;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public List<OlympiadStageDto> getOlympStageDtos(Long id) {
        return repository.getOlympiadStageByOlympiad_Id(id)
                .stream()
                .map(olympiadStage -> OlympiadStageDto
                        .builder()
                        .id(Long.valueOf(olympiadStage.getId()))
                        .stageOrder(olympiadStage.getStageOrder())
                        .registrationStart(olympiadStage.getRegistrationStart())
                        .registrationEnd(olympiadStage.getRegistrationEnd())
                        .createdAt(olympiadStage.getCreatedAt())
                        .updatedAt(olympiadStage.getUpdatedAt())
                        .startDate(olympiadStage.getStartDate())
                        .endDate(olympiadStage.getEndDate())
                        .result(olympiadStage.getResult().stream().map(result -> ResultDto
                                .builder()
                                        .id(result.getId())
                                        .file(new FileDto(
                                                result.getFile().getId(),
                                                result.getFile().getFilename(),
                                                result.getFile().getFilePath(),
                                                result.getFile().getType(),
                                                result.getFile().getSize(),
                                                result.getFile().getS3Link()
                                        ))
                                        .createdAt(result.getCreatedAt())
                                        .updatedAt(result.getUpdatedAt())
                                .build())
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public void addAll(List<OlympiadStage> olympiadStages) {
        repository.saveAll(olympiadStages);
    }

    @Override
    public void deleteByOlympiadId(Long olympiadId) {
        repository.deleteByOlympiadId(olympiadId);
    }

    @Override
    public OlympiadStage getOlympiadStageById(Integer stageId) {
        return repository.findById(stageId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<OlympiadStage> saveAll(List<OlympiadStage> olympiadStages) {
        return repository.saveAll(olympiadStages);
    }
}
