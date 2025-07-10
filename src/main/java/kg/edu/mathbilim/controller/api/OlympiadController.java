package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.olympiad.OlympListDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("apiOlympiadController")
@RequiredArgsConstructor
@RequestMapping("api/olymp")
public class OlympiadController {
    private final OlympiadService olympiadService;

    @GetMapping("all")
    public ResponseEntity<Page<OlympListDto>> getAllOlymps(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ofNullable(olympiadService.getAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<OlympiadCreateDto> getOlympById(@PathVariable long id) {
        return ResponseEntity.ofNullable(olympiadService.getOlympiadCreateDto(id));
    }
}
