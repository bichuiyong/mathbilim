package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.enums.FileType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private Long id;

    private String filename;

    private String filePath;

    private FileType type;

    private Long size;

    private String s3Link;
}