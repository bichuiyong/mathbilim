package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.olympiad.Registration;

import java.util.List;
import java.util.Optional;

public interface OlympiadStageService {
    void save(OlympiadCreateDto dto, Olympiad olympiad);

    List<OlympiadStageDto> getOlympStageDtos(Long id);

    void addAll(List<OlympiadStage> olympiadStages);

    void deleteByOlympiadId(Long olympiadId);

    OlympiadStage getOlympiadStageById(Integer stageId);

    List<OlympiadStage> saveAll(List<OlympiadStage> olympiadStages);

    void updateTime(OlympiadStage olympiadStage);

    Optional<Long> createRegistrationOlympiad(RegistrationDto dto, Long stageId, String userName);

    boolean checkRegisterActually(long stageId);

    boolean userHasRegistered(String userName, long stageId);
}
