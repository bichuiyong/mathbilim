package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;

import java.util.List;

public interface OlympiadStageService {
    void save(OlympiadCreateDto dto, Olympiad olympiad);

    List<OlympiadStageDto> getOlympStageDtos(Long id);

    void addAll(List<OlympiadStage> olympiadStages);

    void deleteByOlympiadId(Long olympiadId);

    OlympiadStage getOlympiadStageById(Integer stageId);

    List<OlympiadStage> saveAll(List<OlympiadStage> olympiadStages);

    void updateTime(OlympiadStage olympiadStage);
}
