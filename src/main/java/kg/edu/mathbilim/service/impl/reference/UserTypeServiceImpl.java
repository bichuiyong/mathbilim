package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.user.UserTypeMapper;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.repository.user.UserTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;
    private final UserTypeTranslationService userTypeTranslationService;

    @Override
    public List<UserTypeDto> getAllUserTypes() {
        return userTypeRepository.findAll()
                .stream()
                .map(userTypeMapper::toDto)
                .toList();
    }

    @Override
    public UserType getUserTypeEntity(Integer id) {
        return userTypeRepository.findById(id)
                .orElseThrow(TypeNotFoundException::new);
    }

    @Override
    public UserTypeDto getUserTypeById(Integer id) {
        return userTypeMapper.toDto(getUserTypeEntity(id));
    }

    @Override
    public List<UserTypeDto> getUserTypesByLanguage(String languageCode) {
        return userTypeRepository.findAll().stream()
                .map(userType -> {
                    UserTypeDto dto = userTypeMapper.toDto(userType);
                    dto.setUserTypeTranslations(List.of(userTypeTranslationService.getTranslation(userType.getId(), languageCode)));
                    return dto;
                })
                .toList();
    }

    @Transactional
    @Override
    public UserTypeDto createUserType(UserTypeDto userTypeDto) {
        UserType userType = new UserType();
        UserType savedUserType = userTypeRepository.save(userType);

        UserTypeDto savedDto = userTypeMapper.toDto(savedUserType);

        if (userTypeDto.getUserTypeTranslations() != null && !userTypeDto.getUserTypeTranslations().isEmpty()) {
            List<UserTypeTranslationDto> savedTranslations = userTypeDto
                    .getUserTypeTranslations()
                    .stream()
                    .map(translation -> {
                        translation.setUserTypeId(savedUserType.getId());
                        return userTypeTranslationService.createTranslation(translation);
                    })
                    .toList();
            savedDto.setUserTypeTranslations(savedTranslations);
        }

        return savedDto;
    }

    @Transactional
    @Override
    public UserTypeDto updateUserType(Integer id, UserTypeDto userTypeDto) {
        UserTypeDto dto = getUserTypeById(id);

        if (userTypeDto.getUserTypeTranslations() != null) {
            userTypeTranslationService.deleteAllTranslationsByUserTypeId(id);

            List<UserTypeTranslationDto> savedTranslations =
                    userTypeDto.getUserTypeTranslations()
                            .stream()
                            .map(translation -> {
                                translation.setUserTypeId(id);
                                return userTypeTranslationService.createTranslation(translation);
                            })
                            .toList();

            dto.setUserTypeTranslations(savedTranslations);
            return dto;
        }

        return dto;
    }

    @Transactional
    @Override
    public void deleteUserType(Integer id) {
        userTypeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserTypeDto addTranslation(Integer userTypeId, String languageCode, String translation) {
        UserTypeDto userType = getUserTypeById(userTypeId);

        UserTypeTranslationDto translationDto = UserTypeTranslationDto.builder()
                .userTypeId(userTypeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();

        userTypeTranslationService.upsertTranslation(translationDto);
        userType.getUserTypeTranslations().add(translationDto);
        return userType;
    }

    @Transactional
    @Override
    public UserTypeDto removeTranslation(Integer userTypeId, String languageCode) {
        getUserTypeById(userTypeId);
        userTypeTranslationService.deleteTranslation(userTypeId, languageCode);
        return getUserTypeById(userTypeId);
    }
}