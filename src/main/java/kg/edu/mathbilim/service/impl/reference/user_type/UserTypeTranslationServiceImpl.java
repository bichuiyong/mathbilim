package kg.edu.mathbilim.service.impl.reference.user_type;

import kg.edu.mathbilim.dto.user.user_type.UserTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.reference.user_type.UserTypeTranslationMapper;
import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslation;
import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslationId;
import kg.edu.mathbilim.repository.reference.user_type.UserTypeTranslationRepository;
import kg.edu.mathbilim.service.interfaces.reference.user_type.UserTypeTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserTypeTranslationServiceImpl implements UserTypeTranslationService {
    private final UserTypeTranslationRepository uttRepository;
    private final UserTypeTranslationMapper uttMapper;

    @Override
    public List<UserTypeTranslationDto> getTranslationsByUserTypeId(Integer userTypeId) {
        return uttRepository.findByUserTypeId(userTypeId)
                .stream()
                .map(uttMapper::toDto)
                .toList();
    }

    @Override
    public UserTypeTranslation getTranslationEntity(Integer userTypeId, String languageCode) {
        return uttRepository.findByUserTypeIdAndLanguageCode(userTypeId, languageCode)
                .orElseThrow(() -> new TranslationNotFoundException("Перевод для этого типа пользователя не был найден"));
    }

    @Override
    public UserTypeTranslationDto getTranslation(Integer userTypeId, String languageCode) {
        return uttMapper.toDto(getTranslationEntity(userTypeId, languageCode));
    }

    @Override
    public List<UserTypeTranslationDto> getTranslationsByLanguage(String languageCode) {
        return uttRepository.findByLanguageCode(languageCode).stream()
                .map(uttMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public UserTypeTranslationDto createTranslation(UserTypeTranslationDto dto) {
        UserTypeTranslation translation = uttMapper.toEntity(dto);
        uttRepository.save(translation);
        log.info("Save translation to language {}: {}", translation.getId().getLanguageCode(), translation.getTranslation());
        return dto;
    }

    @Transactional
    @Override
    public UserTypeTranslationDto updateTranslation(Integer userTypeId, String languageCode, String newTranslation) {
        UserTypeTranslation translation = getTranslationEntity(userTypeId, languageCode);
        translation.setTranslation(newTranslation);
        UserTypeTranslation saved = uttRepository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, newTranslation);
        return uttMapper.toDto(saved);
    }

    @Transactional
    @Override
    public UserTypeTranslationDto upsertTranslation(UserTypeTranslationDto dto) {
        return updateTranslation(dto.getUserTypeId(), dto.getLanguageCode(), dto.getTranslation());
    }

    @Override
    @Transactional
    public void deleteTranslation(Integer userTypeId, String languageCode) {
        UserTypeTranslationId id = new UserTypeTranslationId();
        id.setUserTypeId(userTypeId);
        id.setLanguageCode(languageCode);
        uttRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllTranslationsByUserTypeId(Integer userTypeId) {
        uttRepository.deleteByUserTypeId(userTypeId);
    }

    @Override
    public boolean existsTranslation(Integer userTypeId, String languageCode) {
        return uttRepository.existsByUserTypeIdAndLanguageCode(userTypeId, languageCode);
    }
}