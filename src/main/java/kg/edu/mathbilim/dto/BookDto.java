package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto extends ContentDto {
    String name;

    String authors;

    String isbn;

    String description;

    FileDto file;

    CategoryDto category;
}