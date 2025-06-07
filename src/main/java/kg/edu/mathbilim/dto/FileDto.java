package kg.edu.mathbilim.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private Long id;

    private String filename;

    private String filePath;

    private FileTypeDto type;

    private UserDto user;

    private UserDto approvedBy;

    private Long size;

    private Instant createdAt;

    private Instant updatedAt;

    private String s3Link;

}