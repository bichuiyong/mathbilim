package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Result;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.repository.ResultRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.olympiad.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final FileService fileService;
    private final OlympiadStageService olympiadStageService;
    private final UserService userService;

    @Override
    public String uploadResult(MultipartFile uploadFile, long stageId) {
        OlympiadStage olympStage = olympiadStageService.getOlympiadStageById((int) stageId);
        File file = fileService.uploadFileReturnEntity(uploadFile, "general");
        Result result = resultRepository.findByOlympiadStage(olympStage)
                .map(r -> {
                    r.setFile(file);
                    r.setUpdatedAt(LocalDateTime.now());
                    return r;
                })
                .orElseGet(() -> Result.builder()
                        .file(file)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .olympiadStage(olympStage)
                        .build()
                );
        resultRepository.save(result);
        olympStage.setUpdatedAt(LocalDateTime.now());
        olympiadStageService.updateTime(olympStage);
        return "redirect:/olympiad/details?id="+olympStage.getOlympiad().getId();
    }
}
