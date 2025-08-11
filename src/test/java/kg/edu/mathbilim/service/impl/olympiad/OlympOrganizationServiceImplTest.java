package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.organization.OlympOrganizationDto;
import kg.edu.mathbilim.mapper.OlympOrganizationMapper;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.organization.OlympiadOrganizationKey;
import kg.edu.mathbilim.repository.OrganizationRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.repository.organization.OlympOrganizationRepository;
import kg.edu.mathbilim.service.impl.organization.OlympOrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OlympOrganizationServiceImplTest {

    @Mock
    private OlympOrganizationRepository olympOrganizationRepository;

    @Mock
    private OlympiadRepository olympiadRepository;

    @Mock
    private OrganizationRepository organizationRepo;

    @Mock
    private OlympOrganizationMapper mapper;

    @InjectMocks
    private OlympOrganizationServiceImpl olympOrganizationService;

    private OlympOrganizationDto dto;
    private Olympiad olympiad;
    private Organization organization;
    private OlympiadOrganization olympiadOrganization;
    private OlympiadOrganizationKey olympiadOrganizationKey;

    @BeforeEach
    void setUp() {
        dto = new OlympOrganizationDto();
        dto.setOlympiadId(1L);
        dto.setOrganizationId(2L);

        olympiad = new Olympiad();
        olympiad.setId(1L);

        organization = new Organization();
        organization.setId(2L);

        olympiadOrganizationKey = new OlympiadOrganizationKey();
        olympiadOrganizationKey.setOlympiad(1L);
        olympiadOrganizationKey.setOrganization(2L);

        olympiadOrganization = new OlympiadOrganization();
        olympiadOrganization.setId(olympiadOrganizationKey);
    }

    @Test
    void addOrganizationToOlympiad_Success() {
        when(olympiadRepository.findById(dto.getOlympiadId())).thenReturn(Optional.of(olympiad));
        when(organizationRepo.findById(dto.getOrganizationId())).thenReturn(Optional.of(organization));
        when(mapper.toEntity(dto)).thenReturn(olympiadOrganization);
        when(olympOrganizationRepository.save(any(OlympiadOrganization.class))).thenReturn(olympiadOrganization);

        olympOrganizationService.addOrganizationToOlympiad(dto);

        verify(olympiadRepository).findById(dto.getOlympiadId());
        verify(organizationRepo).findById(dto.getOrganizationId());
        verify(mapper).toEntity(dto);
        verify(olympOrganizationRepository).save(any(OlympiadOrganization.class));

        assertEquals(olympiad, olympiadOrganization.getOlympiad());
        assertEquals(organization, olympiadOrganization.getOrganization());

        assertNotNull(olympiadOrganization.getId());
        assertEquals(olympiad.getId(), olympiadOrganization.getId().getOlympiad());
        assertEquals(organization.getId(), olympiadOrganization.getId().getOrganization());
    }

    @Test
    void addOrganizationToOlympiad_OlympiadNotFound() {
        when(olympiadRepository.findById(dto.getOlympiadId())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> olympOrganizationService.addOrganizationToOlympiad(dto));

        assertEquals("Olympiad not found", exception.getMessage());
        verify(olympiadRepository).findById(dto.getOlympiadId());
        verify(organizationRepo, never()).findById(anyLong());
        verify(mapper, never()).toEntity(any());
        verify(olympOrganizationRepository, never()).save(any());
    }

    @Test
    void addOrganizationToOlympiad_OrganizationNotFound() {
        when(olympiadRepository.findById(dto.getOlympiadId())).thenReturn(Optional.of(olympiad));
        when(organizationRepo.findById(dto.getOrganizationId())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> olympOrganizationService.addOrganizationToOlympiad(dto));

        assertEquals("Organization not found", exception.getMessage());
        verify(olympiadRepository).findById(dto.getOlympiadId());
        verify(organizationRepo).findById(dto.getOrganizationId());
        verify(mapper, never()).toEntity(any());
        verify(olympOrganizationRepository, never()).save(any());
    }

    @Test
    void getOrganizationIds_Success() {
        int olympId = 1;
        List<Long> expectedIds = Arrays.asList(1L, 2L, 3L);
        when(olympOrganizationRepository.getOrganizationIdsWhereOlympiadId(olympId))
                .thenReturn(expectedIds);

        List<Long> result = olympOrganizationService.getOrganizationIds(olympId);

        assertEquals(expectedIds, result);
        assertEquals(3, result.size());
        verify(olympOrganizationRepository).getOrganizationIdsWhereOlympiadId(olympId);
    }

    @Test
    void getOrganizationIds_EmptyList() {
        int olympId = 1;
        List<Long> expectedIds = Arrays.asList();
        when(olympOrganizationRepository.getOrganizationIdsWhereOlympiadId(olympId))
                .thenReturn(expectedIds);

        List<Long> result = olympOrganizationService.getOrganizationIds(olympId);

        assertEquals(expectedIds, result);
        assertTrue(result.isEmpty());
        verify(olympOrganizationRepository).getOrganizationIdsWhereOlympiadId(olympId);
    }

    @Test
    void getByOlympiadId_Success() {
        Long olympiadId = 1L;

        OlympiadOrganization org1 = new OlympiadOrganization();
        OlympiadOrganizationKey key1 = new OlympiadOrganizationKey();
        key1.setOlympiad(olympiadId);
        key1.setOrganization(1L);
        org1.setId(key1);

        OlympiadOrganization org2 = new OlympiadOrganization();
        OlympiadOrganizationKey key2 = new OlympiadOrganizationKey();
        key2.setOlympiad(olympiadId);
        key2.setOrganization(2L);
        org2.setId(key2);

        List<OlympiadOrganization> expectedList = Arrays.asList(org1, org2);
        when(olympOrganizationRepository.findByOlympiadId(olympiadId))
                .thenReturn(expectedList);

        List<OlympiadOrganization> result = olympOrganizationService.getByOlympiadId(olympiadId);

        assertEquals(expectedList, result);
        assertEquals(2, result.size());
        verify(olympOrganizationRepository).findByOlympiadId(olympiadId);
    }

    @Test
    void getByOlympiadId_EmptyList() {
        Long olympiadId = 1L;
        List<OlympiadOrganization> expectedList = Arrays.asList();
        when(olympOrganizationRepository.findByOlympiadId(olympiadId))
                .thenReturn(expectedList);

        List<OlympiadOrganization> result = olympOrganizationService.getByOlympiadId(olympiadId);

        assertEquals(expectedList, result);
        assertTrue(result.isEmpty());
        verify(olympOrganizationRepository).findByOlympiadId(olympiadId);
    }

    @Test
    void getByOrganizationId_Success() {
        Long organizationId = 1L;

        OlympiadOrganization org1 = new OlympiadOrganization();
        OlympiadOrganizationKey key1 = new OlympiadOrganizationKey();
        key1.setOlympiad(1L);
        key1.setOrganization(organizationId);
        org1.setId(key1);

        OlympiadOrganization org2 = new OlympiadOrganization();
        OlympiadOrganizationKey key2 = new OlympiadOrganizationKey();
        key2.setOlympiad(2L);
        key2.setOrganization(organizationId);
        org2.setId(key2);

        List<OlympiadOrganization> expectedList = Arrays.asList(org1, org2);
        when(olympOrganizationRepository.findByOrganizationId(organizationId))
                .thenReturn(expectedList);

        List<OlympiadOrganization> result = olympOrganizationService.getByOrganizationId(organizationId);

        assertEquals(expectedList, result);
        assertEquals(2, result.size());
        verify(olympOrganizationRepository).findByOrganizationId(organizationId);
    }

    @Test
    void getByOrganizationId_EmptyList() {
        Long organizationId = 1L;
        List<OlympiadOrganization> expectedList = Arrays.asList();
        when(olympOrganizationRepository.findByOrganizationId(organizationId))
                .thenReturn(expectedList);

        List<OlympiadOrganization> result = olympOrganizationService.getByOrganizationId(organizationId);

        assertEquals(expectedList, result);
        assertTrue(result.isEmpty());
        verify(olympOrganizationRepository).findByOrganizationId(organizationId);
    }

    @Test
    void addAll_Success() {
        OlympiadOrganization org1 = new OlympiadOrganization();
        OlympiadOrganizationKey key1 = new OlympiadOrganizationKey();
        key1.setOlympiad(1L);
        key1.setOrganization(1L);
        org1.setId(key1);

        OlympiadOrganization org2 = new OlympiadOrganization();
        OlympiadOrganizationKey key2 = new OlympiadOrganizationKey();
        key2.setOlympiad(1L);
        key2.setOrganization(2L);
        org2.setId(key2);

        List<OlympiadOrganization> olympiadOrganizations = Arrays.asList(org1, org2);
        when(olympOrganizationRepository.saveAll(olympiadOrganizations))
                .thenReturn(olympiadOrganizations);

        olympOrganizationService.addAll(olympiadOrganizations);

        verify(olympOrganizationRepository).saveAll(olympiadOrganizations);
    }

    @Test
    void addAll_EmptyList() {
        List<OlympiadOrganization> emptyList = Arrays.asList();

        olympOrganizationService.addAll(emptyList);

        verify(olympOrganizationRepository).saveAll(emptyList);
    }

    @Test
    void addAll_NullList() {
        List<OlympiadOrganization> nullList = null;

        olympOrganizationService.addAll(nullList);

        verify(olympOrganizationRepository).saveAll(nullList);
    }

    @Test
    void deleteByOlympiadId_Success() {
        Long olympiadId = 1L;
        doNothing().when(olympOrganizationRepository).deleteByOlympiadId(olympiadId);

        olympOrganizationService.deleteByOlympiadId(olympiadId);

        verify(olympOrganizationRepository).deleteByOlympiadId(olympiadId);
    }

    @Test
    void deleteByOlympiadId_NullId() {
        Long olympiadId = null;
        doNothing().when(olympOrganizationRepository).deleteByOlympiadId(olympiadId);

        olympOrganizationService.deleteByOlympiadId(olympiadId);

        verify(olympOrganizationRepository).deleteByOlympiadId(olympiadId);
    }

    @Test
    void addOrganizationToOlympiad_VerifyCompositeKeyStructure() {
        when(olympiadRepository.findById(dto.getOlympiadId())).thenReturn(Optional.of(olympiad));
        when(organizationRepo.findById(dto.getOrganizationId())).thenReturn(Optional.of(organization));
        when(mapper.toEntity(dto)).thenReturn(olympiadOrganization);
        when(olympOrganizationRepository.save(any(OlympiadOrganization.class))).thenReturn(olympiadOrganization);

        olympOrganizationService.addOrganizationToOlympiad(dto);

        ArgumentCaptor<OlympiadOrganization> captor = ArgumentCaptor.forClass(OlympiadOrganization.class);
        verify(olympOrganizationRepository).save(captor.capture());

        OlympiadOrganization savedEntity = captor.getValue();
        assertNotNull(savedEntity.getId());
        assertNotNull(savedEntity.getOlympiad());
        assertNotNull(savedEntity.getOrganization());

        assertEquals(olympiad.getId(), savedEntity.getId().getOlympiad());
        assertEquals(organization.getId(), savedEntity.getId().getOrganization());
        assertEquals(olympiad, savedEntity.getOlympiad());
        assertEquals(organization, savedEntity.getOrganization());
    }

    @Test
    void compositeKey_EqualsAndHashCode() {
        OlympiadOrganizationKey key1 = new OlympiadOrganizationKey(1L, 2L);
        OlympiadOrganizationKey key2 = new OlympiadOrganizationKey(1L, 2L);
        OlympiadOrganizationKey key3 = new OlympiadOrganizationKey(2L, 3L);

        assertEquals(key1, key2);
        assertNotEquals(key1, key3);
        assertEquals(key1.hashCode(), key2.hashCode());
        assertNotEquals(key1.hashCode(), key3.hashCode());
    }
    @Test
    void addOrganizationToOlympiad_WithNullDto() {
        OlympOrganizationDto nullDto = null;

        assertThrows(NullPointerException.class,
                () -> olympOrganizationService.addOrganizationToOlympiad(nullDto));
    }

    @Test
    void addOrganizationToOlympiad_MapperReturnsNull() {
        when(olympiadRepository.findById(dto.getOlympiadId())).thenReturn(Optional.of(olympiad));
        when(organizationRepo.findById(dto.getOrganizationId())).thenReturn(Optional.of(organization));
        when(mapper.toEntity(dto)).thenReturn(null);
        
        assertThrows(NullPointerException.class,
                () -> olympOrganizationService.addOrganizationToOlympiad(dto));

        verify(olympiadRepository).findById(dto.getOlympiadId());
        verify(organizationRepo).findById(dto.getOrganizationId());
        verify(mapper).toEntity(dto);
        verify(olympOrganizationRepository, never()).save(any());
    }
}