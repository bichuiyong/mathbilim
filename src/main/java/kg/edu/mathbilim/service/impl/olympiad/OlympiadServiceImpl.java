package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OlympiadServiceImpl implements OlympiadService {
    private final OlympiadRepository olympiadRepository;
    private final UserService userService;
    private final OlympiadContactService contactService;
    private final OlympiadStageService stageService;
    private final UserMapper userMapper;

    @Override
    public void save(OlympiadCreateDto olympiadCreateDto, String email) {
        User user = userService.findByEmail(email);
        Olympiad olympiad = new Olympiad();

        olympiad.setTitle(olympiadCreateDto.getTitle());
        olympiad.setCreator(user);
        olympiad.setInfo(olympiadCreateDto.getInfo());
        olympiad.setRules(olympiadCreateDto.getRules());
        olympiad.setCreatedAt(LocalDateTime.now());
        olympiad.setUpdatedAt(LocalDateTime.now());
        olympiad.setStartDate(olympiadCreateDto.getStartDate());
        olympiad.setEndDate(olympiadCreateDto.getEndDate());
        olympiadRepository.saveAndFlush(olympiad);

        if (olympiadCreateDto.getStages() != null) {
            stageService.save(olympiadCreateDto, olympiad);
        }

        if (olympiadCreateDto.getContacts() != null) {
            contactService.save(olympiadCreateDto, olympiad);
        }
    }

    @Override
    public Page<OlympiadDto> getAll(Pageable pageable) {
        return olympiadRepository.findAll(pageable)
                .map(olympiad -> new OlympiadDto(
                        olympiad.getId(),
                        olympiad.getTitle(),
                        olympiad.getInfo(),
                        userMapper.toDto(olympiad.getCreator()),
                        olympiad.getRules(),
                        olympiad.getStartDate(),
                        olympiad.getEndDate(),
                        olympiad.getCreatedAt(),
                        olympiad.getUpdatedAt()
                ));
    }


}
