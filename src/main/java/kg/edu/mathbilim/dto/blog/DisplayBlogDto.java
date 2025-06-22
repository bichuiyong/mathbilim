package kg.edu.mathbilim.dto.blog;

import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisplayBlogDto extends ContentDto {
    String title;
    String content;

    public DisplayBlogDto(Long id,
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

    public String getDescription() {
        if (content == null) return "";
        String plainText = content.replaceAll("<[^>]*>", "").trim();
        return plainText.length() > 160 ?
                plainText.substring(0, 157) + "..." : plainText;
    }
}