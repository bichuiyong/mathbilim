package kg.edu.mathbilim.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
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

    Long id;

    @NotBlank
    String name;

    @NotBlank
    String authors;

    @NotBlank
    String isbn;

    @NotBlank
    String description;

    FileDto file;

    @NotNull
    CategoryDto category;
}
