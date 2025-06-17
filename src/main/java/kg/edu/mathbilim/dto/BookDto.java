package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.reference.category.CategoryDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;

    private String name;

    private String authors;

    private String isbn;

    private String description;

    private FileDto file;

    private CategoryDto category;

    private Instant createdAt;

    private Instant updatedAt;

    private ContentStatus status;

    private UserDto user;

    private UserDto approvedBy;
}