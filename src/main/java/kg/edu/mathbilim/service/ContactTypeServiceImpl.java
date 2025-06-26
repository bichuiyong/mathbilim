package kg.edu.mathbilim.service;

import jakarta.persistence.EntityNotFoundException;
import kg.edu.mathbilim.dto.ContactTypeDto;
import kg.edu.mathbilim.model.ContactType;
import kg.edu.mathbilim.repository.ContactTypeRepository;
import kg.edu.mathbilim.service.interfaces.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository repository;

    @Override
    public List<ContactTypeDto> getTypes() {
        List<ContactType> types = repository.findAll();
        return types.stream().map(contactType -> ContactTypeDto
                .builder()
                .id(contactType.getId())
                .name(contactType.getName())
                .build())
                .toList();
    }

    @Override
    public ContactType getById(Integer id) {
        return repository.findById(Math.toIntExact(id)).orElseThrow(() -> new EntityNotFoundException());
    }

}
