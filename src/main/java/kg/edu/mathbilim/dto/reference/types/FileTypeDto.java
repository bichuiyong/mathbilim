package kg.edu.mathbilim.dto.reference.types;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileTypeDto {
    private Integer id;

    private String name;

    private String mimeType;

    private String extension;

}