package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Result;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.repository.ResultRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ResultServiceImplTest {

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private FileService fileService;

    @Mock
    private OlympiadStageService olympiadStageService;

    @InjectMocks
    private ResultServiceImpl resultService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadResult_existingResult_updatesFileAndTimestamp() {
        Olympiad olympiad = new Olympiad();
        olympiad.setId(10L);

        long stageId = 1L;
        MultipartFile uploadFile = mock(MultipartFile.class);
        OlympiadStage olympStage = new OlympiadStage();
        olympStage.setId((int) stageId);
        olympStage.setOlympiad(olympiad);

        File uploadedFile = new File();

        Result existingResult = new Result();
        existingResult.setOlympiadStage(olympStage);
        existingResult.setUpdatedAt(LocalDateTime.now().minusDays(1));

        when(olympiadStageService.getOlympiadStageById((int) stageId)).thenReturn(olympStage);
        when(fileService.uploadFileReturnEntity(uploadFile, "general")).thenReturn(uploadedFile);
        when(resultRepository.findByOlympiadStage(olympStage)).thenReturn(Optional.of(existingResult));
        when(resultRepository.save(any(Result.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String redirect = resultService.uploadResult(uploadFile, stageId);

        assertEquals("redirect:/olympiad/details?id=10", redirect);
        assertEquals(uploadedFile, existingResult.getFile());
        assertNotNull(existingResult.getUpdatedAt());
        verify(resultRepository).save(existingResult);
        verify(olympiadStageService).updateTime(olympStage);
    }

    @Test
    void uploadResult_noExistingResult_createsNewResult() {
        Olympiad olympiad = new Olympiad();
        olympiad.setId(20L);

        long stageId = 2L;
        MultipartFile uploadFile = mock(MultipartFile.class);
        OlympiadStage olympStage = new OlympiadStage();
        olympStage.setId((int) stageId);
        olympStage.setOlympiad(olympiad);

        File uploadedFile = new File();

        when(olympiadStageService.getOlympiadStageById((int) stageId)).thenReturn(olympStage);
        when(fileService.uploadFileReturnEntity(uploadFile, "general")).thenReturn(uploadedFile);
        when(resultRepository.findByOlympiadStage(olympStage)).thenReturn(Optional.empty());
        when(resultRepository.save(any(Result.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String redirect = resultService.uploadResult(uploadFile, stageId);

        assertEquals("redirect:/olympiad/details?id=20", redirect);
        ArgumentCaptor<Result> captor = ArgumentCaptor.forClass(Result.class);
        verify(resultRepository).save(captor.capture());

        Result savedResult = captor.getValue();
        assertEquals(uploadedFile, savedResult.getFile());
        assertEquals(olympStage, savedResult.getOlympiadStage());
        assertNotNull(savedResult.getCreatedAt());
        assertNotNull(savedResult.getUpdatedAt());

        verify(olympiadStageService).updateTime(olympStage);
    }
}