package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto extends ContentDto {
    private String name;

    private String authors;

    private String isbn;

    private String description;

    private FileDto file;

    private CategoryDto category;
}