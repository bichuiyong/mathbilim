package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.olympiad.ContactType;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadContactInfo;
import kg.edu.mathbilim.repository.ContactTypeRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadContactRepository;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OlympiadContactServiceImpl implements OlympiadContactService {
    private final OlympiadContactRepository repository;
    private final ContactTypeRepository contactTypeRepository;


    public ContactType toContactTypeEntity(Integer typeId) {
        return contactTypeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("No such contact type"));
    }

    @Override
    public void save(OlympiadCreateDto dto, Olympiad olympiad) {
        if (dto.getContacts() == null) return;

        dto.getContacts().forEach(c -> {
            OlympiadContactInfo contact =
                    OlympiadContactInfo.builder()
                            .contactType(toContactTypeEntity(c.getContactDto().getId()))
                            .olympiad(olympiad)
                            .info(c.getInfo())
                            .build();
            repository.saveAndFlush(contact);
        });
    }

}
