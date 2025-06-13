package kg.edu.mathbilim.service.impl.reference.user_type;

import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;
import kg.edu.mathbilim.dto.reference.user_type.UserTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.reference.user_type.UserTypeMapper;
import kg.edu.mathbilim.model.reference.user_type.UserType;
import kg.edu.mathbilim.repository.reference.user_type.UserTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.user_type.UserTypeService;
import kg.edu.mathbilim.service.interfaces.reference.user_type.UserTypeTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                    dto.setUserTypeTranslations(Set.of(userTypeTranslationService.getTranslation(userType.getId(), languageCode)));
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
            Set<UserTypeTranslationDto> savedTranslations = userTypeDto
                    .getUserTypeTranslations()
                    .stream()
                    .map(translation -> {
                        translation.setUserTypeId(savedUserType.getId());
                        return userTypeTranslationService.createTranslation(translation);
                    })
                    .collect(Collectors.toSet());
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

            Set<UserTypeTranslationDto> savedTranslations =
                    userTypeDto.getUserTypeTranslations()
                            .stream()
                            .map(translation -> {
                                translation.setUserTypeId(id);
                                return userTypeTranslationService.createTranslation(translation);
                            })
                            .collect(Collectors.toSet());

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