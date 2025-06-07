package kg.edu.mathbilim.dto;

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