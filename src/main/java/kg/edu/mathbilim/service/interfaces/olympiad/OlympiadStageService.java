package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;

import java.util.List;

public interface OlympiadStageService {
    void save(OlympiadCreateDto dto, Olympiad olympiad);

    List<OlympiadStageDto> getOlympStageDtos(int id);
}
