package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.enums.Category;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;

    private String name;

    private FileDto file;

    private Category category;

    private Map<String, Object> metadata;

    private Instant createdAt;

    private Instant updatedAt;

    private Set<AuthorDto> authors = new LinkedHashSet<>();

    private ContentStatus status;

    private UserDto user;

    private UserDto approvedBy;
}