package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;

public interface OlympiadContactService {
    void save(OlympiadCreateDto dto, Olympiad olympiad);
}
