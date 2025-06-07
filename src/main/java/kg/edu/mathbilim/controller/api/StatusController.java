package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.ContentStatusDto;
import kg.edu.mathbilim.dto.TestStatusDto;
import kg.edu.mathbilim.service.interfaces.ContentStatusService;
import kg.edu.mathbilim.service.interfaces.TestStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("restStatus")
@RequestMapping("api/statuses")
@RequiredArgsConstructor
public class StatusController {
    private final TestStatusService testStatusService;
    private final ContentStatusService contentStatusService;

    @GetMapping("tests")
    public ResponseEntity<List<TestStatusDto>> getAllUserTypes() {
        return ResponseEntity.ofNullable(testStatusService.getAllStatuses());
    }

    @GetMapping("contents")
    public ResponseEntity<List<ContentStatusDto>> getAllEventTypes() {
        return ResponseEntity.ofNullable(contentStatusService.getAllStatuses());
    }
}
