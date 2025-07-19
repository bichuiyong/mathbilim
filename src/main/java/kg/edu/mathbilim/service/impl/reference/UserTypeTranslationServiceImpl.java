package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.mapper.user.UserTypeTranslationMapper;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import kg.edu.mathbilim.repository.user.UserTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeTranslationService;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeTranslationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeTranslationServiceImpl extends AbstractTypeTranslationService<
        UserTypeTranslation,
        UserTypeTranslationDto,
        UserTypeTranslationRepository,
        UserTypeTranslationMapper> implements UserTypeTranslationService {

    public UserTypeTranslationServiceImpl(UserTypeTranslationRepository repository,
                                          UserTypeTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getNotFoundMessage() {
        return "Перевод для этого типа пользователя не был найден";
    }

    @Override
    public List<UserTypeTranslationDto> getTranslationsByUserTypeId(Integer userTypeId) {
        return super.getTranslationsByTypeId(userTypeId);
    }

    @Override
    public void deleteAllTranslationsByUserTypeId(Integer userTypeId) {
        super.deleteAllTranslationsByTypeId(userTypeId);
    }
}