package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympListDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OlympiadService {
    @Transactional
    void olympiadCreate(OlympiadCreateDto dto);

    @Transactional
    void olympiadUpdate(OlympiadCreateDto dto);

    Page<OlympListDto> getAll(Pageable pageable);

    OlympiadCreateDto getOlympiadCreateDto(Long olympId);

    OlympiadDto getById(long id);

    List<OlympListDto> getOlympiadForMainPage();

    String uploadRegistrationResult(MultipartFile uploadFile, long stageId);

    List<OlympListDto> getOlympiadForMainPage();

    String uploadRegistrationResult(MultipartFile uploadFile, long stageId);

    List<OlympListDto> getOlympiadForMainPage();
}
