package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.repository.olympiad.OlympiadContactRepository;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OlympiadContactServiceImpl implements OlympiadContactService {
    private final OlympiadContactRepository repository;

    @Override
    public void save(OlympiadCreateDto dto, Olympiad olympiad) {
        if (dto.getContacts() == null) return;

        dto.getContacts().forEach(c -> {
            OlympiadContact contact =
                    OlympiadContact.builder()
                            .createdAt(LocalDateTime.now())
                            .olympiad(olympiad)
                            .info(c.getInfo())
                            .build();
            repository.saveAndFlush(contact);
        });
    }

}
