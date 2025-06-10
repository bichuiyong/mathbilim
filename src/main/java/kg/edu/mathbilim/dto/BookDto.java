package kg.edu.mathbilim.dto;

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

    private CategoryDto category;

    private Map<String, Object> metadata;

    private Instant createdAt;

    private Instant updatedAt;

    private Set<AuthorDto> authors = new LinkedHashSet<>();
}