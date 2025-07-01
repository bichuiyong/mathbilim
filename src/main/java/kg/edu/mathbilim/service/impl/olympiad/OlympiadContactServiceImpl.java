package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.ContactType;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.repository.ContactTypeRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadContactRepository;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OlympiadContactServiceImpl implements OlympiadContactService {
    private final OlympiadContactRepository repository;
    private final ContactTypeRepository contactTypeRepository;

    @Override
    public List<OlympiadContact> getContactsByOlympId(int olympId) {
        return repository.getByOlympiad_Id(olympId);
    }

    @Override
    public void addAllContacts(List<OlympiadContact> contacts) {
        repository.saveAll(contacts);
    }
}
