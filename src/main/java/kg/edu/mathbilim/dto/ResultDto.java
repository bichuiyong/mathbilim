package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import kg.edu.mathbilim.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ResultDto {
    private Long id;

    private UserDto user;

    private FileDto file;

    private OlympiadDto olympiad;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
