package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;

public interface OlympiadService {
    void save(OlympiadCreateDto olympiadCreateDto, String email);
}
