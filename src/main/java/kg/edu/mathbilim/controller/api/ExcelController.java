package kg.edu.mathbilim.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import kg.edu.mathbilim.service.impl.ExcelService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/excel")
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;
    private final OlympiadStageService olympiadStageService;
    private final TestService testService;

    @GetMapping("/download/registered")
    public void downloadExcel(HttpServletResponse response,
                              @RequestParam long stageId) throws IOException {

        excelService.generateExcelFile(olympiadStageService.getOlympiadRegistrationsForExcel(stageId), response);
    }

    @GetMapping("/download/result")
    public void downloadAttemptResult(HttpServletResponse response,
                                      @RequestParam long attemptId,
                                      Authentication auth) throws IOException {
        excelService.generateDetailedTestResult(testService.getResultByAttemptId(attemptId,auth.getName()),response);
    }
}
