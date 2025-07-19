package kg.edu.mathbilim.service.impl.organization;

import kg.edu.mathbilim.dto.organization.OlympOrganizationDto;
import kg.edu.mathbilim.mapper.OlympOrganizationMapper;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.repository.organization.OlympOrganizationRepository;

import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.repository.OrganizationRepository;
import kg.edu.mathbilim.service.interfaces.organization.OlympOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OlympOrganizationServiceImpl implements OlympOrganizationService {

    private final OlympOrganizationRepository olympOrganizationRepository;
    private final OlympiadRepository olympiadRepository;
    private final OrganizationRepository organizationRepo;
    private final OlympOrganizationMapper mapper;

    @Transactional
    @Override
    public void addOrganizationToOlympiad(OlympOrganizationDto dto) {
        Olympiad olympiad = olympiadRepository.findById(dto.getOlympiadId())
                .orElseThrow(() -> new NoSuchElementException("Olympiad not found"));
        Organization organization = organizationRepo.findById(dto.getOrganizationId())
                .orElseThrow(() -> new NoSuchElementException("Organization not found"));

        OlympiadOrganization link = mapper.toEntity(dto);
        link.setOlympiad(olympiad);
        link.setOrganization(organization);

        olympOrganizationRepository.save(link);
    }

    @Override
    public List<Long> getOrganizationIds(int olympId) {
        return olympOrganizationRepository.getOrganizationIdsWhereOlympiadId(olympId);
    }

    @Override
    public List<OlympiadOrganization> getByOlympiadId(Long olympiadId) {
        return olympOrganizationRepository.findByOlympiadId(olympiadId);
    }
    @Override
    public List<OlympiadOrganization> getByOrganizationId(Long olympiadId) {
        return olympOrganizationRepository.findByOrganizationId(olympiadId);
    }

    @Override
    public void addAll(List<OlympiadOrganization> olympiadOrganization) {
        olympOrganizationRepository.saveAll(olympiadOrganization);
    }

    @Override
    public void deleteByOlympiadId(Long olympiadId) {
        olympOrganizationRepository.deleteByOlympiadId((olympiadId));
    }
}
