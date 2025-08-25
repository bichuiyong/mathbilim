package kg.edu.mathbilim.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import kg.edu.mathbilim.validation.annotation.NotEmptyMultipartFile;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto extends ContentDto {

    Long id;

    @NotBlank
    @Size(max = 100)
    String name;

    @NotBlank
    @Size(max = 100)
    String authors;

    @NotBlank
    @Size(max = 20)
    String isbn;

    @NotBlank
    @Size(max = 255)
    String description;
    FileDto file;

    @NotNull
    CategoryDto category;

    @NotEmptyMultipartFile
    @AllowedExtension({"pdf"})
    MultipartFile attachments;

    MultipartFile mpMainImage;
}
