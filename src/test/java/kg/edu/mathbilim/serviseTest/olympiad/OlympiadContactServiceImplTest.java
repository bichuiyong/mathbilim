package kg.edu.mathbilim.serviseTest.olympiad;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;
import kg.edu.mathbilim.repository.ContactTypeRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadContactRepository;
import kg.edu.mathbilim.service.impl.olympiad.OlympiadContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OlympiadContactServiceImplTest {
    @Mock
    private OlympiadContactRepository olympiadContactRepository;
    @Mock
    private ContactTypeRepository contactTypeRepository;
    @InjectMocks
    private OlympiadContactServiceImpl service;



}
