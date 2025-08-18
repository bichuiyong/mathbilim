package kg.edu.mathbilim.serviseTest.olympiad;

import kg.edu.mathbilim.repository.olympiad.OlympiadStageRepository;
import kg.edu.mathbilim.service.impl.olympiad.OlympiadStageServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OlympiadStageServiceImplTest {
    @Mock
    private OlympiadStageRepository repository;
    @InjectMocks
    private OlympiadStageServiceImpl service;



}
