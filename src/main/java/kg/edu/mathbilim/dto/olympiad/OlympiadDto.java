package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadDto {
    Long id;

    String title;

    String info;

    UserDto creator;

    String rules;

    String subject;

    LocalDate startDate;

    LocalDate endDate;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();
}
