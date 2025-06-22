package kg.edu.mathbilim.dto.abstracts;

import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DisplayContentDto extends ContentDto {
    String title;
    String content;

    public DisplayContentDto(Long id,
                             Long creatorId,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             Long viewCount,
                             Long shareCount,
                             Long mainImageId,
                             Long approvedById,
                             ContentStatus status,
                             String title,
                             String content) {
        this.setId(id);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
        this.setViewCount(viewCount);
        this.setShareCount(shareCount);
        this.setStatus(status);
        this.title = title;
        this.content = content;
        this.setCreatorId(creatorId);
        this.setMainImageId(mainImageId);
        this.setApprovedById(approvedById);
    }
}
