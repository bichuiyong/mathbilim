package kg.edu.mathbilim.serviseTest.olympiad;

import kg.edu.mathbilim.mapper.OlympOrganizationMapper;
import kg.edu.mathbilim.repository.OrganizationRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.repository.organization.OlympOrganizationRepository;
import kg.edu.mathbilim.service.impl.organization.OlympOrganizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OlympiadOrganizationServiceImplTest {
    @Mock
    private OlympOrganizationRepository olympOrganizationRepository;
    @Mock
    private OlympiadRepository olympiadRepository;
    @Mock
    private OrganizationRepository organizationRepo;
    @Mock
    private OlympOrganizationMapper mapper;
    @InjectMocks
    private OlympOrganizationServiceImpl service;






}

