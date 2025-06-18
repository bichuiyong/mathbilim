package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class OlympiadDto {
    private Long id;

    private String title;

    private String info;

    private UserDto creator;

    private String rules;

    private String subject;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
