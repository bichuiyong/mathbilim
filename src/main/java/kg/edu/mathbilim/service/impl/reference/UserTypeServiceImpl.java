package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.mapper.user.UserTypeMapper;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import kg.edu.mathbilim.repository.user.UserTypeRepository;
import kg.edu.mathbilim.repository.user.UserTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeContentService;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserTypeServiceImpl
        extends AbstractTypeContentService<
        UserType,
        UserTypeDto,
        UserTypeTranslation,
        UserTypeTranslationDto,
        UserTypeRepository,
        UserTypeTranslationRepository,
        UserTypeMapper>
        implements UserTypeService {

    public UserTypeServiceImpl(UserTypeRepository repository,
                               UserTypeTranslationRepository translationRepository,
                               UserTypeMapper mapper) {
        super(repository, translationRepository, mapper);
    }

    @Override
    public List<UserTypeDto> getAllUserTypes() {
        return getAll();
    }

    @Override
    public UserType getUserTypeEntity(Integer id) {
        return getEntity(id);
    }

    @Override
    public UserTypeDto getUserTypeById(Integer id) {
        return getByIdOrThrow(id);
    }

    @Override
    public List<UserTypeDto> getUserTypesByLanguage(String languageCode) {
        return getByLanguage(languageCode);
    }

    @Transactional
    @Override
    public UserTypeDto createUserType(UserTypeDto userTypeDto) {
        return create(userTypeDto);
    }

    @Transactional
    @Override
    public UserTypeDto updateUserType(Integer id, UserTypeDto userTypeDto) {
        return update(id, userTypeDto);
    }

    @Transactional
    @Override
    public void deleteUserType(Integer id) {
        delete(id);
    }

    @Transactional
    @Override
    public UserTypeDto addTranslation(Integer userTypeId, String languageCode, String translation) {
        return addTranslation(userTypeId, languageCode, translation);
    }

    @Transactional
    @Override
    public UserTypeDto removeTranslation(Integer userTypeId, String languageCode) {
        return removeTranslation(userTypeId, languageCode);
    }

    @Override
    protected UserType createNewEntity() {
        return new UserType();
    }

    @Override
    protected UserTypeTranslationDto createTranslationDto(Integer typeId, String languageCode, String translation) {
        return UserTypeTranslationDto.builder()
                .userTypeId(typeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();
    }

    @Override
    protected void setTypeIdInTranslation(UserTypeTranslationDto translationDto, Integer typeId) {
        translationDto.setUserTypeId(typeId);
    }
}