package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.FileType;
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

    private FileType type;

    private UserDto user;

    private UserDto approvedBy;

    private Long size;

    private Instant createdAt;

    private Instant updatedAt;

    private String s3Link;

    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
}