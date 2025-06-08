package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.status.TestStatusDto;
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

    private String s3Link;

    private CategoryDto category;

    private Map<String, Object> metadata;

    private Instant startedAt;

    private Instant finishedAt;

    private Integer result;

    private TestStatusDto status;

    private UserDto user;

    private Integer timeLimit;
}