package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.olympiad.OlympListDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.service.impl.olympiad.ResultServiceImpl;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.olympiad.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController("apiOlympiadController")
@RequiredArgsConstructor
@RequestMapping("api/olymp")
public class OlympiadController {
    private final OlympiadService olympiadService;
    private final OlympiadStageService olympiadStageService;

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

    @GetMapping("main")
    public ResponseEntity<?> getLatestEvents() {
        return ResponseEntity.ofNullable(olympiadService.getOlympiadForMainPage());
    }

    @GetMapping("stage/list")
    public ResponseEntity<Page<RegistrationDto>> getRegisteredUsersList(long stageId,
                                                                        @RequestParam(defaultValue = "") String keyword,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "fullName") String sortBy,
                                                                        @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ofNullable(olympiadStageService.getOlympiadRegistrations(stageId,pageable,keyword));
    }

    @GetMapping("main")
    public ResponseEntity<?> getLatestEvents() {
        return ResponseEntity.ofNullable(olympiadService.getOlympiadForMainPage());
    }
}
