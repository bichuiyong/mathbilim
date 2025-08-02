package kg.edu.mathbilim.serviseTest.olympiad;

import jakarta.persistence.EntityManager;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.service.impl.olympiad.OlympiadServiceImpl;
import kg.edu.mathbilim.service.interfaces.ContactTypeService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.organization.OlympOrganizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OlympiadServiceImplTest {

    @Mock
    private OlympiadRepository olympiadRepository;
    @Mock
    private UserService userService;
    @Mock
    private OlympiadContactService olympiadContactService;
    @Mock
    private OlympiadStageService olympiadStageService;
    @Mock
    private OlympOrganizationService olympOrganizationService;
    @Mock
    private OrganizationService organizationService;
    @Mock
    private FileService fileService;
    @Mock
    private ContactTypeService contactTypeService;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private OlympiadServiceImpl olympiadService;

    @Test
    void getById_ShouldThrowException_WhenOlympiadNotFound() {
        long olympiadId = 1L;
        when(olympiadRepository.findById(olympiadId)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> olympiadService.getById(olympiadId));
    }

}

