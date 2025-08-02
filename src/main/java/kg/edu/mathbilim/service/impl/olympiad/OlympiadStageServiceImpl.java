package kg.edu.mathbilim.service.impl.olympiad;

import jakarta.ws.rs.NotFoundException;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.ResultDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadApprovedListDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.olympiad.Registration;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.ResultRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadApprovedListRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadStageRepository;
import kg.edu.mathbilim.repository.olympiad.RegistrationRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OlympiadStageServiceImpl implements OlympiadStageService {
    private final OlympiadStageRepository repository;
    private final OlympiadStageRepository olympiadStageRepository;
    private final OlympiadApprovedListRepository olympiadApprovedListRepository;
    private final ResultRepository resultRepository;
    private final UserService userService;
    private final RegistrationRepository registrationRepository;

    @Override
    public void save(OlympiadCreateDto dto, Olympiad olympiad) {
        if (dto.getStages() == null) return;

        AtomicInteger order = new AtomicInteger(1);
        dto.getStages().forEach(s -> {
            OlympiadStage stage = OlympiadStage.builder()
                    .startDate(s.getEventStartDate())
                    .endDate(s.getEventEndDate())
                    .registrationStart(s.getRegistrationStart())
                    .registrationEnd(s.getRegistrationEnd())
                    .olympiad(olympiad)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .stageOrder(order.getAndIncrement())
                    .build();
            repository.saveAndFlush(stage);
        });

    }

    @Override
    public List<OlympiadStageDto> getOlympStageDtos(Long id) {
        return repository.getOlympiadStageByOlympiad_IdOrderByStageOrderAsc(id)
                .stream()
                .map(olympiadStage -> OlympiadStageDto
                        .builder()
                        .id(Long.valueOf(olympiadStage.getId()))
                        .stageOrder(olympiadStage.getStageOrder())
                        .registrationStart(olympiadStage.getRegistrationStart())
                        .registrationEnd(olympiadStage.getRegistrationEnd())
                        .createdAt(olympiadStage.getCreatedAt())
                        .updatedAt(olympiadStage.getUpdatedAt())
                        .startDate(olympiadStage.getStartDate())
                        .endDate(olympiadStage.getEndDate())
                        .registrationDate(java.sql.Date.valueOf(olympiadStage.getRegistrationStart()))
                        .registrationEndDate(java.sql.Date.valueOf(olympiadStage.getRegistrationEnd()))
                        .result(olympiadStage.getResult().stream().map(result -> ResultDto
                                .builder()
                                        .id(result.getId())
                                        .file(new FileDto(
                                                result.getFile().getId(),
                                                result.getFile().getFilename(),
                                                result.getFile().getFilePath(),
                                                result.getFile().getType(),
                                                result.getFile().getSize(),
                                                result.getFile().getS3Link()
                                        ))
                                        .createdAt(result.getCreatedAt())
                                        .updatedAt(result.getUpdatedAt())
                                .build())
                                .toList())
                        .approvedList(olympiadStage.getApprovedList().stream().map(list -> OlympiadApprovedListDto
                                        .builder()
                                        .id(list.getId())
                                        .file(new FileDto(
                                                list.getFile().getId(),
                                                list.getFile().getFilename(),
                                                list.getFile().getFilePath(),
                                                list.getFile().getType(),
                                                list.getFile().getSize(),
                                                list.getFile().getS3Link()
                                        ))
                                        .createdAt(list.getCreatedAt())
                                        .updatedAt(list.getUpdatedAt())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public void addAll(List<OlympiadStage> olympiadStages) {
        repository.saveAll(olympiadStages);
    }

    @Override
    public void deleteByOlympiadId(Long olympiadId) {
        repository.deleteByOlympiadId(olympiadId);
    }

    @Override
    public OlympiadStage getOlympiadStageById(Integer stageId) {
        return repository.findById(stageId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<OlympiadStage> saveAll(List<OlympiadStage> olympiadStages) {
        return repository.saveAll(olympiadStages);
    }

    @Override
    public void updateTime(OlympiadStage olympiadStage) {
        olympiadStage.setUpdatedAt(LocalDateTime.now());
        olympiadStageRepository.save(olympiadStage);
    }

    @Override
    public Optional<Long> createRegistrationOlympiad(RegistrationDto dto, Long stageId, String userName) {
        Optional<OlympiadStage> olympiadStage = olympiadStageRepository.findById(stageId);
        if(olympiadStage.isPresent()) {
            OlympiadStage stage = olympiadStage.get();
                Registration registration = Registration.builder()
                        .user(userService.findByEmail(userName))
                        .olympiadStage(stage)
                        .classNumber(dto.getClassNumber())
                        .email(dto.getEmail())
                        .classTeacherFullName(dto.getClassTeacherFullName())
                        .district(dto.getDistrict())
                        .fullName(dto.getFullName())
                        .locality(dto.getLocality())
                        .parentEmail(dto.getParentEmail())
                        .school(dto.getSchool())
                        .region(dto.getRegion())
                        .parentFullName(dto.getParentFullName())
                        .phoneNumber(dto.getPhoneNumber())
                        .telegram(dto.getTelegram())
                        .parentPhoneNumber(dto.getParentPhoneNumber())
                        .created(LocalDateTime.now())
                        .build();
                registrationRepository.save(registration);
            System.out.println("parentPhoneNumber: " + dto.getParentPhoneNumber());
            System.out.println("Entity parentPhoneNumber: " + registration.getParentPhoneNumber());

            return Optional.ofNullable(stage.getOlympiad().getId());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean checkRegisterActually(long stageId) {
        Optional<OlympiadStage> olympiadStage = olympiadStageRepository.findById(stageId);
        if(olympiadStage.isPresent()) {
            OlympiadStage stage = olympiadStage.get();
            return !stage.getRegistrationStart().isAfter(LocalDate.now())
                    && !stage.getRegistrationEnd().isBefore(LocalDate.now());
        }
        return false;
    }

    @Override
    public boolean userHasRegistered(String userName, long stageId) {
        User user = userService.findByEmail(userName);
        if (user.getRole().getName().equalsIgnoreCase("admin") || user.getRole().getName().equalsIgnoreCase("MODER")) {
            return false;}
        OlympiadStage olympiadStage = olympiadStageRepository.findById(stageId).orElse(null);
        return registrationRepository.existsByOlympiadStageAndUser(olympiadStage,user);
    }

    @Override
    public Page<RegistrationDto> getOlympiadRegistrations(Long stageId, Pageable pageable, String keyword) {
        return registrationRepository.getByOlympiadStage_Id(stageId, keyword,pageable)
                .map(reg -> RegistrationDto.builder()
                        .classNumber(reg.getClassNumber())
                        .email(reg.getEmail())
                        .classTeacherFullName(reg.getClassTeacherFullName())
                        .district(reg.getDistrict())
                        .fullName(reg.getFullName())
                        .locality(reg.getLocality())
                        .parentEmail(reg.getParentEmail())
                        .phoneNumber(reg.getPhoneNumber())
                        .telegram(reg.getTelegram())
                        .parentPhoneNumber(reg.getParentPhoneNumber())
                        .olympiadName(reg.getOlympiadStage().getOlympiad().getTitle())
                        .userName(reg.getUser().getEmail())
                        .parentFullName(reg.getParentFullName())
                        .school(reg.getSchool())
                        .region(reg.getRegion())
                        .build());
    }

    @Override
    public List<RegistrationDto> getOlympiadRegistrationsForExcel(Long stageId) {
        return registrationRepository.getByOlympiadStage_IdForExcel(stageId).stream()
                .map(reg -> RegistrationDto.builder()
                        .classNumber(reg.getClassNumber())
                        .email(reg.getEmail())
                        .classTeacherFullName(reg.getClassTeacherFullName())
                        .district(reg.getDistrict())
                        .fullName(reg.getFullName())
                        .locality(reg.getLocality())
                        .parentEmail(reg.getParentEmail())
                        .phoneNumber(reg.getPhoneNumber())
                        .telegram(reg.getTelegram())
                        .parentPhoneNumber(reg.getParentPhoneNumber())
                        .olympiadName(reg.getOlympiadStage().getOlympiad().getTitle())
                        .userName(reg.getUser().getEmail())
                        .parentFullName(reg.getParentFullName())
                        .school(reg.getSchool())
                        .region(reg.getRegion())
                        .build())
                .toList();
    }


}
