package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OlympiadService {
    void save(OlympiadCreateDto olympiadCreateDto, String email);

    Page<OlympiadDto> getAll(Pageable pageable);
}
