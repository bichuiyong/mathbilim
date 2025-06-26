package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympListDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OlympiadService {
    @Transactional
    void olympiadCreate(OlympiadCreateDto dto);

    Page<OlympListDto> getAll(Pageable pageable);

    OlympiadDto getById(long id);
}
