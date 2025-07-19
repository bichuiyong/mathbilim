package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.ContactTypeDto;
import kg.edu.mathbilim.model.ContactType;

import java.util.List;

public interface ContactTypeService {
    List<ContactTypeDto> getTypes();

    ContactType getById(Integer id);
}
