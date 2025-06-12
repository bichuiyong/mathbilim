package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.reference.category.CategoryDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;

    private String name;

    private FileDto file;

    private CategoryDto category;

    private Map<String, String> metadata;

    private Instant createdAt;

    private Instant updatedAt;

    private ContentStatus status;

    private UserDto user;

    private UserDto approvedBy;
}