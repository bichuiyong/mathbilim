package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.organization.OlympOrganizationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadDto {
    Integer id;

    String title;

    String info;

    UserDto creator;

    Long fileId;

    String rules;

    LocalDate startDate;

    LocalDate endDate;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();

    List<OlympiadStageDto> stages = new ArrayList<>();

    List<OlympContactDto> contacts = new ArrayList<>();

    List<OrganizationDto> organizations = new ArrayList<>();
}
