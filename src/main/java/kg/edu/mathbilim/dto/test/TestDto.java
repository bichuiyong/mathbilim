package kg.edu.mathbilim.dto.test;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.TestStatus;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
    private Long id;

    private FileDto file;

    private Map<String, Object> metadata;

    private Instant startedAt;

    private Instant finishedAt;

    private Integer result;

    private TestStatus status;

    private UserDto user;

    private Integer timeLimit;
}