package kg.edu.mathbilim.dto.test;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.TestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDto {
    Long id;

    FileDto file;

    Map<String, Object> metadata;

    LocalDateTime startedAt;

    LocalDateTime finishedAt;

    Integer result;

    TestStatus status;

    UserDto user;

    Integer timeLimit;
}