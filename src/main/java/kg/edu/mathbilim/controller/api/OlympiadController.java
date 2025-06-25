package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.olympiad.OlympListDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("apiOlympiadController")
@RequiredArgsConstructor
@RequestMapping("api/olymp")
public class OlympiadController {
    private final OlympiadService olympiadService;

    @GetMapping("all")
    public ResponseEntity<Page<OlympListDto>> getAllOlymps(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ofNullable(olympiadService.getAll(pageable));
    }
}
