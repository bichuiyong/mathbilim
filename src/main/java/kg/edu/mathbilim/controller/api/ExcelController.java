package kg.edu.mathbilim.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import kg.edu.mathbilim.service.impl.ExcelService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/excel")
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;
    private final OlympiadStageService olympiadStageService;

    @GetMapping("/download/registered")
    public void downloadExcel(HttpServletResponse response,
                              @RequestParam long stageId) throws IOException {

        excelService.generateExcelFile(olympiadStageService.getOlympiadRegistrationsForExcel(stageId), response);
    }
}
