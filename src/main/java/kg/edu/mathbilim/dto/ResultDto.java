package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.olympiad.OlympiadDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultDto {
    Long id;

    FileDto file;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();
}
