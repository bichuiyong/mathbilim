package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.enums.FileType;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDto {
    Long id;

    String filename;

    String filePath;

    FileType type;

    Long size;

    String s3Link;
}