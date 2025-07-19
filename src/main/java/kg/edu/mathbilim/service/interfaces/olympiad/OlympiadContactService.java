package kg.edu.mathbilim.service.interfaces.olympiad;

import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import java.util.List;

public interface OlympiadContactService {
    List<OlympiadContact> getContactsByOlympId(int olympId);

    void addAllContacts(List<OlympiadContact> contacts);

    void deleteByOlympiadId(Long olympId);
}
