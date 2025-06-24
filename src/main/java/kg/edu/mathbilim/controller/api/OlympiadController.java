package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("apiOlympiadController")
@RequiredArgsConstructor
@RequestMapping("api/olymp")
public class OlympiadController {
    private final OlympiadService olympiadService;

    @GetMapping()
    public ResponseEntity<Page<OlympiadDto>> getAllOlymps() {
        Pageable pageable = PageRequest.of(0, 10);
        return ResponseEntity.ofNullable(olympiadService.getAll(pageable));
    }
}
